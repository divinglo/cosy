package cosyfish.pro.common.proto.exception;

/**
 * @author wprehard@qq.com
 * @description APP业务类异常
 * @date 2017年8月11日
 **/
@SuppressWarnings("serial")
public class AppBizException extends RuntimeException {

    public AppBizException(String msg) {
        super(msg);
    }
}
