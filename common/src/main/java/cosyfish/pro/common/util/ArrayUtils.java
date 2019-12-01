package cosyfish.pro.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 各种类型数组的工具方法
 */
public class ArrayUtils {

    private static final Logger log = LoggerFactory.getLogger(ArrayUtils.class);

    public static byte[] append(byte[] src, byte[] append) {
        return org.apache.commons.lang3.ArrayUtils.addAll(src, append);
    }

    public static <T> T getFirst(T[] array) {
        if (ArrayUtils.isEmpty(array)) {
            return null;
        }
        return array[0];
    }

    public static int max(int[] array) {
        int max = array[0];
        for (int i = 0; i < array.length; i++) {
            if (max < array[i]) {
                max = array[i];
            }
        }
        return max;
    }

    public static long max(long[] array) {
        long max = array[0];
        for (int i = 0; i < array.length; i++) {
            if (max < array[i]) {
                max = array[i];
            }
        }
        return max;
    }

    public static double max(double[] array) {
        double max = array[0];
        for (int i = 0; i < array.length; i++) {
            if (max < array[i]) {
                max = array[i];
            }
        }
        return max;
    }

    public static float max(float[] array) {
        float max = array[0];
        for (int i = 0; i < array.length; i++) {
            if (max < array[i]) {
                max = array[i];
            }
        }
        return max;
    }

    public static float min(float[] array) {
        float min = array[0];
        for (int i = 0; i < array.length; i++) {
            if (min > array[i]) {
                min = array[i];
            }
        }
        return min;
    }

    public static int min(int[] array) {
        int min = array[0];
        for (int i = 0; i < array.length; i++) {
            if (min > array[i]) {
                min = array[i];
            }
        }
        return min;
    }

    public static long min(long[] array) {
        long min = array[0];
        for (int i = 0; i < array.length; i++) {
            if (min > array[i]) {
                min = array[i];
            }
        }
        return min;
    }

    public static double min(double[] array) {
        double min = array[0];
        for (int i = 0; i < array.length; i++) {
            if (min > array[i]) {
                min = array[i];
            }
        }
        return min;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] nCopy(T value, int len) {

        Class<? extends Object> c = value.getClass();
        Object array = Array.newInstance(c, len);
        Object[] objs = (Object[]) array;
        Arrays.fill(objs, value);
        return (T[]) objs;
    }

    public static int[] nCopy(int val, int len) {
        int[] array = new int[len];
        Arrays.fill(array, val);
        return array;
    }

    /**
     * 数组里每个元素加前缀
     *
     * @param array 数组
     * @param prefix 需加的前缀
     * @return 返回添加了前缀的数组，返回的数组和原始数组在同一空间
     */
    public static String[] prefix(String[] array, String prefix) {
        for (int i = 0; i < array.length; i++) {
            array[i] = prefix + array[i];
        }
        return array;
    }

