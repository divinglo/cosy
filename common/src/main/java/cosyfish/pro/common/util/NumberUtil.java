package cosyfish.pro.common.util;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class NumberUtil {
    /**
     * 非数值返回null; 数值时按照小数位数返回对应字符串
     *
     * @param str 数值字符串
     * @param scale 允许小数位数
     * @return 对应小数位的字符串
     */
    public static String getLocationStr(String str, int scale) {
        if (NumberUtils.isCreatable(str)) {
            double val = NumberUtils.toDouble(str);
            StringBuilder sb = new StringBuilder();
            sb.append("#.");
            for (int i = 0; i < scale; i++) {
                sb.append("#");
            }
            DecimalFormat df = new DecimalFormat(sb.toString());
            return df.format(val);
        }
        return null;
    }

    /**
     * 是否为正确的ID值, ID不为空, 且ID值不允许小于1
     *
     * @param id ID对象
     * @return 是否为正确的ID值
     */
    public static boolean isCorrectId(Integer id) {
        if (id == null) {
            return false;
        } else if (id < 1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 是否为正确的ID值, ID不为空, 且ID值不允许小于1
     *
     * @param id ID对象
     * @return 是否为正确的ID值
     */
    public static boolean isCorrectId(Long id) {
        if (id == null) {
            return false;
        } else if (id < 1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 两个整形值比较<br>
     * http://www.cnblogs.com/xh0102/p/5280032.html<br>
     *
     * <pre>
     *  NumberUtil.equals(null, null) = true
     *  NumberUtil.equals(null, 1) = false
     *  NumberUtil.equals(1, 2) = false
     *  NumberUtil.equals(129, 129) = true
     * </pre>
     *
     * @param a 对比值1
     * @param b 对比值2
     * @return 是否相等
     */
    public static boolean equals(Integer a, Integer b) {
        boolean flag = false;
        if (a == null || b == null) {
            if (a == null && b == null) {
                flag = true;
            }
        } else {
            flag = (a.intValue() == b.intValue());
        }
        return flag;
    }

    /**
     * 两个整形值比较<br>
     * http://www.cnblogs.com/xh0102/p/5280032.html<br>
     *
     * <pre>
     *  NumberUtil.equals(null, null, 1) = true
     *  NumberUtil.equals(1, null, 1) = false
     *  NumberUtil.equals(1, 2,3) = false
     *  NumberUtil.equals(129, 129, 130) = true
     * </pre>
     *
     * @param num 对比值num
     * @param searchNums 匹配值列表
     * @return 匹配值列表
     */
    public static boolean equalsAny(final Integer num, final Integer... searchNums) {
        if (ArrayUtils.isNotEmpty(searchNums)) {
            for (final Integer next : searchNums) {
                if (equals(num, next)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 两个长整形值比较<br>
     *
     * <pre>
     *  NumberUtil.equals(null, null) = true
     *  NumberUtil.equals(null, 1L) = false
     *  NumberUtil.equals(1L, 2L) = false
     *  NumberUtil.equals(129L, 129L) = true
     * </pre>
     *
     * @param a 对比值1
     * @param b 对比值2
     * @return 是否相等
     */
    public static boolean equals(Long a, Long b) {
        boolean flag = false;
        if (a == null || b == null) {
            if (a == null && b == null) {
                flag = true;
            }
        } else {
            flag = (a.longValue() == b.longValue());
        }
        return flag;
    }

    /**
     * 两个长整形值比较<br>
     *
     * <pre>
     *  NumberUtil.equals(null, null, 1L) = true
     *  NumberUtil.equals(1L, 2L, 3L) = false
     *  NumberUtil.equals(129L, 129L, 130L) = true
     * </pre>
     *
     * @param num 对比值num
     * @param searchNums 匹配值列表
     * @return 是否相等
     */
    public static boolean equalsAny(final Long num, final Long... searchNums) {
        if (ArrayUtils.isNotEmpty(searchNums)) {
            for (final Long next : searchNums) {
                if (equals(num, next)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取指定精度的浮点型<br>
     *
     * <pre>
     * setScale(1.234, 2, null) = 1.23
     * setScale(1.235, 2, null) = 1.24
     * etScale(1.234, 2, RoundingMode.UP) = 1.24
     * setScale(1.235, 2, RoundingMode.DOWN) = 1.24
     * </pre>
     *
     * @param val 原始值
     * @param newScale 精度位数
     * @param roundingMode 精度转换模式, 默认: RoundingMode.HALF_UP(向上取整)
     * @return 对应精度后的浮点型值
     */
    public static double setScale(double val, int newScale, RoundingMode roundingMode) {
        BigDecimal _val = new BigDecimal(val);
        if (roundingMode == null) {
            roundingMode = RoundingMode.HALF_UP;
        }
        return _val.setScale(newScale, roundingMode).doubleValue();
    }

    /**
     * num是否在min和max之间(包含关系)<br>
     *
     * <pre>
     *  NumberUtil.isRang(null, 1, 3) = false
     *  NumberUtil.isRang(2, null, 3) = false
     *  NumberUtil.isRang(2, 1, null) = false
     *  NumberUtil.isRang(1, 1, 3) = true
     *  NumberUtil.isRang(2, 1, 3) = true
     *  NumberUtil.isRang(3, 1, 3) = true
     *  NumberUtil.isRang(0, 1, 3) = false
     *  NumberUtil.isRang(4, 1, 3) = false
     * </pre>
     *
     * @param num 数值
     * @param min 最小值
     * @param max 最大值
     * @return 是否在范围内
     */
    public static boolean isRang(Integer num, Integer min, Integer max) {
        boolean _val = false;
        if (min != null && max != null && num != null) {
            if (num >= min && num <= max) {
                _val = true;
            }
        }
        return _val;
    }
}
