package cosyfish.pro.common.util.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * gson对Double类型适配
 * <p>
 * 用于对double或float类型的字段序列化和反序列化的特殊处理:
 * 1. 反序列化时,遇到字段不存在或为null时,替换为0.00
 * 2. 序列化时,遇到字段为null时,替换为"0.00"
 * </p>
 *
 * @author weipengfei@youcheihou.com
 * @date 2018-05-11
 */
public class DoubleTypeAdapter extends TypeAdapter<Double> {
    @Override
    public void write(JsonWriter out, Double value) throws IOException {
        if (value == null) {
            // out.nullValue();
            out.value(0.00d); // 序列化时将 null 转为 0
        } else {
            DecimalFormat format = new DecimalFormat("#0.##"); // 保留两位
            String temp = format.format(value);
            out.value(Double.valueOf(temp));
        }
    }

    @Override
    public Double read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return 0.00d;
        }
        return in.nextDouble();
    }
}
