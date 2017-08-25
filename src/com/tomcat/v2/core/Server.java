package com.tomcat.v2.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {

    private static int port = 8080;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        ThreadPoolExecutor threadPoolExecutor = null;
        try {
            threadPoolExecutor = new ThreadPoolExecutor(2, 10, 3000, TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<Runnable>(10), new ThreadPoolExecutor.AbortPolicy());
            serverSocket = new ServerSocket(port);

            System.out.println("web服务器初始化完成");

            while (true) {
                System.out.println("等待请求...");

                // 监听客户端请求
                socket = serverSocket.accept();
                threadPoolExecutor.submit(new ServerRunnble(socket));
            }
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

class ServerRunnble extends Thread {

    private Socket socket;

    public ServerRunnble(Socket socket) {
        super();
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream;
        OutputStream outputStream;
        HttpRequest request;
        HttpResponse response;

        try {
            // 获取请求信息
            inputStream = socket.getInputStream();
            request = new HttpRequest(inputStream);

            // 响应
            outputStream = socket.getOutputStream();
            response = new HttpResponse(outputStream);
            response.write("<html><h1>hello world</h1></html>");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
