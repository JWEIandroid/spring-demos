package com.example.thread.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static StringBuilder getStringBuilder(HttpURLConnection con) throws IOException {
        StringBuilder content;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String line;
            content = new StringBuilder();
            while ((line = in.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
        }
        return content;
    }

    /**
     * 发送一个GET请求
     *
     * @param url
     * @return
     * @throws MalformedURLException
     * @throws ProtocolException
     * @throws IOException
     */
    public static String get(String url) throws IOException {
        HttpURLConnection con = null;
        try {
            URL myUrl = new URL(url);
            con = (HttpURLConnection) myUrl.openConnection();
            con.setRequestMethod("GET");
            StringBuilder content;
            content = getStringBuilder(con);
            return content.toString();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    public static String post(String url, String urlParameters, String contentType, Map<String, String> headers) throws IOException {
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        HttpURLConnection con = null;
        try {
            URL myUrl = new URL(url);
            con = (HttpURLConnection) myUrl.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36 Junbo/1.0.4 Junbo-Common/Tookit");
            con.setRequestProperty("Content-Type", contentType);
            logger.trace("http post:{}, param:{}, content-type:{}", url, urlParameters, contentType);
            if (headers != null && headers.size() > 0) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    con.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postData);
            }
            StringBuilder content = getStringBuilder(con);
            logger.trace("http post:{}, response:{}", url, content);
            return content.toString();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    public static <T> T post(String url, String urlParameters, Class<T> clz) throws IOException {
        return JsonMapperUtil.fromJson(post(url, urlParameters, MediaType.APPLICATION_FORM_URLENCODED_VALUE, null), clz);
    }

    /**
     * 使用默认的url_encoded形式发送POST请求
     *
     * @param url           地址
     * @param urlParameters 键值对 a=b&c=d
     * @return
     * @throws IOException
     */
    public static String post(String url, String urlParameters) throws IOException {
        return post(url, urlParameters, MediaType.APPLICATION_FORM_URLENCODED_VALUE, null);
    }
}

