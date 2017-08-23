package com.tomcat.v2.core;

import java.io.IOException;
import java.io.InputStream;

public class HttpRequest {

    private InputStream inputStream;

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
            // 解析信息 参数
            // 处理请求
        }

    }

}
