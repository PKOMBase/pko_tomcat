package com.tomcat.v4.servlet;

import com.tomcat.v4.core.HttpRequest;
import com.tomcat.v4.core.HttpResponse;

/**
 * 
 * 基础servlet
 * 
 * @author sunjie at 2017年1月31日
 *
 */
public abstract class HttpServlet {

    /**
     * 
     * 初始化servlet
     *
     * @author sunjie at 2017年1月31日
     *
     * @param httpRequest
     * @param httpResponse
     */
    public abstract void init(HttpRequest httpRequest, HttpResponse httpResponse);

    /**
     * 
     * service处理方法
     *
     * @author sunjie at 2017年1月31日
     *
     * @param httpRequest
     * @param httpResponse
     */
    public abstract void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception;

}
