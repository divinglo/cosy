package cosyfish.pro.common.proto;

import cosyfish.pro.common.proto.exception.AppAuthException;
import cosyfish.pro.common.proto.exception.AppBizException;
import cosyfish.pro.common.proto.exception.AppParamsException;
import cosyfish.pro.common.proto.exception.AppRuntimeException;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常处理工具类
 *
 * @author weipengfei@youcheyihou.com
 * @date 2018年3月28日
 **/
public class ExceptionUtil {

    /**
     * 获取指定异常的堆栈
     *
     * @param e Throwable
     * @return String
     */
    public static String getStackTrace(Throwable e) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    /**
     * 获取自定义的系统运行异常
     *
     * @param msg 异常消息内容
     * @return AppRuntimeException
     * @date 2018年3月28日
     */
    public static AppRuntimeException getRuntimeException(String msg) {
        return new AppRuntimeException(msg);
    }

    /**
     * 获取业务自定义异常
     *
     * @param msg 异常消息内容
     * @return AppBizException
     * @date 2018年3月28日
     */
    public static AppBizException getBizException(String msg) {
        return new AppBizException(msg);
    }

    /**
     * 获取业务参数异常
     *
     * @param msg 异常消息内容
     * @return AppParamsException
     * @date 2018年3月28日
     */
    public static AppParamsException getParamsException(String msg) {
        return new AppParamsException(msg);
    }

    /**
     * 获取用户身份校验异常
     *
     * @param msg 异常消息内容
     * @return AppAuthException
     * @date 2018年3月28日
     */
    public static AppAuthException getAuthException(String msg) {
        return new AppAuthException(msg);
    }

}
