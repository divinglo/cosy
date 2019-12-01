//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cosyfish.pro.common.proto;

public enum ErrCode {
    NO_ERR(0),
    AUTH_ERR(100),
    SIGN_ERR(101),
    PARAMS_ERR(102),
    BIZ_ERR(103),
    FROZENED_ERR(120),
    FORBIDDEN_ERR(121),
    SYS_ERR(400);

    private int value;

    private ErrCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
