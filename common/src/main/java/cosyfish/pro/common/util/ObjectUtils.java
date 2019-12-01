package cosyfish.pro.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Arrays;

/**
 * Miscellaneous object utility methods. Mainly for internal use within the framework; consider
 * Jakarta's Commons Lang for a more comprehensive suite of object utilities.
 *
 * @author Juergen Hoeller
 * @author Keith Donald
 * @author Rod Johnson
 * @author Rob Harrop
 * @author Alex Ruiz
 * @see org.apache.commons.lang3.ObjectUtils
 * @since 19.03.2004
 */
public abstract class ObjectUtils {
    private static final Logger log = LoggerFactory.getLogger(ObjectUtils.class);
    private static final int INITIAL_HASH = 7;
    private static final int MULTIPLIER = 31;

    private static final String EMPTY_STRING = "";
    private static final String NULL_STRING = "null";
    private static final String ARRAY_START = "{";
    private static final String ARRAY_END = "}";
    private static final String EMPTY_ARRAY = ARRAY_START + ARRAY_END;
    private static final String ARRAY_ELEMENT_SEPARATOR = ", ";

    /**
     * Return whether the given throwable is a checked exception: that is, neither a
     * RuntimeException nor an Error.
     *
     * @param ex the throwable to check
     * @return whether the throwable is a checked exception
     * @see Exception
     * @see RuntimeException
     * @see Error
     */
    public static boolean isCheckedException(Throwable ex) {
        return !(ex instanceof RuntimeException || ex instanceof Error);
    }

