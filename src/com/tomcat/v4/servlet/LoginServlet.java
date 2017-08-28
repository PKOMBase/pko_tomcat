package com.tomcat.v4.servlet;

import com.tomcat.v4.core.HttpRequest;
import com.tomcat.v4.core.HttpResponse;

public class LoginServlet extends HttpServlet {

    @Override
    public void init(HttpRequest httpRequest, HttpResponse httpResponse) {
        System.out.println("初始化servlet");
    }

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
        System.out.println("执行登录:" + httpRequest.paramMap);
        String login = (String) httpRequest.getParameter("login");
        String passport = (String) httpRequest.getParameter("passport");
        if (login.equals("kid") && passport.equals("123")) {
            System.out.println("登录成功");
            httpResponse.write4File("/html/loginSuccess.html");
        } else {
            System.out.println("登录失败");
            httpResponse.write4File("/html/loginError.html");
        }

    }
}
