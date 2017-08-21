package com.tomcat.v2.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

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
        try {
            // new一个http的socket服务
            serverSocket = new ServerSocket(port);

            System.out.println("web服务器初始化成功");
            System.out.println("等待请求...");

            // 监听客户端socket的请求，并返回socket对象
            socket = serverSocket.accept();
            // socket中的输入管道流
            InputStream inputStream = socket.getInputStream();

            /** 处理请求 */
            // 将通道中的数据读取到bytes中
            byte[] bytes = new byte[1024];
            int length = inputStream.read(bytes);
            // 将请求信息打印
            if (length > 0) {
                String msg = new String(bytes, 0, length);
                System.out.println("==msg==" + msg + "====");
                // 解析信息 uri
                // 解析信息 参数
                // 处理请求
            }

            /** 返回信息 */
            // socket中的输出管道流
            OutputStream outputStream = socket.getOutputStream();
            String result = "<html><h1>1.4.1</h1></html>";
            PrintStream writer = new PrintStream(outputStream);
            writer.println("HTTP/1.1 200 OK");// 返回应答消息,并结束应答
            writer.println("Content-Type:text/html;charset=UTF-8");
            writer.println();// 根据 HTTP 协议, 空行将结束头信息
            writer.print(result);
            writer.close();
            outputStream.flush();
            outputStream.close();

            socket.close();
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
