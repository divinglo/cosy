package cosyfish.pro.common.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * 字节or字符 流读取工具
 */
public class IOUtils {

    /**
     * Get the contents of an InputStream as a String using the default character encoding of the
     * platform.
     */
    public static String toString(InputStream in, boolean close) throws Exception {
        try {
            return org.apache.commons.io.IOUtils.toString(in, Charset.defaultCharset());
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (close) {
                    in.close();
                }
            } catch (IOException e) {

            }
        }
    }

    /**
     * 没有关闭流的连接
     */
    public static String toString(InputStream in, String encoding, boolean close) throws Exception {
        try {
            return org.apache.commons.io.IOUtils.toString(in, encoding);
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (close) {
                    in.close();
                }
            } catch (IOException e) {

            }
        }

    }

    /**
     * 尽量读取指定长度的字节数组，返回真实读取到的数据
     */
    public static byte[] readAll(InputStream in, int readLen, boolean close) throws IOException {
        // DataInputStream s=new DataInputStream(in);
        byte[] bs = new byte[readLen];
        // in.read(bs);
        // 读满一个数组
        int bytesRead = 0;
        try {
            // BufferedInputStream br=new BufferedInputStream(in);
            while (bytesRead < readLen) {
                int result = in.read(bs, bytesRead, readLen - bytesRead);
                if (result == -1) {
                    break;
                }
                bytesRead += result;
            }
        } catch (IOException ex) {
            // throw ex;
            // bs = new byte[0];
        } finally {
            try {
                if (close) {
                    in.close();
                }
            } catch (IOException e) {
            }
        }
        if (bytesRead < readLen) {
            bs = Arrays.copyOfRange(bs, 0, bytesRead);
        }
        return bs;
    }

    /**
     * 尽量读固定长度的字符，返回真实读取到的数据
     */
    public static char[] readAll(Reader in, int readLen, boolean close) throws IOException {
        char[] bs = new char[readLen];
        // in.read(bs);
        // 读满一个数组
        int bytesRead = 0;
        try {
            while (bytesRead < readLen) {
                int result = in.read(bs, bytesRead, readLen - bytesRead);
                if (result == -1) {
                    break;
                }
                bytesRead += result;
            }
            if (bytesRead < readLen) {
                // throw new IOException("haven't read given" +
                // " number[" +readLen+
                // "]chars");
            }
        } catch (IOException ex) {
            // throw ex;
            // bs = new char[0];
        } finally {
            try {
                if (close) {
                    in.close();
                }
            } catch (IOException e) {

            }
        }
        if (bytesRead < readLen) {
            bs = Arrays.copyOfRange(bs, 0, bytesRead);
            // throw new IOException("haven't read given" +
            // " number[" +readLen+
            // "]chars");
        }
        return bs;
    }

    /**
     * 读固定长度的字节,如果少于给定的字节,抛出异常
     */
    public static byte[] readFully(InputStream in, int readLen, boolean close) throws IOException {
        // DataInputStream s=new DataInputStream(in);
        byte[] bs = new byte[readLen];
        // in.read(bs);
        // 读满一个数组
        int bytesRead = 0;
        try {
            // BufferedInputStream br=new BufferedInputStream(in);
            while (bytesRead < readLen) {
                int result = in.read(bs, bytesRead, readLen - bytesRead);
                if (result == -1) {
                    break;
                }
                bytesRead += result;
            }
            if (bytesRead < readLen) {
                // bs = Arrays.copyOfRange(bs, 0, bytesRead);
                throw new IOException("haven't  read given" + " number[" + readLen + "]bytes");
            }
        } catch (IOException ex) {
            throw ex;
            // bs = new byte[0];
        } finally {
            try {
                if (close) {
                    in.close();
                }
            } catch (IOException e) {
            }
        }
        return bs;
    }

    /**
     * 读固定长度的字符,如果少于给定的字节,抛出异常
     */
    public static char[] readFully(Reader in, int readLen, boolean close) throws IOException {
        char[] bs = new char[readLen];
        // in.read(bs);
        // 读满一个数组
        int bytesRead = 0;
        try {
            while (bytesRead < readLen) {
                int result = in.read(bs, bytesRead, readLen - bytesRead);
                if (result == -1) {
                    break;
                }
                bytesRead += result;
            }
            if (bytesRead < readLen) {
                throw new IOException("haven't  read given" + " number[" + readLen + "]chars");
            }
        } catch (IOException ex) {
            throw ex;
            // bs = new char[0];
        } finally {
            try {
                if (close) {
                    in.close();
                }
            } catch (IOException e) {

            }
        }
        return bs;
    }

    public static byte[] toByteArray(InputStream in, boolean close) throws Exception {
        try {
            return org.apache.commons.io.IOUtils.toByteArray(in);
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (close) {
                    in.close();
                }
            } catch (IOException e) {

            }
        }

    }

    public static byte[] toByteArray(Reader in, String charset, boolean close) throws Exception {
        try {
            return org.apache.commons.io.IOUtils.toByteArray(in, charset);
        } catch (IOException e) {
            // log.error("", e);
            throw e;
        } finally {
            try {
                if (close) {
                    in.close();
                }
            } catch (IOException e) {

            }
        }

    }

    public static String toString(Reader in, boolean close) throws Exception {
        try {
            return org.apache.commons.io.IOUtils.toString(in);
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (close) {
                    in.close();
                }
            } catch (IOException e) {

            }
        }

    }

    public static void close(InputStream in) throws Exception {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                throw e;
            }
        }
    }

    public static void close(OutputStream out) throws Exception {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                throw e;
            }
        }
    }

    public static void close(Writer out) throws Exception {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                throw e;
            }
        }
    }

    public static void close(Reader in) throws Exception {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                throw e;
            }
        }
    }
}
