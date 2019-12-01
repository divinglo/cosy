package cosyfish.pro.common.util;

import com.esotericsoftware.reflectasm.MethodAccess;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("rawtypes")
public class BeanCopyUtil {
    private static Map<Class, MethodAccess> methodAccessMap = new HashMap<Class, MethodAccess>();

    private static Map<String, Integer> methodIndexMap = new HashMap<String, Integer>();

    private static Map<Class, Set<String>> fieldMap = new HashMap<Class, Set<String>>();

    /**
     * 在2个Bean之间复制属性值
     */
    public static void copy(Object source, Object target) {

        MethodAccess descMethodAccess = methodAccessMap.get(target.getClass());
        if (descMethodAccess == null) {
            descMethodAccess = cache(target);
        }
        MethodAccess orgiMethodAccess = methodAccessMap.get(source.getClass());
        if (orgiMethodAccess == null) {
            orgiMethodAccess = cache(source);
        }

        Set<String> fieldSet = fieldMap.get(source.getClass());
        for (String field : fieldSet) {
            String getKey = source.getClass().getName() + "." + "get" + field;
            String setkey = target.getClass().getName() + "." + "set" + field;
            Integer setIndex = methodIndexMap.get(setkey);
            if (setIndex != null) {
                int getIndex = methodIndexMap.get(getKey);
                // 参数一需要反射的对象
                // 参数二class.getDeclaredMethods 对应方法的index
                // 参数对三象集合
                descMethodAccess.invoke(target, setIndex.intValue(), orgiMethodAccess.invoke(source, getIndex));
            }
        }
    }

    public static void copyProperties(Object desc, Object orgi) {
        MethodAccess descMethodAccess = methodAccessMap.get(desc.getClass());
        if (descMethodAccess == null) {
            descMethodAccess = cache(desc);
        }
        MethodAccess orgiMethodAccess = methodAccessMap.get(orgi.getClass());
        if (orgiMethodAccess == null) {
            orgiMethodAccess = cache(orgi);
        }

        Set<String> fieldSet = fieldMap.get(orgi.getClass());
        for (String field : fieldSet) {
            String getKey = orgi.getClass().getName() + "." + "get" + field;
            String setkey = desc.getClass().getName() + "." + "set" + field;
            Integer setIndex = methodIndexMap.get(setkey);
            if (setIndex != null) {
                int getIndex = methodIndexMap.get(getKey);
                // 参数一需要反射的对象
                // 参数二class.getDeclaredMethods 对应方法的index
                // 参数对三象集合
                descMethodAccess.invoke(desc, setIndex.intValue(), orgiMethodAccess.invoke(orgi, getIndex));
            }
        }
    }

    // 单例模式
    private static MethodAccess cache(Object orgi) {
        synchronized (orgi.getClass()) {
            MethodAccess methodAccess = MethodAccess.get(orgi.getClass());
            Field[] fields = orgi.getClass().getDeclaredFields();
            Set<String> fieldSet = new HashSet<>();
            for (Field field : fields) {
                if (Modifier.isPrivate(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())) { // 是否是私有的，是否是静态的
                    // 非公共私有变量
                    String fieldName = StringUtils.capitalize(field.getName()); // 获取属性名称
                    int getIndex = methodAccess.getIndex("get" + fieldName); // 获取get方法的下标
                    int setIndex = methodAccess.getIndex("set" + fieldName); // 获取set方法的下标
                    methodIndexMap.put(orgi.getClass().getName() + "." + "get" + fieldName, getIndex); // 将类名get方法名，方法下标注册到map中
                    methodIndexMap.put(orgi.getClass().getName() + "." + "set" + fieldName, setIndex); // 将类名set方法名，方法下标注册到map中
                    fieldSet.add(fieldName); // 将属性名称放入集合里
                }
            }

            // 子类访问不到父类的private属性，可以通过父类public的get set方法调用
            String[] methodNames = methodAccess.getMethodNames();
            for (String methodName : methodNames) {
                if (methodName.startsWith("get") || methodName.startsWith("set")) {
                    String fieldName = StringUtils.capitalize(methodName.substring(3));
                    int index = methodAccess.getIndex(methodName); // 获取set方法的下标
                    methodIndexMap.put(orgi.getClass().getName() + "." + methodName, index); // 将类名set方法名，方法下标注册到map中
                    fieldSet.add(fieldName);
                }
            }
            fieldMap.put(orgi.getClass(), fieldSet); // 将类名，属性名称注册到map中
            methodAccessMap.put(orgi.getClass(), methodAccess);
            return methodAccess;
        }
    }

}

