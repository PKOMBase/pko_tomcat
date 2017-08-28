package com.tomcat.v4.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private InputStream inputStream;

    private String uri;

    public Map<String, Object> paramMap = new HashMap<String, Object>();

    public String getUri() {
        return uri;
    }

    public Object getParameter(String key) {
        return paramMap.get(key);
    }

    public HttpRequest(final InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        /** 处理请求 */
        // 将通道中的数据读取到bytes中
        byte[] bytes = new byte[1024];
        int length = this.inputStream.read(bytes);
        // 将请求信息打印
        if (length > 0) {
            String msg = new String(bytes, 0, length);
            System.out.println("==msg==" + msg + "====");
            // 解析信息 uri
            int beginIndex = 0;
            int endIndex = 0;
            if (msg.startsWith("GET")) {
                beginIndex = msg.indexOf("GET") + 4;
                endIndex = msg.indexOf("HTTP/1.1") - 1;
            } else if (msg.startsWith("POST")) {
                beginIndex = msg.indexOf("POST") + 5;
                endIndex = msg.indexOf("HTTP/1.1") - 1;
            }
            this.uri = msg.substring(beginIndex, endIndex);

            // 解析信息 参数
            String paramString = msg.substring(msg.lastIndexOf("\n") + 1);
            String[] params = paramString.split("&");
            for (String param : params) {
                String[] paramArray = param.split("=");
                if (paramArray.length >= 2) {
                    paramMap.put(paramArray[0], paramArray[1]);
                }
            }
        }

    }

}
