package cosyfish.pro.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class ObjectMapUtil {
    public static Logger LOGGER = LoggerFactory.getLogger(ObjectMapUtil.class);
    static DecimalFormat decimalFormat = new DecimalFormat();

    static {
        decimalFormat.setGroupingUsed(false);
    }

    public static <T> T objectMapToObject(Map<String, Object> map, Class<T> beanClass) {
        if (map == null || map.size() == 0) {
            return null;
        }
        T obj = null;
        PropertyDescriptor[] propertyDescriptors = null;
        try {
            obj = beanClass.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            propertyDescriptors = beanInfo.getPropertyDescriptors();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        if (propertyDescriptors != null) {
            for (PropertyDescriptor property : propertyDescriptors) {
                Method setter = property.getWriteMethod();
                if (setter != null) {
                    try {
                        setter.invoke(obj, map.get(property.getName()));
                    } catch (Exception e) {
                        LOGGER.error("convert error:" + property.getName() + "->" + e.getMessage());
                    }
                }
            }
        }
        return obj;
    }

    public static Map<String, Object> objectToObjectMap(Object obj) {
        Map<String, Object> map = null;
        try {
            map = new HashMap<String, Object>();
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (key.compareToIgnoreCase("class") == 0) {
                    continue;
                }
                Method getter = property.getReadMethod();
                Object value = getter != null ? getter.invoke(obj) : null;
                map.put(key, value);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return map;
    }

    public static <T> T mapToObject(Map<String, String> map, Class<T> beanClass) {
        if (map == null || map.size() == 0) {
            return null;
        }
        T obj = null;
        PropertyDescriptor[] propertyDescriptors = null;
        try {
            obj = beanClass.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            propertyDescriptors = beanInfo.getPropertyDescriptors();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        if (propertyDescriptors != null) {
            for (PropertyDescriptor property : propertyDescriptors) {
                Method setter = property.getWriteMethod();
                String value = map.get(property.getName());
                if (setter != null && value != null) {
                    try {
                        switch (property.getPropertyType().getName()) {
                            case "java.lang.String":
                                setter.invoke(obj, value);
                                break;
                            case "java.lang.Number":
                                setter.invoke(obj, NumberFormat.getInstance().parse(value));
                                break;
                            case "int":
                                setter.invoke(obj, Integer.parseInt(value));
                                break;
                            case "java.lang.Integer":
                                setter.invoke(obj, Integer.parseInt(value));
                                break;
                            case "double":
                                setter.invoke(obj, Double.parseDouble(value));
                                break;
                            case "java.lang.Double":
                                setter.invoke(obj, Double.parseDouble(value));
                                break;
                            case "long":
                                setter.invoke(obj, Long.parseLong(value));
                                break;
                            case "java.lang.Long":
                                setter.invoke(obj, Long.parseLong(value));
                                break;
                            case "boolean":
                                setter.invoke(obj, Boolean.parseBoolean(value));
                                break;
                            case "java.lang.Boolean":
                                setter.invoke(obj, Boolean.parseBoolean(value));
                                break;
                            case "java.util.Date":
                                if (value != null) {
                                    setter.invoke(obj, DateUtils.parseWebDateQuietly(value));
                                }
                                break;
                            default:
                                break;
                        }
                    } catch (Exception e) {
                        LOGGER.error("convert error:" + property.getName() + "->" + value);
                    }
                }
            }
        }
        return obj;
    }

    public static Map<String, String> objectToMap(Object obj) {
        try {
            Map<String, String> map = new HashMap<String, String>();
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (key.compareToIgnoreCase("class") == 0) {
                    continue;
                }
                Method getter = property.getReadMethod();
                switch (property.getPropertyType().getName()) {
                    case "java.lang.Number":
                        Object tmp = getter != null ? getter.invoke(obj) : null;
                        String value = decimalFormat.format(tmp);
                        if (value != null) {
                            map.put(key, value);
                        }
                        break;
                    case "java.util.Date":
                        tmp = getter != null ? getter.invoke(obj) : null;
                        if (tmp != null) {
                            map.put(key, DateUtils.DefaultDateTimeMSFormat.format(tmp));
                        }
                        break;
                    default:
                        tmp = getter != null ? getter.invoke(obj) : null;
                        if (tmp != null) {
                            map.put(key, tmp.toString());
                        }
                        break;
                }
            }
            return map;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }
}
