package cosyfish.pro.common.util;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 2018年3月12日 新增指定字段复制
 *
 * @author caixiwei
 */
public class ASMBeanUtil {

    private static Logger logger = LoggerFactory.getLogger(ASMBeanUtil.class);

    private static Map<Class<?>, MethodAccess> clazzMap = new HashMap<Class<?>, MethodAccess>();

    private static Map<String, Integer> clazzMethodIndexMap = new HashMap<String, Integer>();

    private static Map<Class<?>, Set<String>> clazzFieldSetMap = new HashMap<Class<?>, Set<String>>();

    /**
     * 复制属性<br>
     * 注意,字段类型需要相同, 如src.idx(Integer)无法赋值给dest.idx(Long)<br>
     *
     * @param dest 目标对象
     * @param src 来源对象
     */
    public static void copyProperties(Object dest, Object src) {
        copy(dest, src);
    }

    /**
     * 复制属性<br>
     * 注意,字段类型需要相同, 如src.idx(Integer)无法赋值给dest.idx(Long)<br>
     *
     * @param dest 目标对象
     * @param src 来源对象
     */
    public static void copy(Object dest, Object src) {
        MethodAccess destMethodAccess = _getMethodAccess(dest);
        MethodAccess srcMethodAccess = _getMethodAccess(src);
        // 获得可用字段set
        Set<String> fieldSet = clazzFieldSetMap.get(src.getClass());
        for (String field : fieldSet) {
            Integer getIndex = clazzMethodIndexMap.get(src.getClass().getName() + "." + "get" + field);
            Integer setIndex = clazzMethodIndexMap.get(dest.getClass().getName() + "." + "set" + field);
            if (setIndex != null && getIndex != null) {
                destMethodAccess.invoke(dest, setIndex.intValue(), srcMethodAccess.invoke(src, getIndex));
            }
        }
    }

    /**
     * 限定字段复制属性<br>
     * 注意,字段类型需要相同, 如src.idx(Integer)无法赋值给dest.idx(Long)
     *
     * @param dest 目标对象
     * @param src 来源对象
     * @param limitFields 限定字段(忽略大小写)
     */
    public static void copyLimitFields(Object dest, Object src, Set<String> limitFields) {
        MethodAccess destMethodAccess = _getMethodAccess(dest);
        MethodAccess srcMethodAccess = _getMethodAccess(src);
        Set<String> _limitFields = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(limitFields)) {
            for (String field : limitFields) {
                if (StringUtils.isNotBlank(field)) {
                    _limitFields.add(StringUtils.lowerCase(field));
                }
            }
        }
        if (CollectionUtils.isNotEmpty(_limitFields)) {
            Set<String> fieldSet = clazzFieldSetMap.get(src.getClass());
            for (String field : fieldSet) {
                if (_limitFields.contains(StringUtils.lowerCase(field))) {
                    Integer getIndex = clazzMethodIndexMap.get(src.getClass().getName() + "." + "get" + field);
                    Integer setIndex = clazzMethodIndexMap.get(dest.getClass().getName() + "." + "set" + field);
                    if (setIndex != null && getIndex != null) {
                        destMethodAccess.invoke(dest, setIndex.intValue(), srcMethodAccess.invoke(src, getIndex));
                    }
                }
            }
        }
    }

    /**
     * 限定字段复制属性, 并忽略字段复制异常<br>
     * 注意,字段类型需要相同, 如src.idx(Integer)无法赋值给dest.idx(Long)
     *
     * @param dest 目标对象
     * @param src 来源对象
     * @param limitFields 限定字段(忽略大小写)
     */
    public static void copyLimitFieldsQuietly(Object dest, Object src, Set<String> limitFields) {
        MethodAccess destMethodAccess = _getMethodAccess(dest);
        MethodAccess srcMethodAccess = _getMethodAccess(src);
        Set<String> _limitFields = Sets.newHashSet();
        if (CollectionUtils.isNotEmpty(limitFields)) {
            for (String field : limitFields) {
                if (StringUtils.isNotBlank(field)) {
                    _limitFields.add(StringUtils.lowerCase(field));
                }
            }
        }
        if (CollectionUtils.isNotEmpty(_limitFields)) {
            Set<String> fieldSet = clazzFieldSetMap.get(src.getClass());
            for (String field : fieldSet) {
                if (_limitFields.contains(StringUtils.lowerCase(field))) {
                    String src_method = src.getClass().getName() + "." + "get" + field;
                    String dest_method = dest.getClass().getName() + "." + "set" + field;
                    Integer getIndex = clazzMethodIndexMap.get(src.getClass().getName() + "." + "get" + field);
                    Integer setIndex = clazzMethodIndexMap.get(dest.getClass().getName() + "." + "set" + field);
                    if (setIndex != null && getIndex != null) {
                        try {
                            destMethodAccess.invoke(dest, setIndex.intValue(), srcMethodAccess.invoke(src, getIndex));
                        } catch (Exception e) {
                            logger.warn("复制属性异常, src_method={}, dest_method={}, 异常=", src_method, dest_method, e.getMessage());
                        }
                    }
                }
            }
        }
    }

    /**
     * 缓存类的可获得方法
     *
     * @param clazz 类
     * @return 可获得方法
     */
    private static MethodAccess _getMethodAccess(Object clazz) {
        MethodAccess clazzMethodAccess = clazzMap.get(clazz.getClass());
        if (clazzMethodAccess == null) {
            synchronized (clazz.getClass()) {
                // 进入锁, 再次尝试从Map获取
                clazzMethodAccess = clazzMap.get(clazz.getClass());
                if (clazzMethodAccess != null) {
                    return clazzMethodAccess;
                }
                MethodAccess methodAccess = MethodAccess.get(clazz.getClass());
                Field[] fields = clazz.getClass().getDeclaredFields();
                Set<String> fieldSet = new HashSet<>();
                for (Field field : fields) {
                    if (Modifier.isPrivate(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())) { // 是否是私有的，是否是静态的
                        // 非公共私有变量
                        String fieldName = StringUtils.capitalize(field.getName()); // 获取属性名称
                        int getIndex = methodAccess.getIndex("get" + fieldName); // 获取get方法的下标
                        int setIndex = methodAccess.getIndex("set" + fieldName); // 获取set方法的下标
                        clazzMethodIndexMap.put(clazz.getClass().getName() + "." + "get" + fieldName, getIndex); // 将类名get方法名，方法下标注册到map中
                        clazzMethodIndexMap.put(clazz.getClass().getName() + "." + "set" + fieldName, setIndex); // 将类名set方法名，方法下标注册到map中
                        fieldSet.add(fieldName); // 将属性名称放入集合里
                    }
                }
                // 子类访问不到父类的private属性，可以通过父类public的get set方法调用
                String[] methodNames = methodAccess.getMethodNames();
                for (String methodName : methodNames) {
                    if (methodName.startsWith("get") || methodName.startsWith("set")) {
                        String fieldName = StringUtils.capitalize(methodName.substring(3));
                        int index = methodAccess.getIndex(methodName); // 获取set方法的下标
                        clazzMethodIndexMap.put(clazz.getClass().getName() + "." + methodName, index); // 将类名set方法名，方法下标注册到map中
                        fieldSet.add(fieldName);
                    }
                }
                clazzFieldSetMap.put(clazz.getClass(), fieldSet); // 将类名，属性名称注册到map中
                clazzMap.put(clazz.getClass(), methodAccess);
                clazzMethodAccess = methodAccess;
            }
        }
        return clazzMethodAccess;
    }

}
