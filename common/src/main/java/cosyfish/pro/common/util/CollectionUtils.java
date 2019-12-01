package cosyfish.pro.common.util;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 集合的工具方法
 *
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @since 1.1.3
 */
public abstract class CollectionUtils {

    /**
     * Return <code>true</code> if the supplied Collection is <code>null</code> or empty. Otherwise,
     * return <code>false</code>.
     *
     * @param collection the Collection to check
     * @return whether the given Collection is empty
     */
    public static boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * Return <code>true</code> if the supplied Map is <code>null</code> or empty. Otherwise, return
     * <code>false</code>.
     *
     * @param map the Map to check
     * @return whether the given Map is empty
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * Convert the supplied array into a List. A primitive array gets converted into a List of the
     * appropriate wrapper type.
     * <p>
     * A <code>null</code> source value will be converted to an empty List.
     *
     * @param source the (potentially primitive) array
     * @return the converted List result
     * @see ObjectUtils#toObjectArray(Object)
     */
    public static List<Object> arrayToList(Object source) {
        return Arrays.asList(ObjectUtils.toObjectArray(source));
    }

    /**
     * Merge the given array into the given Collection.
     *
     * @param array the array to merge (may be <code>null</code>)
     * @param collection the target Collection to merge the array into
     */
    public static void mergeArrayIntoCollection(Object array, Collection<Object> collection) {
        if (collection == null) {
            throw new IllegalArgumentException("Collection must not be null");
        }
        Object[] arr = ObjectUtils.toObjectArray(array);
        for (int i = 0; i < arr.length; i++) {
            collection.add(arr[i]);
        }
    }

    public static Map<Object, Object[]> mergeMapIntoGivenMap(Map<?, ?> givenMap, Map<?, ?> map) {
        Set<?> keys = givenMap.keySet();

        Map<Object, Object[]> newMap = new HashMap<Object, Object[]>();
        for (Object key : keys) {
            Object value = givenMap.get(key);
            if (value == null) {
                continue;
            }
            if (value.getClass().isArray()) {
                Object[] objs = ObjectUtils.toObjectArray(value);
                newMap.put(key, objs);
            } else {
                Object[] newArray = (Object[]) Array.newInstance(value.getClass(), 1);
                Array.set(newArray, 0, value);
                newMap.put(key, newArray);
            }
        }

        keys = map.keySet();
        for (Object key : keys) {
            Object value = map.get(key);
            if (value == null) {
                continue;
            }
            if (value.getClass().isArray()) {
                Object[] objs = ObjectUtils.toObjectArray(value);
                Object[] old = newMap.get(key);
                if (old != null) {
                    Object[] newObjs = (Object[]) ArrayUtils.append(old, objs);
                    newMap.put(key, newObjs);
                } else {
                    newMap.put(key, objs);
                }

            } else {
                Object[] newArray = (Object[]) Array.newInstance(value.getClass(), 1);
                Array.set(newArray, 0, value);
                Object[] old = newMap.get(key);
                if (old != null) {
                    Object[] newObjs = (Object[]) ArrayUtils.append(old, newArray);
                    newMap.put(key, newObjs);
                } else {
                    newMap.put(key, newArray);
                }
            }
        }

        return newMap;
    }

    /**
     * Merge the given Properties instance into the given Map, copying all properties (key-value
     * pairs) over.
     * <p>
     * Uses <code>Properties.propertyNames()</code> to even catch default properties linked into the
     * original Properties instance.
     *
     * @param props the Properties instance to merge (may be <code>null</code>)
     * @param map the target Map to merge the properties into
     */
    public static void mergePropertiesIntoMap(Properties props, Map<String, String> map) {
        if (map == null) {
            throw new IllegalArgumentException("Map must not be null");
        }
        if (props != null) {
            for (Enumeration<?> en = props.propertyNames(); en.hasMoreElements(); ) {
                String key = (String) en.nextElement();
                map.put(key, props.getProperty(key));
            }
        }
    }

