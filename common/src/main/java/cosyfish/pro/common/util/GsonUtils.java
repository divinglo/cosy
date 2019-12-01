package cosyfish.pro.common.util;

import com.google.gson.*;
import cosyfish.pro.common.util.gson.DoubleTypeAdapter;
import cosyfish.pro.common.util.gson.StringTypeAdapter;
import cosyfish.pro.common.util.gson.TypeOfClass;
import cosyfish.pro.common.util.gson.TypeOfClassAndType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Gson工具类
 *
 * @author wprehard@qq.com
 * @date 2017年3月24日
 */
public class GsonUtils {

    /**
     * Double类型的序列化和反序列化处理 序列化为字符类型
     */
    public static final TypeAdapter<Double> DOUBLE_ADAPTER = new DoubleTypeAdapter();

    /**
     * 空字符的序列化和反序列化处理
     */
    public static final TypeAdapter<String> STRING_ADAPTER = new StringTypeAdapter();

    /**
     * 日期格式
     */
    public final static String defaultDateFormatPattern = "yyyy-MM-dd HH:mm:ss";

    /**
     * 序列化不带NUll值字段
     */
    public final static Gson gsonWithoutNull = new GsonBuilder()
            // 日期格式
            .setDateFormat(defaultDateFormatPattern)
            .create();

    /**
     * 序列化带NUll值字段
     */
    public final static Gson gsonWithNull = new GsonBuilder()
            // 当需要序列化的字段为空时，采用null映射
            .serializeNulls()
            // 日期格式
            .setDateFormat(defaultDateFormatPattern)
            .create();

    /**
     * WEB传输协议专用：序列化不包括null字段;浮点数默认只保留小数点后2位
     */
    public final static Gson gsonGenProto = new GsonBuilder()
            // 不再兼容Expose
            // .excludeFieldsWithoutExposeAnnotation()
            // 日期格式
            .setDateFormat(defaultDateFormatPattern)
            // 浮点数处理，默认小数点后2位
            .registerTypeAdapter(Double.class, DOUBLE_ADAPTER)
            .create();

    /**
     * 针对@Expose注解解析对象，且序列化后不出现null值字段
     */
    public final static Gson gsonOnlyWithExposeFieldsWithoutNull =
            new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat(GsonUtils.defaultDateFormatPattern).create();

    /**
     * 解析json字符串，返回某个指定key的整形值
     *
     * @param jsonStr    json字符串
     * @param key        解析带查找的key
     * @param defaultVal 找不到时返回的默认值
     * @return intValue 返回整形值
     */
    public static int getInt(String jsonStr, String key, int defaultVal) {
        JsonElement e = new JsonParser().parse(jsonStr);
        if (e != null && e.isJsonObject()) {
            JsonElement v = e.getAsJsonObject().get(key);
            if (v != null && v.isJsonPrimitive()) {
                return v.getAsInt();
            }
        }
        return defaultVal;
    }

    /**
     * 解析json字符串对象，返回指定key字段的字符串值；如果不存在，返回null
     *
     * @param jsonStr JSON字符串
     * @param key     指定的字段名
     * @return 字符值，如果不存在，返回null
     */
    public static String getString(String jsonStr, String key) {
        return getString(jsonStr, key, null);
    }

    /**
     * 解析json字符串对象，返回指定key字段的字符串值；如果不存在，则返回默认值
     *
     * @param jsonStr    JSON字符串
     * @param key        指定的字段名
     * @param defaultVal 默认值
     * @return 字符值，如果不存在，返回null
     */
    public static String getString(String jsonStr, String key, String defaultVal) {
        JsonElement e = new JsonParser().parse(jsonStr);
        if (e != null && e.isJsonObject()) {
            JsonElement v = e.getAsJsonObject().get(key);
            if (v != null && v.isJsonPrimitive()) {
                return v.getAsString();
            }
        }
        return defaultVal;
    }

    /**
     * 解析字符串为JsonObject，如果不是JsonObject，则返回null值
     *
     * @param jsonStr JSON字符串
     * @return JsonObject对象
     */
    public static JsonObject parseToJsonObject(String jsonStr) {
        JsonElement e = new JsonParser().parse(jsonStr);
        if (e != null && e.isJsonObject()) {
            return e.getAsJsonObject();
        }
        return null;
    }

    /**
     * 解析字符串的为JsonArray，如果不是JsonArray，则返回null值
     *
     * @param jsonStr JSON字符串
     * @return JsonArray对象
     */
    public static JsonArray parseToJsonArray(String jsonStr) {
        JsonElement e = new JsonParser().parse(jsonStr);
        if (e != null && e.isJsonArray()) {
            return e.getAsJsonArray();
        }
        return null;
    }