    /**
     * @param o 要序列化的对象，必须遵循java序列化对象的要求,即对象是可序列化的
     * @return 对象对象的字节数组
     */
    public static byte[] serializeObject(Object o) throws Exception {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        if (o == null) {
            return null;
        }

        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(o);
            oos.flush();
            return bos.toByteArray();
        } finally {
            if (oos != null) {
                oos.close();
            }
        }
    }

    /**
     * @param data 对象序列化字节流
     * @return 反序列化的对象
     */
    public static Object deSerializeObject(byte[] data) throws Exception {
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        if (data == null || data.length == 0) {
            return null;
        }
        try {
            bis = new ByteArrayInputStream(data);
            ois = new ObjectInputStream(bis);
            return ois.readObject();
        } finally {
            if (ois != null) {
                ois.close();
            }
        }
    }

    // 序列化对象
    public static String converToString(Object value, boolean isCompress) throws Exception {
        if (value != null)// 如果要设置的值非空，那么就需要先序列化，然后转化为String
        {
            try {
                byte[] b = serializeObject(value);
                if (isCompress) {
                    b = ZipUtils.gzip(b);
                }
                String result = new String(b, "iso-8859-1");
                log.error("converToString===" + result);
                return result;
            } catch (Exception e) {
                // e.printStackTrace();
                throw e;
            }

        } else {
            return null;
        }

    }

    // 反序列化对象
    public static Object converToObject(String value, boolean isCompress) throws Exception {
        log.error("converToObject1111111111===" + value);
        try {
            if (value != null) {
                byte[] b = value.getBytes("iso-8859-1");
                if (isCompress) {
                    b = ZipUtils.ungzip(b);
                }
                // Object
                // result=deSerializeObject(ZipUtils.ungzip(value.getBytes("iso-8859-1")));
                Object result = deSerializeObject(b);
                return result;
            } else {
                return null;
            }

        } catch (Exception e) {
            // e.printStackTrace();
            throw e;
        }
    }

    /**
     * Check whether the given exception is compatible with the exceptions declared in a throws
     * clause.
     *
     * @param ex the exception to checked
     * @param declaredExceptions the exceptions declared in the throws clause
     * @return whether the given exception is compatible
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static boolean isCompatibleWithThrowsClause(Throwable ex, Class[] declaredExceptions) {
        if (!isCheckedException(ex)) {
            return true;
        }
        if (declaredExceptions != null) {
            for (int i = 0; i < declaredExceptions.length; i++) {
                if (declaredExceptions[i].isAssignableFrom(ex.getClass())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return whether the given array is empty: that is, <code>null</code> or of zero length.
     *
     * @param array the array to check
     * @return whether the given array is empty
     */
    public static boolean isEmpty(Object[] array) {
        return (array == null || array.length == 0);
    }

    /**
     * Append the given Object to the given array, returning a new array consisting of the input
     * array contents plus the given Object.
     *
     * @param array the array to append to (can be <code>null</code>)
     * @param obj the Object to append
     * @return the new array (of the same component type; never <code>null</code>)
     */
    public static Object[] addObjectToArray(Object[] array, Object obj) {
        Class<?> compType = Object.class;
        if (array != null) {
            compType = array.getClass().getComponentType();
        } else if (obj != null) {
            compType = obj.getClass();
        }
        int newArrLength = (array != null ? array.length + 1 : 1);
        Object[] newArr = (Object[]) Array.newInstance(compType, newArrLength);
        if (array != null) {
            System.arraycopy(array, 0, newArr, 0, array.length);
        }
        newArr[newArr.length - 1] = obj;
        return newArr;
    }

    /**
     * Convert the given array (which may be a primitive array) to an object array (if necessary of
     * primitive wrapper objects).
     * <p>
     * A <code>null</code> source value will be converted to an empty Object array.
     *
     * @param source the (potentially primitive) array
     * @return the corresponding object array (never <code>null</code>)
     * @throws IllegalArgumentException if the parameter is not an array
     */
    public static Object[] toObjectArray(Object source) {
        if (source instanceof Object[]) {
            return (Object[]) source;
        }
        if (source == null) {
            return new Object[0];
        }
        if (!source.getClass().isArray()) {
            throw new IllegalArgumentException("Source is not an array: " + source);
        }

        int length = Array.getLength(source);
        if (length == 0) {
            return new Object[0];
        }
        Class<?> wrapperType = Array.get(source, 0).getClass();
        Object[] newArray = (Object[]) Array.newInstance(wrapperType, length);
        for (int i = 0; i < length; i++) {
            newArray[i] = Array.get(source, i);
        }
        return newArray;
    }

    // ---------------------------------------------------------------------
    // Convenience methods for content-based equality/hash-code handling
    // ---------------------------------------------------------------------

    /**
     * Determine if the given objects are equal, returning <code>true</code> if both are
     * <code>null</code> or <code>false</code> if only one is <code>null</code>.
     * <p>
     * Compares arrays with <code>Arrays.equals</code>, performing an equality check based on the
     * array elements rather than the array reference.
     *
     * @param o1 first Object to compare
     * @param o2 second Object to compare
     * @return whether the given objects are equal
     * @see Arrays#equals
     */
    public static boolean nullSafeEquals(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        if (o1.equals(o2)) {
            return true;
        }
        if (o1 instanceof Object[] && o2 instanceof Object[]) {
            return Arrays.equals((Object[]) o1, (Object[]) o2);
        }
        if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
            return Arrays.equals((boolean[]) o1, (boolean[]) o2);
        }
        if (o1 instanceof byte[] && o2 instanceof byte[]) {
            return Arrays.equals((byte[]) o1, (byte[]) o2);
        }
        if (o1 instanceof char[] && o2 instanceof char[]) {
            return Arrays.equals((char[]) o1, (char[]) o2);
        }
        if (o1 instanceof double[] && o2 instanceof double[]) {
            return Arrays.equals((double[]) o1, (double[]) o2);
        }
        if (o1 instanceof float[] && o2 instanceof float[]) {
            return Arrays.equals((float[]) o1, (float[]) o2);
        }
        if (o1 instanceof int[] && o2 instanceof int[]) {
            return Arrays.equals((int[]) o1, (int[]) o2);
        }
        if (o1 instanceof long[] && o2 instanceof long[]) {
            return Arrays.equals((long[]) o1, (long[]) o2);
        }
        if (o1 instanceof short[] && o2 instanceof short[]) {
            return Arrays.equals((short[]) o1, (short[]) o2);
        }
        return false;
    }

    /**
     * Return as hash code for the given object; typically the value of
     * <code>{@link Object#hashCode()}</code>. If the object is an array, this method will delegate
     * to any of the <code>nullSafeHashCode</code> methods for arrays in this class. If the object
     * is <code>null</code>, this method returns 0.
     *
     * @see #nullSafeHashCode(Object[])
     * @see #nullSafeHashCode(boolean[])
     * @see #nullSafeHashCode(byte[])
     * @see #nullSafeHashCode(char[])
     * @see #nullSafeHashCode(double[])
     * @see #nullSafeHashCode(float[])
     * @see #nullSafeHashCode(int[])
     * @see #nullSafeHashCode(long[])
     * @see #nullSafeHashCode(short[])
     */
    public static int nullSafeHashCode(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            return nullSafeHashCode((Object[]) obj);
        }
        if (obj instanceof boolean[]) {
            return nullSafeHashCode((boolean[]) obj);
        }
        if (obj instanceof byte[]) {
            return nullSafeHashCode((byte[]) obj);
        }
        if (obj instanceof char[]) {
            return nullSafeHashCode((char[]) obj);
        }
        if (obj instanceof double[]) {
            return nullSafeHashCode((double[]) obj);
        }
        if (obj instanceof float[]) {
            return nullSafeHashCode((float[]) obj);
        }
        if (obj instanceof int[]) {
            return nullSafeHashCode((int[]) obj);
        }
        if (obj instanceof long[]) {
            return nullSafeHashCode((long[]) obj);
        }
        if (obj instanceof short[]) {
            return nullSafeHashCode((short[]) obj);
        }
        return obj.hashCode();
    }

    /**
     * Return a hash code based on the contents of the specified array. If <code>array</code> is
     * <code>null</code>, this method returns 0.
     */
    public static int nullSafeHashCode(Object[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        int arraySize = array.length;
        for (int i = 0; i < arraySize; i++) {
            hash = MULTIPLIER * hash + nullSafeHashCode(array[i]);
        }
        return hash;
    }

    /**
     * Return a hash code based on the contents of the specified array. If <code>array</code> is
     * <code>null</code>, this method returns 0.
     */
    public static int nullSafeHashCode(boolean[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        int arraySize = array.length;
        for (int i = 0; i < arraySize; i++) {
            hash = MULTIPLIER * hash + hashCode(array[i]);
        }
        return hash;
    }

    /**
     * Return a hash code based on the contents of the specified array. If <code>array</code> is
     * <code>null</code>, this method returns 0.
     */
    public static int nullSafeHashCode(byte[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        int arraySize = array.length;
        for (int i = 0; i < arraySize; i++) {
            hash = MULTIPLIER * hash + array[i];
        }
        return hash;
    }

    /**
     * Return a hash code based on the contents of the specified array. If <code>array</code> is
     * <code>null</code>, this method returns 0.
     */
    public static int nullSafeHashCode(char[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        int arraySize = array.length;
        for (int i = 0; i < arraySize; i++) {
            hash = MULTIPLIER * hash + array[i];
        }
        return hash;
    }

    /**
     * Return a hash code based on the contents of the specified array. If <code>array</code> is
     * <code>null</code>, this method returns 0.
     */
    public static int nullSafeHashCode(double[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        int arraySize = array.length;
        for (int i = 0; i < arraySize; i++) {
            hash = MULTIPLIER * hash + hashCode(array[i]);
        }
        return hash;
    }

    /**
     * Return a hash code based on the contents of the specified array. If <code>array</code> is
     * <code>null</code>, this method returns 0.
     */
    public static int nullSafeHashCode(float[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        int arraySize = array.length;
        for (int i = 0; i < arraySize; i++) {
            hash = MULTIPLIER * hash + hashCode(array[i]);
        }
        return hash;
    }

    /**
     * Return a hash code based on the contents of the specified array. If <code>array</code> is
     * <code>null</code>, this method returns 0.
     */
    public static int nullSafeHashCode(int[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        int arraySize = array.length;
        for (int i = 0; i < arraySize; i++) {
            hash = MULTIPLIER * hash + array[i];
        }
        return hash;
    }

    /**
     * Return a hash code based on the contents of the specified array. If <code>array</code> is
     * <code>null</code>, this method returns 0.
     */
    public static int nullSafeHashCode(long[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        int arraySize = array.length;
        for (int i = 0; i < arraySize; i++) {
            hash = MULTIPLIER * hash + hashCode(array[i]);
        }
        return hash;
    }

    /**
     * Return a hash code based on the contents of the specified array. If <code>array</code> is
     * <code>null</code>, this method returns 0.
     */
    public static int nullSafeHashCode(short[] array) {
        if (array == null) {
            return 0;
        }
        int hash = INITIAL_HASH;
        int arraySize = array.length;
        for (int i = 0; i < arraySize; i++) {
            hash = MULTIPLIER * hash + array[i];
        }
        return hash;
    }

    /**
     * Return the same value as <code>{@link Boolean#hashCode()}</code>.
     *
     * @see Boolean#hashCode()
     */
    public static int hashCode(boolean bool) {
        return bool ? 1231 : 1237;
    }

    /**
     * Return the same value as <code>{@link Double#hashCode()}</code>.
     *
     * @see Double#hashCode()
     */
    public static int hashCode(double dbl) {
        long bits = Double.doubleToLongBits(dbl);
        return hashCode(bits);
    }

    /**
     * Return the same value as <code>{@link Float#hashCode()}</code>.
     *
     * @see Float#hashCode()
     */
    public static int hashCode(float flt) {
        return Float.floatToIntBits(flt);
    }

    /**
     * Return the same value as <code>{@link Long#hashCode()}</code>.
     *
     * @see Long#hashCode()
     */
    public static int hashCode(long lng) {
        return (int) (lng ^ (lng >>> 32));
    }

    // ---------------------------------------------------------------------
    // Convenience methods for toString output
    // ---------------------------------------------------------------------

    /**
     * Return a String representation of an object's overall identity.
     *
     * @param obj the object (may be <code>null</code>)
     * @return the object's identity as String representation, or <code>null</code> if the object
     * was <code>null</code>
     */
    public static String identityToString(Object obj) {
        if (obj == null) {
            return EMPTY_STRING;
        }
        return obj.getClass().getName() + "@" + getIdentityHexString(obj);
    }

    /**
     * Return a hex String form of an object's identity hash code.
     *
     * @param obj the object
     * @return the object's identity code in hex notation
     */
    public static String getIdentityHexString(Object obj) {
        return Integer.toHexString(System.identityHashCode(obj));
    }

    /**
     * Return a content-based String representation if <code>obj</code> is not <code>null</code>;
     * otherwise returns an empty String.
     * <p>
     * Differs from {@link #nullSafeToString(Object)} in that it returns an empty String rather than
     * "null" for a <code>null</code> value.
     *
     * @param obj the object to build a display String for
     * @return a display String representation of <code>obj</code>
     * @see #nullSafeToString(Object)
     */
    public static String getDisplayString(Object obj) {
        if (obj == null) {
            return EMPTY_STRING;
        }
        return nullSafeToString(obj);
    }

    /**
     * Determine the class name for the given object.
     * <p>
     * Returns <code>"null"</code> if <code>obj</code> is <code>null</code>.
     *
     * @param obj the object to introspect (may be <code>null</code>)
     * @return the corresponding class name
     */
    public static String nullSafeClassName(Object obj) {
        return (obj != null ? obj.getClass().getName() : NULL_STRING);
    }

    /**
     * Return a String representation of the specified Object.
     * <p>
     * Builds a String representation of the contents in case of an array. Returns
     * <code>"null"</code> if <code>obj</code> is <code>null</code>.
     *
     * @param obj the object to build a String representation for
     * @return a String representation of <code>obj</code>
     */
    public static String nullSafeToString(Object obj) {
        if (obj == null) {
            return NULL_STRING;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof Object[]) {
            return nullSafeToString((Object[]) obj);
        }
        if (obj instanceof boolean[]) {
            return nullSafeToString((boolean[]) obj);
        }
        if (obj instanceof byte[]) {
            return nullSafeToString((byte[]) obj);
        }
        if (obj instanceof char[]) {
            return nullSafeToString((char[]) obj);
        }
        if (obj instanceof double[]) {
            return nullSafeToString((double[]) obj);
        }
        if (obj instanceof float[]) {
            return nullSafeToString((float[]) obj);
        }
        if (obj instanceof int[]) {
            return nullSafeToString((int[]) obj);
        }
        if (obj instanceof long[]) {
            return nullSafeToString((long[]) obj);
        }
        if (obj instanceof short[]) {
            return nullSafeToString((short[]) obj);
        }
        String str = obj.toString();
        return (str != null ? str : EMPTY_STRING);
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>
     * The String representation consists of a list of the array's elements, enclosed in curly
     * braces (<code>"{}"</code>). Adjacent elements are separated by the characters
     * <code>", "</code> (a comma followed by a space). Returns <code>"null"</code> if
     * <code>array</code> is <code>null</code>.
     *
     * @param array the array to build a String representation for
     * @return a String representation of <code>array</code>
     */
    public static String nullSafeToString(Object[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                buffer.append(ARRAY_START);
            } else {
                buffer.append(ARRAY_ELEMENT_SEPARATOR);
            }
            buffer.append(String.valueOf(array[i]));
        }
        buffer.append(ARRAY_END);
        return buffer.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>
     * The String representation consists of a list of the array's elements, enclosed in curly
     * braces (<code>"{}"</code>). Adjacent elements are separated by the characters
     * <code>", "</code> (a comma followed by a space). Returns <code>"null"</code> if
     * <code>array</code> is <code>null</code>.
     *
     * @param array the array to build a String representation for
     * @return a String representation of <code>array</code>
     */
    public static String nullSafeToString(boolean[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                buffer.append(ARRAY_START);
            } else {
                buffer.append(ARRAY_ELEMENT_SEPARATOR);
            }

            buffer.append(array[i]);
        }
        buffer.append(ARRAY_END);
        return buffer.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>
     * The String representation consists of a list of the array's elements, enclosed in curly
     * braces (<code>"{}"</code>). Adjacent elements are separated by the characters
     * <code>", "</code> (a comma followed by a space). Returns <code>"null"</code> if
     * <code>array</code> is <code>null</code>.
     *
     * @param array the array to build a String representation for
     * @return a String representation of <code>array</code>
     */
    public static String nullSafeToString(byte[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                buffer.append(ARRAY_START);
            } else {
                buffer.append(ARRAY_ELEMENT_SEPARATOR);
            }
            buffer.append(array[i]);
        }
        buffer.append(ARRAY_END);
        return buffer.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>
     * The String representation consists of a list of the array's elements, enclosed in curly
     * braces (<code>"{}"</code>). Adjacent elements are separated by the characters
     * <code>", "</code> (a comma followed by a space). Returns <code>"null"</code> if
     * <code>array</code> is <code>null</code>.
     *
     * @param array the array to build a String representation for
     * @return a String representation of <code>array</code>
     */
    public static String nullSafeToString(char[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                buffer.append(ARRAY_START);
            } else {
                buffer.append(ARRAY_ELEMENT_SEPARATOR);
            }
            buffer.append("'").append(array[i]).append("'");
        }
        buffer.append(ARRAY_END);
        return buffer.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>
     * The String representation consists of a list of the array's elements, enclosed in curly
     * braces (<code>"{}"</code>). Adjacent elements are separated by the characters
     * <code>", "</code> (a comma followed by a space). Returns <code>"null"</code> if
     * <code>array</code> is <code>null</code>.
     *
     * @param array the array to build a String representation for
     * @return a String representation of <code>array</code>
     */
    public static String nullSafeToString(double[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                buffer.append(ARRAY_START);
            } else {
                buffer.append(ARRAY_ELEMENT_SEPARATOR);
            }

            buffer.append(array[i]);
        }
        buffer.append(ARRAY_END);
        return buffer.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>
     * The String representation consists of a list of the array's elements, enclosed in curly
     * braces (<code>"{}"</code>). Adjacent elements are separated by the characters
     * <code>", "</code> (a comma followed by a space). Returns <code>"null"</code> if
     * <code>array</code> is <code>null</code>.
     *
     * @param array the array to build a String representation for
     * @return a String representation of <code>array</code>
     */
    public static String nullSafeToString(float[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                buffer.append(ARRAY_START);
            } else {
                buffer.append(ARRAY_ELEMENT_SEPARATOR);
            }

            buffer.append(array[i]);
        }
        buffer.append(ARRAY_END);
        return buffer.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>
     * The String representation consists of a list of the array's elements, enclosed in curly
     * braces (<code>"{}"</code>). Adjacent elements are separated by the characters
     * <code>", "</code> (a comma followed by a space). Returns <code>"null"</code> if
     * <code>array</code> is <code>null</code>.
     *
     * @param array the array to build a String representation for
     * @return a String representation of <code>array</code>
     */
    public static String nullSafeToString(int[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                buffer.append(ARRAY_START);
            } else {
                buffer.append(ARRAY_ELEMENT_SEPARATOR);
            }
            buffer.append(array[i]);
        }
        buffer.append(ARRAY_END);
        return buffer.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>
     * The String representation consists of a list of the array's elements, enclosed in curly
     * braces (<code>"{}"</code>). Adjacent elements are separated by the characters
     * <code>", "</code> (a comma followed by a space). Returns <code>"null"</code> if
     * <code>array</code> is <code>null</code>.
     *
     * @param array the array to build a String representation for
     * @return a String representation of <code>array</code>
     */
    public static String nullSafeToString(long[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                buffer.append(ARRAY_START);
            } else {
                buffer.append(ARRAY_ELEMENT_SEPARATOR);
            }
            buffer.append(array[i]);
        }
        buffer.append(ARRAY_END);
        return buffer.toString();
    }

    /**
     * Return a String representation of the contents of the specified array.
     * <p>
     * The String representation consists of a list of the array's elements, enclosed in curly
     * braces (<code>"{}"</code>). Adjacent elements are separated by the characters
     * <code>", "</code> (a comma followed by a space). Returns <code>"null"</code> if
     * <code>array</code> is <code>null</code>.
     *
     * @param array the array to build a String representation for
     * @return a String representation of <code>array</code>
     */
    public static String nullSafeToString(short[] array) {
        if (array == null) {
            return NULL_STRING;
        }
        int length = array.length;
        if (length == 0) {
            return EMPTY_ARRAY;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                buffer.append(ARRAY_START);
            } else {
                buffer.append(ARRAY_ELEMENT_SEPARATOR);
            }
            buffer.append(array[i]);
        }
        buffer.append(ARRAY_END);
        return buffer.toString();
    }

    /**
     * 此方法不支持本身就是泛型的对象，如type为 List<String>
     *
     * @param type ，type不能为本身是泛型类型的类型，如List<Stirng>
     */
    public static Object fromJsonToObject(String json, Class<?> type) {
        Gson gson = new GsonBuilder().setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
        Object target = gson.fromJson(json, type);
        return target;
    }

    /**
     * @param type 用法 如下： Type typeOfT = new
     * com.google.gson.reflect.TypeToken<Collection<Foo>>(){}.getType();
     */
    public static Object fromJsonToObject(String json, Type type) {
        Gson gson = new GsonBuilder().setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
        Object target = gson.fromJson(json, type);
        return target;
    }

    public static String toJsonString(Object obj) {
        Gson gson = new GsonBuilder().setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
        String target = gson.toJson(obj);
        return target;
    }

    public static String toJsonString(Object obj, Type type) {
        Gson gson = new GsonBuilder().setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
        String target = gson.toJson(obj, type);
        return target;
    }

    public static String toString(Object obj) {
        if (obj == null) {
            return "";
        }
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.disableHtmlEscaping();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(obj);
    }

    /**
     * @param type 用法如下： Type typeOfT = new
     * com.google.gson.reflect.TypeToken<Collection<Foo>>(){}.getType();
     */
    public static String toString(Object obj, Type type) {
        if (obj == null) {
            return "";
        }
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.disableHtmlEscaping();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        return gson.toJson(obj, type);
    }

    /**
     * 目前只支持字符串和整型
     *
     * 如果新对象里面的字段为空，就用旧对象的替换
     */
    public static <T> void copyNotBlankPropertiesToNewObj(T newObj, T oldObj) {

        Field[] fields = newObj.getClass().getDeclaredFields();

        Method getMethod = null;
        Method newObjSetMethod = null;

        String getMethodName = null;
        String setMethodName = null;

        for (Field field : fields) {

            String name = field.getName();
            int length = name.length();

            getMethodName = new StringBuffer("get").append(name.substring(0, 1).toUpperCase()).append(name.substring(1, length)).toString();

            setMethodName = new StringBuffer("set").append(name.substring(0, 1).toUpperCase()).append(name.substring(1, length)).toString();

            try {
                getMethod = oldObj.getClass().getDeclaredMethod(getMethodName);
                Object oldParam = getMethod.invoke(oldObj);

                getMethod = newObj.getClass().getDeclaredMethod(getMethodName);
                Object newParam = getMethod.invoke(newObj);

                newObjSetMethod = newObj.getClass().getDeclaredMethod(setMethodName, field.getType());

                if (newParam instanceof String) {
                    if (newParam != null && "".equals(((String) newParam).trim()) || StringUtils.isNotBlank(newParam.toString())) {
                        continue;
                    } else {
                        newObjSetMethod.invoke(newObj, oldParam);
                    }
                } else if (newParam instanceof Integer) {
                    if ((Integer) newParam < 0) { // 这里不够通用，已有涉及业务逻辑，因为系统数据库中所有数字都是整型...
                        newObjSetMethod.invoke(newObj, oldParam);
                    } else {
                        continue;
                    }
                } else if (newParam == null) {
                    newObjSetMethod.invoke(newObj, oldParam);
                }

            } catch (Exception e) {
                //
            }
        }
    }

}
