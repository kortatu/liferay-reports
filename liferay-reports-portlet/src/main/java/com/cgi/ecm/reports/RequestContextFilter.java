package com.cgi.ecm.reports;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * We simulate Spring MVC RequestContextListener here so we don't need to change Liferay ROOT webapp configuration
 * This class is meant to be called from Pre and Post Action (setRequestContext in Pre and removeRequestContext in Post)
 */
class RequestContextFilter {
    static final String REQUEST_ATTRIBUTES_ATTRIBUTE = RequestContextListener.class.getName() + ".REQUEST_ATTRIBUTES";

    public RequestContextFilter() {
    }

    void setRequestContext(HttpServletRequest req) {
        ServletRequestAttributes attributes = new ServletRequestAttributes(req);
        req.setAttribute(REQUEST_ATTRIBUTES_ATTRIBUTE, attributes);
        LocaleContextHolder.setLocale(req.getLocale());
        RequestContextHolder.setRequestAttributes(attributes);
    }

    void removeRequestContext(HttpServletRequest req) {
        ServletRequestAttributes attributes = null;
        Object reqAttr = req.getAttribute(REQUEST_ATTRIBUTES_ATTRIBUTE);
        if (reqAttr instanceof ServletRequestAttributes) {
            attributes = (ServletRequestAttributes) reqAttr;
        }
        RequestAttributes threadAttributes = RequestContextHolder.getRequestAttributes();
        if (threadAttributes != null) {
            // We're assumably within the original request thread...
            LocaleContextHolder.resetLocaleContext();
            RequestContextHolder.resetRequestAttributes();
            if (attributes == null && threadAttributes instanceof ServletRequestAttributes) {
                attributes = (ServletRequestAttributes) threadAttributes;
            }
        }
        if (attributes != null) {
            attributes.requestCompleted();
        }
    }
}