package cosyfish.pro.common.util;

import org.apache.commons.lang3.math.NumberUtils;

/**
 * 多优先级排序计算分数值工具类<br>
 *
 * Long.MAX_VALUE=9223372036854775807 (19位)
 *
 * @author caixiwei
 */
public class ScoreUtil {

    /**
     * Long.MAX_VALUE=9223372036854775807 (19位)<br>
     */
    public static int LONGSTR_MAX_LENGTH = 19;


    /**
     * Long.MAX_VALUE=9223372036854775807 (19位)<br>
     * 不足19位,前面补0<br>
     * (Long)7 --> (String)0000000000000000007
     *
     * @param score long排序值
     * @return 排序值字符串
     */
    public static String toString(final Long score) {
        Long _score = check(score);
        String _scoreStr = String.valueOf(_score);
        int _scoreStrLength = _scoreStr.length();
        if (_scoreStrLength < LONGSTR_MAX_LENGTH) {
            String format = "%1$0" + (LONGSTR_MAX_LENGTH - _scoreStr.length()) + "d";
            return String.format(format, 0) + _scoreStr;
        } else {
            return _scoreStr;
        }
    }

    /**
     * 将两个long排序值转成排序值字符串
     *
     * @param isTop 高位排序值
     * @param score 低位排序值
     * @return 字符串排序值
     */
    public static String toString(final boolean isTop, final Long score) {
        return (isTop ? "1" : "0") + toString(score);
    }


    /**
     * 将两个long排序值转成排序值字符串(有高位低位之分)
     *
     * @param score1 高位排序值
     * @param score2 低位排序值
     * @return 字符串排序值
     */
    public static String toString(final Long score1, final Long score2) {
        return toString(score1) + toString(score2);
    }

    /**
     * 将两个long排序值转成排序值字符串(有高位低位之分)
     *
     * @param score1 高位排序值
     * @param score2 中位排序值
     * @param score3 低位排序值
     * @return 字符串排序值
     */
    public static String toString(final Long score1, final Long score2, final Long score3) {
        return toString(score1) + toString(score2) + toString(score3);
    }

    /**
     * 将两个long排序值转成排序值字符串(有高位低位之分)
     *
     * @param isTop 高位排序值
     * @param score 低位排序值
     * @return double排序值
     */
    public static double to(final boolean isTop, final Long score) {
        return NumberUtils.toDouble((isTop ? "1" : "0") + toString(score));
    }

    /**
     * 将两个long排序值转成排序值字符串(有高位低位之分)
     *
     * @param score1 高位排序值
     * @param score2 低位排序值
     * @return double排序值
     */
    public static double to(final Long score1, final Long score2) {
        return NumberUtils.toDouble(toString(score1, score2));
    }

    /**
     * 将两个long排序值转成排序值字符串(有高位低位之分)
     *
     * @param score1 高位排序值
     * @param score2 中位排序值
     * @param score3 低位排序值
     * @return double排序值
     */
    public static double to(final Long score1, final Long score2, final Long score3) {
        return NumberUtils.toDouble(toString(score1, score2, score3));
    }

    /**
     * 检查排序值,并返回合适值
     *
     * @param score 排序值
     * @return 合适排序值
     */
    private static Long check(Long score) {
        if (score == null) {
            score = 0L;
        }
        // 不允许为负数
        if (score < 0) {
            throw new RuntimeException("排序值不允许为负数");
        }
        return score;
    }
}
