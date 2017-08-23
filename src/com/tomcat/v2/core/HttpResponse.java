package com.tomcat.v2.core;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class HttpResponse {

    private OutputStream outputStream;

    public HttpResponse(OutputStream outputStream) throws IOException {
        this.outputStream = outputStream;

    }

    public void write(String content) throws IOException {
        /** 返回信息 */
        // socket中的输出管道流
        PrintStream writer = new PrintStream(this.outputStream);
        writer.println("HTTP/1.1 200 OK");// 返回应答消息,并结束应答
        writer.println("Content-Type:text/html;charset=UTF-8");
        writer.println();// 根据 HTTP 协议, 空行将结束头信息

        writer.print(content);
        writer.close();
        this.outputStream.flush();
        this.outputStream.close();
    }
}
