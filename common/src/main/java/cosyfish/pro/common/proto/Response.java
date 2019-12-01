//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cosyfish.pro.common.proto;


public class Response {
    protected int errcode;
    protected String msg;
    private Object result;

    public static Response ok() {
        return new Response((Object) null);
    }

    public static Response ok(Object result) {
        return new Response(result);
    }

    public static Response errAuth() {
        return errAuth("请重新登录");
    }

    public static Response errAuth(String msg) {
        return errAuth(msg, (Object) null);
    }

    public static Response errAuth(String msg, Object result) {
        return new Response(ErrCode.AUTH_ERR.getValue(), msg, result);
    }

    public static Response errParams() {
        return errParams("参数错误");
    }

    public static Response errParams(String msg) {
        return errParams(msg, (Object) null);
    }

    public static Response errParams(String msg, Object result) {
        return new Response(ErrCode.PARAMS_ERR.getValue(), msg, result);
    }

    public static Response errBiz() {
        return errBiz("系统处理遇到错误");
    }

    public static Response errBiz(String msg) {
        return errBiz(msg, (Object) null);
    }

    public static Response errBiz(String msg, Object result) {
        return new Response(ErrCode.BIZ_ERR.getValue(), msg, result);
    }

    public static Response errSys() {
        return errSys("系统处理遇到异常");
    }

    public static Response errSys(String msg) {
        return errSys(msg, (Object) null);
    }

    public static Response errSys(String msg, Object result) {
        return new Response(ErrCode.SYS_ERR.getValue(), msg, result);
    }

    public static Response errFrozened() {
        return errFrozened("您的帐号已被冻结");
    }

    public static Response errFrozened(String msg) {
        return errFrozened(msg, (Object) null);
    }

    public static Response errFrozened(String msg, Object result) {
        return new Response(ErrCode.FROZENED_ERR.getValue(), msg, result);
    }

    public static Response errForbidden() {
        return errForbidden("您的帐号已被禁言");
    }

    public static Response errForbidden(String msg) {
        return errForbidden(msg, (Object) null);
    }

    public static Response errForbidden(String msg, Object result) {
        return new Response(ErrCode.FORBIDDEN_ERR.getValue(), msg, result);
    }

    public static Response errPermission(String msg) {
        return errPermission(msg, (Object) null);
    }

    public static Response errPermission(String msg, Object result) {
        return new Response(ErrCode.FROZENED_ERR.getValue(), msg, result);
    }

    public static Response build(Integer errcode, String msg) {
        return build(errcode, msg, (Object) null);
    }

    public static Response build(Integer errcode, String msg, Object result) {
        return new Response(errcode, msg, result);
    }

    public static Response buildByErrMsg(String errMsg) {
        return errMsg != null && !"".equals(errMsg.trim()) ? errBiz(errMsg) : ok();
    }

    public Response(Object result) {
        this.errcode = ErrCode.NO_ERR.getValue();
        this.result = result;
    }

    public Response(int errcode, String msg, Object result) {
        this.errcode = ErrCode.NO_ERR.getValue();
        this.errcode = errcode;
        this.msg = msg;
        this.result = result;
    }

    public Response(int errcode, String msg) {
        this.errcode = ErrCode.NO_ERR.getValue();
        this.errcode = errcode;
        this.msg = msg;
    }

    public int getErrcode() {
        return this.errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return this.result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
