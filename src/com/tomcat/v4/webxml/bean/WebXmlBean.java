package com.tomcat.v4.webxml.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * webxml解析对象
 * 
 * @author sunjie at 2017年1月31日
 *
 */
public class WebXmlBean {

    private Map<String, ServletBean> servletBeans;

    public Map<String, ServletBean> getServletBeans() {
        return servletBeans;
    }

    public void setServletBeans(Map<String, ServletBean> servletBeans) {
        this.servletBeans = servletBeans;
    }

    public void putServletBean(String servletName, ServletBean servletBean) {
        if (this.servletBeans == null) {
            this.servletBeans = new HashMap<String, ServletBean>();
        }
        this.servletBeans.put(servletName, servletBean);
    }

    public ServletBean getServletBean(String servletName) {
        if (this.servletBeans == null) {
            return null;
        }
        return this.servletBeans.get(servletName);
    }

    public ServletBean getServletBean4Url(String uri) {
        if (this.servletBeans == null || "".equals(uri)) {
            return null;
        }
        for (ServletBean servletBean : this.servletBeans.values()) {
            if (uri.equals(servletBean.getUrlPattern())) {
                return servletBean;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "WebXmlBean [servletBeans=" + servletBeans + "]";
    }

}