    /**
     * 解析JsonObject字符串的某个子元素为JsonObject，如果不是JsonObject，则返回null值
     *
     * @param jsonStr JSON字符串
     * @return JsonObject对象
     */
    public static JsonObject parseFieldToJsonObject(String jsonStr, String key) {
        JsonObject o = parseToJsonObject(jsonStr);
        if (o != null && o.has(key)) {
            JsonElement e = o.get(key);
            return e.isJsonObject() ? e.getAsJsonObject() : null;
        }
        return null;
    }

    /**
     * 将Json字符串转换反序列化为List<T>对象
     *
     * @param jsonStr 待反序列化的字符串
     * @param clazz   指定的class类型
     * @return 返回对象列表, 对象不存在时, 返回空列表
     */
    public static <T> List<T> toList(String jsonStr, final Class<T> clazz) {
        List<T> l = gsonWithoutNull.fromJson(jsonStr, new TypeOfClass<T>(clazz));
        if (l == null) {
            return new ArrayList<>();
        }
        return l;
    }

    /**
     * 将JsonArray对象转换化为List<T>对象
     *
     * @param jsonStr 待反序列化的字符串
     * @param clazz   指定的class类型
     * @return 返回对象列表，当类型不一致或对象不存在时，返回null
     */
    public static <T> List<T> toList(JsonArray jsonArray, final Class<T> clazz) {
        if (jsonArray == null) {
            return null;
        }
        if (clazz == null) {
            throw new RuntimeException("请填写正确的转换类型");
        }
        return GsonUtils.gsonWithoutNull.fromJson(jsonArray, new TypeOfClass<T>(clazz));
    }

    /**
     * 将json字符串反序列化为对象，使用指定的类型class
     *
     * @param jsonStr 待反序列化的字符串
     * @param clazz   指定的class类型
     * @return 指定类型的对象
     */
    public static <T> T toObject(String jsonStr, final Class<T> clazz) {
        return gsonWithoutNull.fromJson(jsonStr, clazz);
    }

    /**
     * 将json字符串反序列化为对象，使用指定的{@link Type}类型
     *
     * @param jsonStr 待反序列化的字符串
     * @param typeOfT 指定的Java类型
     * @return 指定类型的对象
     */
    public static <T> T toObject(String jsonStr, final Type typeOfT) {
        return gsonWithoutNull.fromJson(jsonStr, typeOfT);
    }

    /**
     * 将json字符串反序列化为对象，适用指定的泛型类和泛型参数类型列表
     * <p>
     * 示例：对目标类Response<String> 实体进行反序列化，调用传参：toObject(str, Response.class, String.class); 该方法参数对静态内部类有效，对内部类可能无效
     * </p>
     *
     * @param jsonStr 待反序列化的字符串
     * @param clazz   指定的class类型
     * @param typeOfT 指定的泛型类参数列表
     * @return 指定类型的对象
     */
    public static <T> T toObject(String jsonStr, final Class<?> clazz, Type... typeOfT) {
        return gsonWithoutNull.fromJson(jsonStr, new TypeOfClassAndType(clazz, typeOfT));
    }

    /**
     * 将json字符串反序列化为对象，允许对象包含null元素
     *
     * @param jsonStr 待反序列化的字符串
     * @param clazz   指定的class类型
     * @return 指定类型的对象
     */
    public static <T> T toObjectWithNull(String jsonStr, final Class<T> clazz) {
        return gsonWithNull.fromJson(jsonStr, clazz);
    }

    /**
     * 将对象转换为字符串，不包含对象中的值为null的字段
     *
     * @param Object 待序列化的对象
     * @return 返回字符串
     */
    public static String toJsonString(Object o) {
        String jsonStr = gsonWithoutNull.toJson(o);
        return jsonStr;
    }

    /**
     * 将对象转换为字符串，使用gsonGenProto
     *
     * @param Object 待序列化的对象
     * @return 返回字符串
     * @date 2018年3月28日
     */
    public static String toJsonStringToProto(Object o) {
        String jsonStr = gsonGenProto.toJson(o);
        return jsonStr;
    }

    /**
     * 将对象转换为字符串，允许null值字段显示
     *
     * @param Object 待序列化的对象
     * @return 返回字符串
     * @date 2018年3月28日
     */
    public static String toJsonStringWithNull(Object o) {
        String jsonStr = gsonWithNull.toJson(o);
        return jsonStr;
    }
}
