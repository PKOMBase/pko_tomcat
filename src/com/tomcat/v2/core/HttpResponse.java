package com.tomcat.v2.core;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class HttpResponse {

    private OutputStream outputStream;

    public HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(String content) throws IOException {
        PrintStream writer = new PrintStream(this.outputStream);
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type:text/html;charset=UTF-8");
        writer.println();

        writer.print(content);

        writer.close();

        this.outputStream.flush();
        this.outputStream.close();
    }
}
