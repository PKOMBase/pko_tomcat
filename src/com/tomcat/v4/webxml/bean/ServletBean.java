package com.tomcat.v4.webxml.bean;

/**
 * 
 * webxml中的servlet对象
 * 
 * @author sunjie at 2017年1月31日
 *
 */
public class ServletBean {

    private String servletName;

    private String className;

    private String urlPattern;

    public ServletBean(String servletName, String className, String urlPattern) {
        super();
        this.servletName = servletName;
        this.className = className;
        this.urlPattern = urlPattern;
    }

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    @Override
    public String toString() {
        return "ServletBean [servletName=" + servletName + ", className=" + className + ", urlPattern=" + urlPattern
                + "]";
    }

}
