package com.tomcat.v3.core;

import java.io.IOException;
import java.io.InputStream;

public class HttpRequest {

    private InputStream inputStream;

    private String uri;

    public String getUri() {
        return uri;
    }

    public HttpRequest(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        byte[] bytes = new byte[1024];
        int length = inputStream.read(bytes);
        if (length > 0) {
            String msg = new String(bytes, 0, length);
            System.out.println("==msg==" + msg);
            // 解析：uri、参数
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

            // 分发处理

        }
    }
}
