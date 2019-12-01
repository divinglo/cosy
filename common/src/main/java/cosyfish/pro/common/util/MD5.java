package cosyfish.pro.common.util;

import java.security.MessageDigest;


public class MD5 implements java.io.Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static byte[] generateBytes(byte[] source) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(source);
        byte[] b = md.digest();
        return b;
    }

    /**
     * 对source字节数组做md5 2013-09-16
     */
    public static String toString(byte[] source, boolean is32) throws Exception {
        byte[] b = generateBytes(source);
        String buf = ByteUtils.toHexAscii(b, "");
        if (is32) {
            return buf;
        } else {
            return buf.substring(8, 24);
        }
    }

    private static String to32BitString(String plainText, boolean is32, String charset) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        if (StringUtils.isNotBlank(charset)) {
            md.update(plainText.getBytes(charset));
        } else {
            md.update(plainText.getBytes());
        }
        byte[] b = md.digest();
        String buf = ByteUtils.toHexAscii(b, "");
        if (is32) {
            return buf;
        } else {
            return buf.substring(8, 24);
        }
    }

    /**
     * 产生32位md5加密字符串
     */
    public final static String MD5generator(String s) throws Exception {
        return MD5.to32BitString(s, true, "");
    }

    /**
     * 产生32位md5加密字符串
     */
    public final static String MD5generator(String s, String charset) throws Exception {
        return MD5.to32BitString(s, true, charset);
    }

    /**
     * 产生16为md5加密字符串
     */
    public final static String MD5generator16Bit(String s, String charset) throws Exception {
        return MD5.to32BitString(s, false, charset);
    }

    public final static String MD5generator16Bit(String s) throws Exception {
        return MD5.to32BitString(s, false, "");
    }

}
