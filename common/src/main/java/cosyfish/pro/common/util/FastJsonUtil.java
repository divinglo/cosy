package cosyfish.pro.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import java.util.List;

public class FastJsonUtil {

    /**
     * 默认日期格式化格式
     */
    private static final String DEF_DATEFORMAT = cosyfish.pro.common.util.DateUtils.FORMART_YYYY_MM_DD_HH_MM_SS;

    /**
     * 获得json字符串的某个属性值
     */
    public static String getVal(String jsonStr, String key) {
        String _val = null;
        if (StringUtils.isNotBlank(jsonStr) && StringUtils.isNotBlank(key)) {
            try {
                JSONObject jsonObject = parse(jsonStr);
                if (jsonObject != null) {
                    _val = jsonObject.getString(key);
                }
            } catch (Exception e) {
                // ignore
            }
        }
        return _val;
    }

    /**
     * 将json字符串转换成JSONObject
     */
    public static JSONObject parse(String jsonStr) {
        if (StringUtils.isBlank(jsonStr) || StringUtils.equalsIgnoreCase("null", jsonStr)) {
            return null;
        } else {
            return JSON.parseObject(jsonStr);
        }
    }

    /**
     * 将json字符串转换成对象
     */
    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        if (StringUtils.isBlank(jsonStr) || StringUtils.equalsIgnoreCase("null", jsonStr)) {
            return null;
        } else {
            return JSON.parseObject(jsonStr, clazz);
        }
    }

    /**
     * 将json字符串转换成对象列表
     */
    public static <T> List<T> parseArray(String jsonStr, Class<T> clazz) {
        if (StringUtils.isBlank(jsonStr) || StringUtils.equalsIgnoreCase("null", jsonStr)) {
            return null;
        }
        return JSON.parseArray(jsonStr, clazz);
    }

    /**
     * 将json字符串子元素转换成对象
     */
    public static <T> T parseSubObject(String jsonStr, String key, Class<T> clazz) {
        return JSON.parseObject(jsonStr).getJSONObject(key).toJavaObject(clazz);
    }

    /**
     * 将json字符串子元素转换成对象列表
     */
    public static <T> List<T> parseSubArray(String jsonStr, String key, Class<T> clazz) {
        List<T> _val = Lists.newArrayList();
        JSONObject _jsonObject = parse(jsonStr);
        if (_jsonObject != null) {
            JSONArray _jsonArry = _jsonObject.getJSONArray(key);
            if (_jsonArry != null) {
                _val = _jsonArry.toJavaList(clazz);
            }
        }
        return _val;
    }

    public static String toJSONString(Object object) {
        return JSON.toJSONStringWithDateFormat(object, DEF_DATEFORMAT);
    }

    /**
     * 对象转json字符串
     */
    public static String toJsonString(Object object) {
        return toJSONString(object);
    }

    /**
     * 根据指定格式转换日期
     */
    public static String toJsonString(Object object, String dateFormat) {
        return JSON.toJSONStringWithDateFormat(object, dateFormat);
    }

    /**
     * 对象转json字符串(带空值输出)
     */
    public static String toJsonStringWithNull(Object object) {
        return JSON.toJSONStringWithDateFormat(object, DEF_DATEFORMAT);
    }

    /**
     * 将json字符串转换成JSONArray
     */
    public static JSONArray parseArray(String jsonStr) {
        if (StringUtils.isBlank(jsonStr) || StringUtils.equalsIgnoreCase("null", jsonStr)) {
            return null;
        } else {
            return JSON.parseArray(jsonStr);
        }
    }
}

