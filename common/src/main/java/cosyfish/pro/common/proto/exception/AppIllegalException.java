package cosyfish.pro.common.proto.exception;

/**
 * @author wprehard@qq.com
 * @description 非法请求或参数异常
 * @date 2017年8月11日
 **/
@SuppressWarnings("serial")
public class AppIllegalException extends IllegalArgumentException {

    public AppIllegalException(String msg) {
        super(msg);
    }
}