    /**
     * Check whether the given Iterator contains the given element.
     *
     * @param iterator the Iterator to check
     * @param element the element to look for
     * @return <code>true</code> if found, <code>false</code> else
     */
    public static boolean contains(Iterator<?> iterator, Object element) {
        if (iterator != null) {
            while (iterator.hasNext()) {
                Object candidate = iterator.next();
                if (ObjectUtils.nullSafeEquals(candidate, element)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check whether the given Enumeration contains the given element.
     *
     * @param enumeration the Enumeration to check
     * @param element the element to look for
     * @return <code>true</code> if found, <code>false</code> else
     */
    public static boolean contains(Enumeration<?> enumeration, Object element) {
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                Object candidate = enumeration.nextElement();
                if (ObjectUtils.nullSafeEquals(candidate, element)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check whether the given Collection contains the given element instance.
     * <p>
     * Enforces the given instance to be present, rather than returning <code>true</code> for an
     * equal element as well.
     *
     * @param collection the Collection to check
     * @param element the element to look for
     * @return <code>true</code> if found, <code>false</code> else
     */
    public static boolean containsInstance(Collection<?> collection, Object element) {
        if (collection != null) {
            for (Iterator<?> it = collection.iterator(); it.hasNext(); ) {
                Object candidate = it.next();
                if (candidate == element) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return <code>true</code> if any element in '<code>candidates</code>' is contained in
     * '<code>source</code>'; otherwise returns <code>false</code>.
     *
     * @param source the source Collection
     * @param candidates the candidates to search for
     * @return whether any of the candidates has been found
     */
    public static boolean containsAny(Collection<?> source, Collection<?> candidates) {
        if (isEmpty(source) || isEmpty(candidates)) {
            return false;
        }
        for (Iterator<?> it = candidates.iterator(); it.hasNext(); ) {
            if (source.contains(it.next())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return the first element in '<code>candidates</code>' that is contained in
     * '<code>source</code>'. If no element in '<code>candidates</code>' is present in
     * '<code>source</code>' returns <code>null</code>. Iteration order is {@link Collection}
     * implementation specific.
     *
     * @param source the source Collection
     * @param candidates the candidates to search for
     * @return the first present object, or <code>null</code> if not found
     */
    public static Object findFirstMatch(Collection<?> source, Collection<?> candidates) {
        if (isEmpty(source) || isEmpty(candidates)) {
            return null;
        }
        for (Iterator<?> it = candidates.iterator(); it.hasNext(); ) {
            Object candidate = it.next();
            if (source.contains(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    /**
     * Find a value of the given type in the given Collection.
     *
     * @param collection the Collection to search
     * @param type the type to look for
     * @return a value of the given type found, or <code>null</code> if none
     * @throws IllegalArgumentException if more than one value of the given type found
     */
    public static Object findValueOfType(Collection<?> collection, Class<?> type) throws IllegalArgumentException {
        if (isEmpty(collection)) {
            return null;
        }
        Class<?> typeToUse = (type != null ? type : Object.class);
        Object value = null;
        for (Iterator<?> it = collection.iterator(); it.hasNext(); ) {
            Object obj = it.next();
            if (typeToUse.isInstance(obj)) {
                if (value != null) {
                    throw new IllegalArgumentException("More than one value of type [" + typeToUse.getName() + "] found");
                }
                value = obj;
            }
        }
        return value;
    }

    /**
     * Find a value of one of the given types in the given Collection: searching the Collection for
     * a value of the first type, then searching for a value of the second type, etc.
     *
     * @param collection the collection to search
     * @param types the types to look for, in prioritized order
     * @return a of one of the given types found, or <code>null</code> if none
     * @throws IllegalArgumentException if more than one value of the given type found
     */
    @SuppressWarnings("rawtypes")
    public static Object findValueOfType(Collection<?> collection, Class[] types) throws IllegalArgumentException {
        if (isEmpty(collection) || ObjectUtils.isEmpty(types)) {
            return null;
        }
        for (int i = 0; i < types.length; i++) {
            Object value = findValueOfType(collection, types[i]);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    /**
     * Determine whether the given Collection only contains a single unique object.
     *
     * @param collection the Collection to check
     * @return <code>true</code> if the collection contains a single reference or multiple
     * references to the same instance, <code>false</code> else
     */
    public static boolean hasUniqueObject(Collection<?> collection) {
        if (isEmpty(collection)) {
            return false;
        }
        boolean hasCandidate = false;
        Object candidate = null;
        for (Iterator<?> it = collection.iterator(); it.hasNext(); ) {
            Object elem = it.next();
            if (!hasCandidate) {
                hasCandidate = true;
                candidate = elem;
            } else if (candidate != elem) {
                return false;
            }
        }
        return true;
    }

    public static <T, W> Collection<W> getValues(Map<T, W> map) {
        return null;
    }

    public static Collection<Object> getSortedValues(Map<Integer, Object> vParameters) {
        if (vParameters == null || vParameters.size() == 0) {
            return null;
        }

        SortedMap<Integer, Object> sortedMap = new TreeMap<Integer, Object>(vParameters);
        Collection<Object> result = sortedMap.values();
        return result;
    }

    /**
     * 递归打印set
     */
    public static String toString(Set<?> set) {

        if (set == null) {
            return "";
        }
        String s = "{";
        for (Object value : set) {
            Class<? extends Object> c = value.getClass();
            if (value instanceof Map) {
                s = s + (s.length() == 1 ? "" : ",") + "{" + toString((Map<?, ?>) value) + "}";
            } else if (value instanceof List) {
                s = s + (s.length() == 1 ? "" : ",") + "{" + toString((List<?>) value) + "}";
            } else if (value instanceof Set) {
                s = s + (s.length() == 1 ? "" : ",") + "{" + toString((Set<?>) value) + "}";
            } else {

                if (c.isArray()) {
                    s = s + (s.length() == 1 ? "" : ",") + "[" + ArrayUtils.toString(value, ",") + "]";
                } else {
                    s = s + (s.length() == 1 ? "" : ",") + value;
                }
            }
        }
        s = s + "}";
        return s;
    }

    /**
     * 递归打印list
     */
    public static String toString(List<?> list) {

        if (list == null) {
            return "";
        }
        String s = "{";
        for (Object value : list) {
            Class<? extends Object> c = value.getClass();
            if (value instanceof Map) {

                s = s + (s.length() == 1 ? "" : ",") + "{" + toString((Map<?, ?>) value) + "}";
            } else if (value instanceof List) {
                s = s + (s.length() == 1 ? "" : ",") + "{" + toString((List<?>) value) + "}";
            } else if (value instanceof Set) {
                s = s + (s.length() == 1 ? "" : ",") + "{" + toString((Set<?>) value) + "}";
            } else {

                if (c.isArray()) {
                    s = s + (s.length() == 1 ? "" : ",") + "[" + ArrayUtils.toString(value, ",") + "]";
                } else {
                    s = s + (s.length() == 1 ? "" : ",") + "" + value;
                }
            }

        }
        s = s + "}";
        return s;
    }

    /**
     * 递归打印map
     */
    public static String toString(Map<?, ?> map) {
        if (CollectionUtils.isEmpty(map)) {
            return "";
        }

        Set<?> keys = map.keySet();
        String s = "{";
        for (Object key : keys) {
            Object value = map.get(key);
            Class<? extends Object> c = value.getClass();
            if (value instanceof Map) {

                s = s + (s.length() == 1 ? "" : ",") + key + "={" + toString((Map<?, ?>) value) + "}";
            } else if (value instanceof List) {
                s = s + (s.length() == 1 ? "" : ",") + key + "={" + toString((List<?>) value) + "}";
            } else if (value instanceof Set) {
                s = s + (s.length() == 1 ? "" : ",") + key + "={" + toString((Set<?>) value) + "}";
            } else {

                if (c.isArray()) {
                    s = s + (s.length() == 1 ? "" : ",") + key + "=[" + ArrayUtils.toString(value, ",") + "]";
                } else {
                    s = s + (s.length() == 1 ? "" : ",") + key + "=" + value;
                }
            }
        }
        s = s + "}";
        return s;
    }

    /**
     * @param type 用法如下： Type typeOfT = new com.google.gson.reflect.TypeToken<Collection
     * <Foo>>(){}.getType();
     */
    public static String toJsonString(Object obj, Type type) {
        return ObjectUtils.toJsonString(obj, type);
    }

    public static String toJsonString(Object obj) {
        return ObjectUtils.toJsonString(obj);
    }

    @SuppressWarnings("unchecked")
    public static <T> Map<T, T[]> putMap(Map<T, T[]> map, T key, T value) {

        T[] v = map.get(key);
        if (v == null) {
            v = (T[]) Array.newInstance(value.getClass(), 1);
            v[0] = value;
        } else {
            v = (T[]) ArrayUtils.append(v, value);
        }
        map.put(key, v);
        return map;
    }
}