    /**
     * 数组里每个元素加前缀
     *
     * @param array 数组
     * @param prefix 需加的前缀
     * @return 返回添加了前缀的数组，返回的数组为一新数组，和原始数组不在同一空间
     */
    public static String[] prefixAndNew(String[] array, String prefix) {
        String[] news = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            news[i] = prefix + array[i];
        }
        return news;
    }

    public static <T> T[] map(T[] array, Callback<T, T> fun) {
        for (int i = 0; i < array.length; i++) {
            T obj = fun.call(array[i]);
            array[i] = obj;
        }
        return array;
    }

    /**
     * 得到元素obj在数组objs里出现的次数
     */
    public static <T> int getOccurrenceFrequence(T[] objs, T obj) {
        int frequence = 0;
        for (T o : objs) {
            if (o.equals(obj)) {
                frequence++;
            }
        }
        return frequence;
    }

    /**
     * 得到重复的元素
     *
     * @return 返回重复元素组成的数组
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] getDuplicateElements(T[] objs) {
        Set<T> list = new LinkedHashSet<T>();
        for (T obj : objs) {
            for (T obj2 : objs) {
                if (obj != obj2 && obj.equals(obj2)) {
                    list.add(obj);
                }
            }
        }
        return list.toArray((T[]) Array.newInstance(objs.getClass().getComponentType(), 0));
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] removeDuplicateElements(T[] objs) {
        Set<T> sets = new LinkedHashSet<T>();
        for (T obj : objs) {
            sets.add(obj);
        }

        return sets.toArray((T[]) Array.newInstance(objs.getClass().getComponentType(), 0));
    }

    public static long[] nCopy(long val, int len) {
        long[] array = new long[len];
        Arrays.fill(array, val);
        return array;
    }

    public static double[] nCopy(double val, int len) {
        double[] array = new double[len];
        Arrays.fill(array, val);
        return array;
    }

    public static float[] nCopy(float val, int len) {
        float[] array = new float[len];
        Arrays.fill(array, val);
        return array;
    }

    public static char[] nCopy(char val, int len) {
        char[] array = new char[len];
        Arrays.fill(array, val);
        return array;
    }

    public static byte[] nCopy(byte val, int len) {
        byte[] array = new byte[len];
        Arrays.fill(array, val);
        return array;
    }

    public static boolean[] nCopy(boolean val, int len) {
        boolean[] array = new boolean[len];
        Arrays.fill(array, val);
        return array;
    }

    /**
     * 向srcArray数组的指定位置末尾添加一个数组或元素
     *
     * @param appendArray 可以为一个数组，也可以为一个元素
     */
    public static Object append(Object srcArray, Object appendArray) {

        Assert.isTrue(srcArray.getClass().isArray(), "srcArray  must be array type!");
        int srcArrayLen = Array.getLength(srcArray);
        return insert(srcArray, srcArrayLen, appendArray);
    }

    /**
     * 在insertPos位置把append数组插入
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] insert(T[] src, int insertPos, T[] append) {
        if (src == null || src.length == 0) {
            return (T[]) append;
        } else if (append == null || append.length == 0) {
            return (T[]) src;
        }
        if (insertPos < 0) {
            return (T[]) src;
        }
        if (insertPos > src.length) {
            insertPos = src.length;
        }
        Object joinedArray = Array.newInstance(src.getClass().getComponentType(), src.length + append.length);

        System.arraycopy(src, 0, joinedArray, 0, insertPos);
        System.arraycopy(append, 0, joinedArray, insertPos, append.length);
        if (insertPos < src.length) {
            System.arraycopy(src, insertPos, joinedArray, insertPos + append.length, src.length - insertPos);
        }

        return (T[]) joinedArray;
    }

    /**
     * 向srcArray数组的指定位置插入一个数组或元素
     *
     * @param appendArray 可以为一个数组，也可以为一个元素
     */
    public static Object insert(Object srcArray, int insertPos, Object append) {

        Assert.isTrue(srcArray.getClass().isArray(), "srcArray  must be array type!");
        if (!append.getClass().isArray()) {
            Object old = append;
            try {
                append = Array.newInstance(srcArray.getClass().getComponentType(), 1);
                Array.set(append, 0, old);
            } catch (Exception ex) {
                Assert.state("the append's type[" + old.getClass() + " ] is not match  the elements's type[" + srcArray.getClass().getComponentType()
                        + "] in srcArray!");
            }
        }
        int srcArrayLen = Array.getLength(srcArray);
        int appendArrayLen = Array.getLength(append);
        if (srcArray == null || srcArrayLen == 0) {
            return append;
        } else if (append == null || appendArrayLen == 0) {
            return srcArray;
        }
        if (insertPos < 0) {
            return srcArray;
        }
        if (insertPos > srcArrayLen) {
            insertPos = srcArrayLen;
        }
        Object joinedArray = Array.newInstance(srcArray.getClass().getComponentType(), srcArrayLen + appendArrayLen);

        System.arraycopy(srcArray, 0, joinedArray, 0, insertPos);
        System.arraycopy(append, 0, joinedArray, insertPos, appendArrayLen);
        if (insertPos < srcArrayLen) {
            System.arraycopy(srcArray, insertPos, joinedArray, insertPos + appendArrayLen, srcArrayLen - insertPos);
        }

        return joinedArray;
    }

    public static boolean contains(String[] arrays, String s) {
        // return org.apache.commons.lang3.ArrayUtils.(array,
        // objectToFind)

        return contains((Object) arrays, s);
    }

    public static boolean contains(Object srcsArray, Object findObj) {
        Assert.state(srcsArray.getClass().isArray(), "srcsArray is must be primitive type array!");
        // boolean isPrimitive = srcsArray.getClass().getComponentType().isPrimitive();
        int len = Array.getLength(srcsArray);
        for (int i = 0; i < len; i++) {
            Object obj = Array.get(srcsArray, i);
            if (obj.equals(findObj)) {
                return true;
            }
        }
        return false;

    }

    // public static boolean isEquals(Object array1, Object array2) {
    // // Arrays.equals(a, a2)
    // return org.apache.commons.lang3.ArrayUtils.isEquals(array1, array2);
    // }

    public static boolean isEmpty(Object array) {
        if (array == null || !array.getClass().isArray() || Array.getLength(array) == 0) {
            return true;
        }

        return false;
    }

    public static boolean hasLength(Object array, int length) {
        if (array != null && array.getClass().isArray() && Array.getLength(array) == length) {
            return true;
        }

        return false;
    }

    public static boolean isNotEmpty(Object array) {
        return !ArrayUtils.isEmpty(array);
    }

    public static int indexOf(Object srcsArray, int startIndex, Object findObj) {
        Assert.state(srcsArray.getClass().isArray(), "srcsArray is must be primitive type array!");
        // boolean isPrimitive = srcsArray.getClass().getComponentType().isPrimitive();
        int len = Array.getLength(srcsArray);
        for (int i = startIndex; i < len; i++) {
            Object obj = Array.get(srcsArray, i);
            if (obj.equals(findObj)) {
                return i;
            }
        }
        return -1;

    }

    public static int indexOf(Object srcsArray, Object findObj) {

        return indexOf(srcsArray, 0, findObj);

    }

    public static Object remove(Object array, int index) {
        int length = Array.getLength(array);
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }

        Object result = Array.newInstance(array.getClass().getComponentType(), length - 1);
        System.arraycopy(array, 0, result, 0, index);
        if (index < length - 1) {
            System.arraycopy(array, index + 1, result, index, length - index - 1);
        }

        return result;
    }

    public static <T> T[] fill(T[] arrays, T value) {
        Arrays.fill(arrays, value);
        return arrays;
    }

    public static String[] ObjectListToStringArray(List<? extends Object> list) {
        String[] rets = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            rets[i] = list.get(i).toString();
        }
        return rets;
    }

    /**
     * 把基本类型数组转换成封装类型数组
     */
    public static Object[] toObjectArray(Object source) {

        // Assert.state(primitiveArray.getClass().isArray()
        // && primitiveArray.getClass().getComponentType().isPrimitive(),
        // "primitiveArray is must be primitive type array!");
        // int len = Array.getLength(primitiveArray);
        // Class c = primitiveArray.getClass().getComponentType();
        // if (c == int.class)
        // c = Integer.class;
        // else if (c == boolean.class)
        // c = Boolean.class;
        // else if (c == short.class)
        // c = Short.class;
        // else if (c == long.class)
        // c = Long.class;
        // else if (c == double.class)
        // c = Double.class;
        // else if (c == float.class)
        // c = Float.class;
        // else if (c == byte.class)
        // c = Byte.class;
        // else if (c == char.class)
        // c = Character.class;
        // Object result = Array.newInstance(c, len);
        // for (int i = 0; i < len; i++) {
        // Array.set(result, i, Array.get(primitiveArray, i));
        // // result[i]=Array.get(primitiveArray, i);
        // }
        //
        // return (Object[])result;

        return ObjectUtils.toObjectArray(source);

    }

    /**
     * 从封装类型数组转换成基本类型数组
     */
    public static Object toPrimitiveArray(Object[] srcArray) {

        Assert.state(srcArray.getClass().isArray(), "srcArray is must be a array!");
        int len = Array.getLength(srcArray);
        Class<?> c = srcArray.getClass().getComponentType();
        if (c == Integer.class) {
            c = int.class;
        } else if (c == Boolean.class) {
            c = boolean.class;
        } else if (c == Short.class) {
            c = short.class;
        } else if (c == Long.class) {
            c = long.class;
        } else if (c == Double.class) {
            c = double.class;
        } else if (c == Float.class) {
            c = float.class;
        } else if (c == Byte.class) {
            c = byte.class;
        } else if (c == Character.class) {
            c = char.class;
        }
        Object result = Array.newInstance(c, len);
        for (int i = 0; i < len; i++) {
            Array.set(result, i, Array.get(srcArray, i));
            // result[i]=Array.get(primitiveArray, i);
        }

        return result;
    }

    public static int[] StringArrayToIntArray(String[] values) {
        int[] intValues = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            intValues[i] = Integer.valueOf(values[i]);
        }
        return intValues;
    }

    public static long[] StringArrayToLongArray(String[] values) {
        long[] longValues = new long[values.length];
        for (int i = 0; i < values.length; i++) {
            longValues[i] = Long.parseLong(values[i], 10);
        }
        return longValues;
    }

    public static float[] StringArrayToFloatArray(String[] values) {
        float[] longValues = new float[values.length];
        for (int i = 0; i < values.length; i++) {
            longValues[i] = Float.parseFloat(values[i]);
        }
        return longValues;
    }

    public static double[] StringArrayToDoubleArray(String[] values) {
        double[] longValues = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            longValues[i] = Double.parseDouble(values[i]);
        }
        return longValues;
    }

    public static String[] trim(String[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i].trim();
        }
        return array;
    }

    /**
     *
     */
    public static Object[] getBytesFromLenByteArray(byte[] b_data) {
        ByteArrayInputStream inout = new ByteArrayInputStream(b_data);
        DataInputStream in = new DataInputStream(inout);
        try {
            ArrayList<byte[]> list = new ArrayList<byte[]>();
            int len = 0;
            while (true) {
                try {
                    len = in.readInt();
                    byte[] bs = new byte[len];
                    // in.read(bs);
                    // 读满一个数组
                    int bytesRead = 0;
                    while (bytesRead < len) {

                        int result = in.read(bs, bytesRead, len - bytesRead);

                        if (result == -1) {
                            break;
                        }

                        bytesRead += result;

                    }
                    if (bytesRead < len) {
                        bs = Arrays.copyOfRange(bs, 0, bytesRead);
                    }
                    list.add(bs);
                } catch (Exception ex) {
                    break;
                }

            }

            Object[] objs = list.toArray();
            return objs;
        } catch (Exception e) {
            log.error("" + e.getMessage());
        } finally {
            try {
                in.close();
            } catch (IOException e) {
            }
        }
        return null;
    }

    public static byte[] addLenToByteArrayHead(byte[] b_data) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();// 缓存
        DataOutputStream dos = new DataOutputStream(byteOut);
        try {
            if (ArrayUtils.isEmpty(b_data)) {
                dos.writeInt(0);
            } else {
                dos.writeInt(b_data.length);
                dos.write(b_data);
            }
        } catch (Exception e) {
            log.error("" + e.getMessage());
        } finally {
            try {
                dos.close();
            } catch (IOException e) {
                log.error("" + e.getMessage());
            }
        }
        return byteOut.toByteArray();
    }

    public static Integer[] IntegerListToIntegerArray(List<Integer> l) {
        if (l == null || l.size() == 0) {
            return null;
        }

        return (Integer[]) ArrayUtils.toArray(l);

    }

    public static Long[] LongListToLongArray(List<Long> l) {
        if (l == null || l.size() == 0) {
            return null;
        }

        return (Long[]) ArrayUtils.toArray(l);

    }

    public static Float[] FloatListToFloatArray(List<Float> l) {
        if (l == null || l.size() == 0) {
            return null;
        }

        return (Float[]) ArrayUtils.toArray(l);

    }

    public static Double[] DoubleListToDoubleArray(List<Double> l) {
        if (l == null || l.size() == 0) {
            return null;
        }

        return (Double[]) ArrayUtils.toArray(l);

    }

    public static void copy(Object src, int srcPos, Object dest, int destPos, int length) {

        System.arraycopy(src, srcPos, dest, destPos, length);
    }

    /**
     * 列表转数组，记得最后返回的对象数组在外面要被强制转换成实际类型数组
     */
    public static <T> Object[] toArray(List<T> list) {

        Assert.notEmpty(list);
        Class<? extends Object> c = list.get(0).getClass();

        if (c == Integer.class) {
            return list.toArray(new Integer[0]);
        } else if (c == Boolean.class) {
            return list.toArray(new Boolean[0]);
        } else if (c == Short.class) {
            return list.toArray(new Short[0]);
        } else if (c == Long.class) {
            return list.toArray(new Long[0]);
        } else if (c == Double.class) {
            return list.toArray(new Double[0]);
        } else if (c == Float.class) {
            return list.toArray(new Float[0]);
        } else if (c == Byte.class) {
            return list.toArray(new Byte[0]);
        } else if (c == Character.class) {
            return list.toArray(new Character[0]);
        } else if (c == String.class) {
            return list.toArray(new String[0]);
        } else {

            Object[] result = (Object[]) Array.newInstance(c, 1);
            return list.toArray(result);
        }

    }

    /**
     * 任意类型转换成String数组
     */
    public static String[] ListToStringArray(List<?> l) {
        if (l == null || l.size() == 0) {
            return null;
        }

        Object[] obs = l.toArray();
        String[] strs = new String[obs.length];
        int i = 0;
        for (Object obj : obs) {
            strs[i++] = obj.toString();
        }
        return strs;

    }

    // public static String toString(int[] arrays) {
    // return Arrays.toString(arrays);
    // }
    //
    // public static String toString(float[] arrays) {
    // return Arrays.toString(arrays);
    // }

    // public static String toString(double[] arrays) {
    // return Arrays.toString(arrays);
    // }
    //
    // public static String toString(long[] arrays) {
    // return Arrays.toString(arrays);
    // }

    // public static String toString(boolean[] arrays) {
    // return Arrays.toString(arrays);
    // }
    //
    // public static String toString(byte[] arrays) {
    // return Arrays.toString(arrays);
    // }
    //
    // public static String toString(short[] arrays) {
    //
    // return Arrays.toString(arrays);
    // }
    //
    // public static String toString(String[] arrays) {
    // return Arrays.toString(arrays);
    // }
    //
    // public static String toString(char[] arrays) {
    // return Arrays.toString(arrays);
    // }
    //
    // public static String toString(Object[] objs) {
    //
    // return Arrays.toString(objs);
    // }

    public static void reverse(Object array) {
        Assert.state(array.getClass().isArray(), "array must be a array type!");
        Class<?> c = array.getClass().getComponentType();
        if (c.isPrimitive()) {
            if (c == int.class) {
                org.apache.commons.lang3.ArrayUtils.reverse((int[]) array);
            } else if (c == boolean.class) {
                org.apache.commons.lang3.ArrayUtils.reverse((boolean[]) array);
            } else if (c == short.class) {
                org.apache.commons.lang3.ArrayUtils.reverse((short[]) array);
            } else if (c == long.class) {
                org.apache.commons.lang3.ArrayUtils.reverse((long[]) array);
            } else if (c == double.class) {
                org.apache.commons.lang3.ArrayUtils.reverse((double[]) array);
            } else if (c == float.class) {
                org.apache.commons.lang3.ArrayUtils.reverse((float[]) array);
            } else if (c == byte.class) {
                org.apache.commons.lang3.ArrayUtils.reverse((byte[]) array);
            } else if (c == char.class) {
                org.apache.commons.lang3.ArrayUtils.reverse((char[]) array);
            }
        } else {
            org.apache.commons.lang3.ArrayUtils.reverse((Object[]) array);
        }

    }

    public static String toString(Object array, String delimiterStr) {
        if (array == null) {
            return "";
        }
        Assert.state(array.getClass().isArray(), "array must be a array type!");

        String result = "";
        int len = Array.getLength(array);

        for (int i = 0; i < len; i++) {
            Object obj = Array.get(array, i);
            String s = "";
            if (obj.getClass().isArray()) {
                s = "[" + toString(obj, ",") + "]";
            } else {
                s = obj.toString();
            }
            result = result + "" + s;
            if (i != len - 1) {
                result = result + delimiterStr;
            }

        }
        return result;

    }

    public static String toString(Object array) {
        return toString(array, " ");
    }

    /**
     * @param type 用法如下： Type typeOfT = new
     * com.google.gson.reflect.TypeToken<Collection<Foo>>(){}.getType();
     */
    public static String toJsonString(Object obj, Type type) {
        return ObjectUtils.toJsonString(obj, type);
    }

    public static String toJsonString(Object obj) {
        return ObjectUtils.toJsonString(obj);
    }

    public static int[] StringListToIntArray(List<String> l) {
        if (l == null || l.size() == 0) {
            return null;
        }
        int[] res = new int[l.size()];
        for (int i = 0; i < l.size(); i++) {
            res[i] = Integer.valueOf((String) l.get(i)).intValue();
        }
        return res;
    }

    public static long[] StringListToLongArray(List<String> l) {
        if (l == null || l.size() == 0) {
            return null;
        }
        long[] res = new long[l.size()];
        for (int i = 0; i < l.size(); i++) {
            res[i] = Long.valueOf((String) l.get(i)).longValue();
        }
        return res;
    }

    public static String[] StringListToStringArray(List<String> l) {
        return l.toArray(new String[0]);
    }

    public static float[] StringListToFloatArray(List<String> l) {
        if (l == null || l.size() == 0) {
            return null;
        }
        float[] res = new float[l.size()];
        for (int i = 0; i < l.size(); i++) {
            res[i] = Float.valueOf((String) l.get(i)).floatValue();
        }
        return res;
    }

    public static double[] StringListToDoubleArray(List<String> l) {
        if (l == null || l.size() == 0) {
            return null;
        }
        double[] res = new double[l.size()];
        for (int i = 0; i < l.size(); i++) {
            res[i] = Double.valueOf((String) l.get(i)).doubleValue();
        }
        return res;
    }
}
