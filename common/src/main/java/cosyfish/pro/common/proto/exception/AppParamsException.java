package cosyfish.pro.common.proto.exception;

/**
 * @author wprehard@qq.com
 * @description 参数异常
 * @date 2017年8月11日
 **/
@SuppressWarnings("serial")
public class AppParamsException extends RuntimeException {

    public AppParamsException(String msg) {
        super(msg);
    }
}
