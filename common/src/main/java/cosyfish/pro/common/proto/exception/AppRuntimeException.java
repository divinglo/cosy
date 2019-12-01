package cosyfish.pro.common.proto.exception;

/**
 * @author wprehard@qq.com
 * @description 运行时异常
 * @date 2017年8月11日
 **/
@SuppressWarnings("serial")
public class AppRuntimeException extends RuntimeException {

    public AppRuntimeException(String msg) {
        super(msg);
    }
}
