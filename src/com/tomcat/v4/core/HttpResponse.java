package com.tomcat.v4.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class HttpResponse {

    private OutputStream outputStream;

    public HttpResponse(OutputStream outputStream) throws IOException {
        this.outputStream = outputStream;
    }

    public void write4File(String uri) throws IOException {
        /** 返回信息 */
        FileInputStream fileInputStream = null;
        File file = null;
        try {
            if (uri.startsWith("/")) {
                uri = uri.substring(1);
            }
            if (!uri.startsWith("WEB-INF")) {
                uri = "WEB-INF/" + uri;
            }
            file = new File(uri);
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("wirte4File is ERROR, " + e.getMessage());
        }
        if (fileInputStream == null) {
            return;
        }
        byte[] bytes = new byte[1024];
        int len = 0;
        PrintStream writer = new PrintStream(this.outputStream);
        this.write4Http(writer, file.length());
        while ((len = fileInputStream.read(bytes)) != -1) {
            // socket中的输出管道流
            this.outputStream.write(bytes, 0, len);
        }
        fileInputStream.close();
        writer.close();
        this.outputStream.flush();
        this.outputStream.close();
    }

    public void write(String content) throws IOException {
        /** 返回信息 */
        // socket中的输出管道流
        PrintStream writer = new PrintStream(this.outputStream);
        this.write4Http(writer, content.length());

        writer.print(content);
        writer.close();
        this.outputStream.flush();
        this.outputStream.close();
    }

    private void write4Http(PrintStream writer, long length) {
        writer.println("HTTP/1.1 200 OK");// 返回应答消息,并结束应答
        writer.println("Content-Type:text/html;charset=UTF-8");
        writer.println("Content-Length:" + length);// 返回内容字节数
        writer.println();// 根据 HTTP 协议, 空行将结束头信息
    }
}
