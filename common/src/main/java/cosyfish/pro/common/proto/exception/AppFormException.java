package cosyfish.pro.common.proto.exception;


/**
 * 请求数据格式有误异常
 *
 * @author caixiwei
 * @date 2018年3月23日
 * @since v1.0.6
 **/
public class AppFormException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AppFormException(String message) {
        super(message);
    }
}
