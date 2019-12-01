package cosyfish.pro.common.proto.exception;

/**
 * @author wprehard@qq.com
 * @description 鉴权异常
 * @date 2017年8月11日
 **/
@SuppressWarnings("serial")
public class AppAuthException extends RuntimeException {

    public AppAuthException(String msg) {
        super(msg);
    }
}
