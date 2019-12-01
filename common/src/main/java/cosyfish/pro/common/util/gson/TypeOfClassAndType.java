package cosyfish.pro.common.util.gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 定义一种Type类型，用于Gson反序列化带泛型类参数的指定解析
 *
 * @author weipengfei@youcheyihou.com
 * @date 2018-05-11
 */
public class TypeOfClassAndType implements ParameterizedType {

    private Class<?> clazz;
    private Type[] typeArgs;

    public TypeOfClassAndType(final Class<?> clazz, final Type... typeArgs) {
        this.clazz = clazz;
        this.typeArgs = typeArgs;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return typeArgs;
    }

    @Override
    public Type getRawType() {
        return clazz;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }

}
