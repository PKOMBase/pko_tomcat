package com.tomcat.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * web服务器核心server类
 * 
 * @author sunjie at 2017年1月31日
 *
 */
public class Server {

    private static int port = 8080;

    // // 解析web.xml
    // public static WebXmlBean webXmlBean = WebXml.getWebXmlBean();

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            // new一个http的socket服务
            serverSocket = new ServerSocket(port);

            System.out.println("web服务器初始化成功");
            // 循环监听socket请求
            while (true) {
                System.out.println("等待请求...");

                // 监听客户端socket的请求，并返回socket对象
                socket = serverSocket.accept();
                // socket中的输入管道流
                InputStream inputStream = socket.getInputStream();

                // /** 处理请求 */
                // HttpRequest request = new HttpRequest(inputStream);
                // String uri = request.getUri();
                //
                // /** 返回信息 */
                // // socket中的输出管道流
                // OutputStream outputStream = socket.getOutputStream();
                // HttpResponse response = new HttpResponse(outputStream);
                //
                // // 判断动态还是静态资源
                // if (Server.isStatic(uri)) {
                // response.wirte4File(uri);
                // } else {
                // // 通过反射获取对应的servlet对象
                // ServletBean servletBean =
                // Server.webXmlBean.getServletBean4Url(uri);
                // if (servletBean == null) {
                // System.out.println("动态资源执行失败，未找到对应servlet, uri:" + uri +
                // "======"
                // + Server.webXmlBean.toString());
                // socket.close();
                // continue;
                // }
                // Class<?> servletClass =
                // Class.forName(servletBean.getClassName());
                // HttpServlet servletObject = (HttpServlet)
                // servletClass.newInstance();
                // servletObject.init(request, response);
                // servletObject.service(request, response);
                // }
                socket.close();
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

    /**
     * 
     * 判断是否静态链接
     *
     * @author sunjie at 2017年1月31日
     *
     * @param uri
     * @return
     */
    private static boolean isStatic(String uri) {
        String[] staticSuffixes = { "html", "js", "css", "png", "jpeg", "jpg", "ico" };
        for (String staticSuffix : staticSuffixes) {
            if (uri.endsWith("." + staticSuffix)) {
                return true;
            }
        }
        return false;
    }
}
