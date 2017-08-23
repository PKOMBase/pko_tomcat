package com.tomcat.v4.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * web服务器核心server类
 * 
 * 
 * @author sunjie at 2017年1月31日
 *
 */
public class Server {

    private static int port = 8080;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        ThreadPoolExecutor threadPoolExecutor = null;
        try {
            // new一个http的socket服务
            serverSocket = new ServerSocket(port);

            System.out.println("web服务器初始化成功");

            // 线程池
            threadPoolExecutor = new ThreadPoolExecutor(2, 10, 3000, TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<Runnable>(5), new ThreadPoolExecutor.AbortPolicy());
            // 循环监听socket请求
            while (true) {
                System.out.println("等待请求...");

                // 监听客户端socket的请求，并返回socket对象
                socket = serverSocket.accept();

                threadPoolExecutor.submit(new ServerRunnable(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭socket服务
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}

class ServerRunnable extends Thread {

    private Socket socket = null;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ServerRunnable(Socket socket) {
        super();
        this.socket = socket;
    }

    @Override
    public void run() {
        // socket中的输入管道流
        InputStream inputStream;
        HttpRequest request;
        HttpResponse response;
        try {
            // 请求
            inputStream = socket.getInputStream();
            request = new HttpRequest(inputStream);

            // 响应
            OutputStream outputStream = socket.getOutputStream();
            response = new HttpResponse(outputStream);

            // 处理，根据uri进行不同的处理，分静态、动态
            String uri = request.getUri();
            // 静态
            if (isStatic(uri)) {
                response.write4File(uri);
            }
            // 动态
            else {
                // // 通过反射获取对应的servlet对象
                // ServletBean servletBean =
                // Server.webXmlBean.getServletBean4Url(uri);
                // if (servletBean == null) {
                // System.out.println("动态资源执行失败，未找到对应servlet, uri:" + uri +
                // "======" + Server.webXmlBean.toString());
                // socket.close();
                // return;
                // }
                // Class<?> servletClass =
                // Class.forName(servletBean.getClassName());
                // HttpServlet servletObject = (HttpServlet)
                // servletClass.newInstance();
                // servletObject.init(request, response);
                // servletObject.service(request, response);
                // response.write("<html><h1>hello world</h1></html>");
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

    /**
     * 
     * 判断是否静态链接
     *
     * @author sunjie at 2017年1月31日
     *
     * @param uri
     * @return
     */
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