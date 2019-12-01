package cosyfish.pro.common.util.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * gson对String类型适配
 * <p>
 * 用于对String类型的字段序列化和反序列化的特殊处理:
 * 1. 序列化时,遇到字段为null时,替换为""
 * </p>
 *
 * @author weipengfei@youcheihou.com
 * @date 2018-05-11
 */
public class StringTypeAdapter extends TypeAdapter<String> {
    @Override
    public void write(JsonWriter out, String value) throws IOException {
        if (value == null) {
            // out.nullValue();
            out.value(""); // 序列化时将 null 转为 ""
        } else {
            out.value(value);
        }
    }

    @Override
    public String read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        return in.nextString();
    }
}
