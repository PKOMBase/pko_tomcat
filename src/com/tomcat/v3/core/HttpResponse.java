package com.tomcat.v3.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class HttpResponse {

    private OutputStream outputStream;

    public HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write4File(String uri) throws IOException {
        FileInputStream fileInputStream = null;
        File file = null;

        if (uri.startsWith("/")) {
            uri = uri.substring(1);
        }
        if (!uri.startsWith("WEB-INF")) {
            uri = "WEB-INF/" + uri;
        }
        file = new File(uri);

        fileInputStream = new FileInputStream(file);

        byte[] bytes = new byte[1024];
        int len = 0;
        PrintStream writer = new PrintStream(this.outputStream);
        this.write4Http(writer, file.length());

        while ((len = fileInputStream.read(bytes)) != -1) {
            this.outputStream.write(bytes, 0, len);
        }
        fileInputStream.close();
        writer.close();
        this.outputStream.flush();
        this.outputStream.close();
    }

    public void write(String content) throws IOException {
        PrintStream writer = new PrintStream(this.outputStream);
        this.write4Http(writer, content.length());

        writer.print(content);

        writer.close();

        this.outputStream.flush();
        this.outputStream.close();
    }

    private void write4Http(PrintStream writer, long length) {
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type:text/html;charset=UTF-8");
        writer.println("Content-Length:" + length);
        writer.println();
    }
}
