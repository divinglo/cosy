package cosyfish.pro.common.util;

/**
 * 回调模板类
 */
public interface Callback<T1, T2> {
    public T2 call(T1 obj);
}
