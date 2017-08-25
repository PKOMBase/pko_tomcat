package com.tomcat.v3.core;

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
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            // 获取请求信息
            request = new HttpRequest(inputStream);
            response = new HttpResponse(outputStream);

            // 根据uri进行不同的处理，分静态、动态
            String uri = request.getUri();
            // 静态
            if (isStatic(uri)) {
                response.write4File(uri);
            } else {
                if ("/login.action".equals(uri)) {
                    response.write("<html><h1>hello world</h1></html>");
                } else {
                    response.write("<html><h1>找不到请求</h1></html>");
                }
            }

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

    public static boolean isStatic(String uri) {
        String[] staticSuffixes = { "html", "js", "css", "png", "jpeg", "jpg", "ico" };
        for (String staticSuffix : staticSuffixes) {
            if (uri.endsWith("." + staticSuffix)) {
                return true;
            }
        }
        return false;
    }
}
