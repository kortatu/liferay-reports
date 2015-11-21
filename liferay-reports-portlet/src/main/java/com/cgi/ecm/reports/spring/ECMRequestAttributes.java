package com.cgi.ecm.reports.spring;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.portlet.context.PortletRequestAttributes;

import javax.portlet.PortletRequest;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by kortatu on 31/07/15.
 */
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class ECMRequestAttributes {

    private final RequestAttributes requestAttributes;

    public ECMRequestAttributes() {
        this.requestAttributes = RequestContextHolder.getRequestAttributes();
    }

    public RequestAttributes getRequestAttributes() {
        return requestAttributes;
    }

    public boolean inServletRequest() {
        return requestAttributes instanceof ServletRequestAttributes;
    }

    public boolean inPortletRequest() {
        return requestAttributes instanceof PortletRequestAttributes;
    }

    public PortletRequest safeGetPortletRequest() {
        if (inPortletRequest())
            return ((PortletRequestAttributes)requestAttributes).getRequest();
        else
            return null;
    }

    public HttpServletRequest safeGetServletRequest() {
        if (inServletRequest())
            return ((ServletRequestAttributes)requestAttributes).getRequest();
        else
            return null;
    }

    public Object getServletOrPortlet() {
        if (inServletRequest())
            return safeGetServletRequest();
        else if (inPortletRequest())
                return safeGetPortletRequest();
        else
            return null;
    }
}
