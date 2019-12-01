package cosyfish.pro.common.util.gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 定义一种Type类型，用于Gson反序列化list格式的字符
 *
 * @author weipengfei@youcheyihou.com
 * @date 2018-05-11
 */
public class TypeOfClass<T> implements ParameterizedType {

    private Class<?> clazz;

    public TypeOfClass(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return new Type[]{clazz};
    }

    @Override
    public Type getRawType() {
        return List.class;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }

}
