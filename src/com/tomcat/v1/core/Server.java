package com.tomcat.v1.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static int port = 8080;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        Socket socket = null;

        try {
            serverSocket = new ServerSocket(port);

            System.out.println("web服务器初始化完成");
            System.out.println("等待请求...");

            // 监听客户端请求
            socket = serverSocket.accept();
            // 获取请求信息
            InputStream inputStream = socket.getInputStream();

            byte[] bytes = new byte[1024];
            int length = inputStream.read(bytes);
            if (length > 0) {
                String msg = new String(bytes, 0, length);
                System.out.println("==msg==" + msg);
                // 解析：uri、参数
                // 分发处理
            }

            // 响应
            OutputStream outputStream = socket.getOutputStream();
            String result = "<html><h1>hello world</h1></html>";

            PrintStream writer = new PrintStream(outputStream);
            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Type:text/html;charset=UTF-8");
            writer.println();

            writer.print(result);

            writer.close();

            outputStream.flush();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
