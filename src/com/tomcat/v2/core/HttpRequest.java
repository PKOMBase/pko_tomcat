package com.tomcat.v2.core;

import java.io.IOException;
import java.io.InputStream;

public class HttpRequest {

    private InputStream inputStream;

    public HttpRequest(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        byte[] bytes = new byte[1024];
        int length = inputStream.read(bytes);
        if (length > 0) {
            String msg = new String(bytes, 0, length);
            System.out.println("==msg==" + msg);
            // 解析：uri、参数
            // 分发处理

        }
    }
}
