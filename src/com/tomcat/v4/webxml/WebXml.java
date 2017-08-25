package com.tomcat.v4.webxml;

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.tomcat.v4.webxml.bean.ServletBean;
import com.tomcat.v4.webxml.bean.WebXmlBean;

public class WebXml {

    public static WebXmlBean getWebXmlBean() {
        WebXmlBean webXmlBean = new WebXmlBean();

        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read("WEB-INF/web.xml");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        if (document == null) {
            return webXmlBean;
        }
        Element rootElement = document.getRootElement();
        Iterator<Element> elementIterator = rootElement.elementIterator("servlet");
        Element servletElement = null;
        while (elementIterator.hasNext()) {
            servletElement = elementIterator.next();
            Element servletNameElement = servletElement.element("servlet-name");
            Element servletClassElement = servletElement.element("servlet-class");
            String servletName = servletNameElement.getText();
            String servletClass = servletClassElement.getText();
            ServletBean servletBean = new ServletBean(servletName, servletClass, "");
            webXmlBean.putServletBean(servletName, servletBean);
            System.out.println("====" + servletName + "===" + servletClass);
        }

        Iterator<Element> elementMappingIterator = rootElement.elementIterator("servlet-mapping");
        Element servletMappingElement = null;
        while (elementMappingIterator.hasNext()) {
            servletMappingElement = elementMappingIterator.next();
            Element servletNameElement = servletMappingElement.element("servlet-name");
            Element servletUrlPatternElement = servletMappingElement.element("url-pattern");
            String servletName = servletNameElement.getText();
            String servletUrlPattern = servletUrlPatternElement.getText();
            ServletBean servletBean = webXmlBean.getServletBean(servletName);
            if (servletBean != null) {
                servletBean.setUrlPattern(servletUrlPattern);
                System.out.println("==map==" + servletName + "===" + servletUrlPattern);
            }
        }
        return webXmlBean;
    }

    public static void main(String[] args) throws DocumentException {
        WebXmlBean webXmlBean = WebXml.getWebXmlBean();
        System.out.println(webXmlBean);
    }
}
