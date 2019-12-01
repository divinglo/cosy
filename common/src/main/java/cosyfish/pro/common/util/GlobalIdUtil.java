package cosyfish.pro.common.util;

import java.nio.ByteBuffer;

/**
 * 全局ID服务接口
 *
 * @author wprehard@qq.com 2017年3月29日
 */
public class GlobalIdUtil {
    public final static String KEY_MACHINE_ID = "globalid.machineid";
    private static GlobalIdWorker _globalIdWorker = null;

    /**
     * 初始化
     */
    public static void init(int machineId) {
        if (_globalIdWorker == null) {
            synchronized (GlobalIdUtil.class) {
                if (machineId <= 0) {
                    throw new IllegalArgumentException(String.format("_globalIdWorker Args not init"));
                }
                _globalIdWorker = new GlobalIdWorker(machineId);
            }
        }
    }

    /**
     * 获取下一个ID
     *
     * @return null
     */
    public static long nextId() {
        if (_globalIdWorker == null) {
            throw new IllegalArgumentException("_globalIdWorker Args not init");
        }
        return _globalIdWorker.nextId();
    }

    /**
     * Convert to a byte array. Note that the numbers are stored in big-endian order.
     *
     * @return the byte array
     */
    public static byte[] toByteArray(long id) {
        byte b[] = new byte[12];
        ByteBuffer bb = ByteBuffer.wrap(b);
        // by default BB is big endian like we need
        bb.putLong(id);
        return b;
    }
}
