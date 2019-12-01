package cosyfish.pro.common.util;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import okhttp3.Headers.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class OkHttpUtil {

    private static Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);

    private static OkHttpClient client = new OkHttpClient();

    /**
     * 获取链接地址html字符串
     */
    public static String getHtml(String url) {
        Response response = null;
        try {
            Request request = new Request.Builder().url(url).build();
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            logger.error("获取html内容异常, url=" + url + ", casue by", e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    public static <T> T post(String url, Map<String, String> params, Class<T> t) {
        return post(url, params, null, t);
    }

    public static <T> T post(String url, Map<String, String> params, Map<String, String> headers, Class<T> t) {
        try {
            FormBody.Builder requetBuilder = new FormBody.Builder();
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    requetBuilder.add(entry.getKey(), entry.getValue());
                }
            }
            Builder headersBuilder = createHeader(headers);
            Request request = null;
            if (headersBuilder != null) {
                request = new Request.Builder().headers(headersBuilder.build()).url(url).post(requetBuilder.build()).build();
            } else {
                request = new Request.Builder().url(url).post(requetBuilder.build()).build();
            }
            logger.debug(
                    "post(url=" + url + ", param=" + JSON.toJSONString(params) + ", headers=" + headers != null ? JSON.toJSONString(headers) : "");
            T body = doRequest(url, t, request);
            if (body != null) {
                return body;
            }
        } catch (IOException e) {
            logger.error("http post请求异常, url=" + url + ", params=" + FastJsonUtil.toJsonString(params) + ", casue by", e);
        }
        return null;
    }

    public static <T> T post(String url, String mediaType, String content, Map<String, String> headers, Class<T> t) {
        try {
            if (StringUtils.isBlank(content)) {
                throw new IllegalArgumentException("传递的内容不能为空");
            }
            if (mediaType == null) {
                mediaType = "application/json";
            }
            MediaType contentType = MediaType.parse(mediaType);
            RequestBody body = RequestBody.create(contentType, content);
            Builder headersBuilder = createHeader(headers);
            Request request = null;
            if (headersBuilder != null) {
                request = new Request.Builder().headers(headersBuilder.build()).url(url).post(body).build();
            } else {
                request = new Request.Builder().url(url).post(body).build();
            }
            logger.debug("post(url=" + url + ", content=" + content + ", headers=" + headers != null ? JSON.toJSONString(headers)
                    : "" + ") response body: " + body);
            T bodyResponse = doRequest(url, t, request);
            if (bodyResponse != null) {
                return bodyResponse;
            }
        } catch (IOException e) {
            logger.error("http post请求异常, url=" + url + ", content=" + content + ", casue by", e);
        }
        return null;
    }

    private static Builder createHeader(Map<String, String> headers) {
        Builder headersBuilder = null;
        if (headers != null) {
            headersBuilder = new Builder();
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                headersBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        return headersBuilder;
    }

    private static <T> T doRequest(String url, Class<T> t, Request request) throws IOException {
        Response response = client.newCall(request).execute();
        try {
            if (response.isSuccessful()) {
                String body = response.body().string();
                logger.debug("post(url=" + url + ") response body: " + body);
                return FastJsonUtil.parseObject(body, t);
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    public static String postReturnBody(String url, Map<String, String> params, Map<String, String> headers) {
        try {
            FormBody.Builder requetBuilder = new FormBody.Builder();
            if (params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    requetBuilder.add(entry.getKey(), entry.getValue());
                }
            }
            Builder headersBuilder = createHeader(headers);
            Request request = null;
            if (headersBuilder != null) {
                request = new Request.Builder().headers(headersBuilder.build()).url(url).post(requetBuilder.build()).build();
            } else {
                request = new Request.Builder().url(url).post(requetBuilder.build()).build();
            }
            logger.debug(
                    "post(url=" + url + ", param=" + JSON.toJSONString(params) + ", headers=" + headers != null ? JSON.toJSONString(headers) : "");
            String body = doRequest(url, request);
            if (body != null) {
                return body;
            }
        } catch (IOException e) {
            logger.error("http post请求异常, url=" + url + ", params=" + FastJsonUtil.toJsonString(params) + ", casue by", e);
        }
        return null;
    }

    private static String doRequest(String url, Request request) throws IOException {
        Response response = client.newCall(request).execute();
        try {
            if (response.isSuccessful()) {
                String body = response.body().string();
                logger.debug("post(url=" + url + ") response body: " + body);
                return body;
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }
}
