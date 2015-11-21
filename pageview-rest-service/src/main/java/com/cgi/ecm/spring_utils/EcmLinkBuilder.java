package com.cgi.ecm.spring_utils;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.lang.reflect.Method;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cgi.ecm.reports.rest_service.CountByReportsController;

public class EcmLinkBuilder {
    
    public static Link buildLink(Class<CountByReportsController> clazz, String methodName, String rel, String... params) throws NoSuchMethodException {
        Method method = getFirstRequestMethodWithName(clazz, methodName);
        String href = hrefToMethodWithParams(method, params);
        return new Link(href,rel);
    }

    private static String hrefToMethodWithParams(Method method, String... params) {
        ControllerLinkBuilder linkTo = linkTo(method, CountByReportsController.class);
        StringBuilder paramsBuilder = new StringBuilder(linkTo.toString());
        if (params.length>0) {
            paramsBuilder.append("{?");
            for (int i = 0; i < params.length; i++) {
        	paramsBuilder.append(params[i]);
        	if (i+1<params.length) {
        	    paramsBuilder.append(",");
        	}
            }
            paramsBuilder.append("}");
        }
        return paramsBuilder.toString();
    }

    private static Method getFirstRequestMethodWithName(Class<?> clazz, String methodName) throws NoSuchMethodException {
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals(methodName)) {
        	RequestMapping[] annotationsByType = method.getAnnotationsByType(RequestMapping.class);
        	if (annotationsByType!=null && annotationsByType.length>0)
        	    return method;
            }
        }
        throw new NoSuchMethodException(clazz.getName()+"."+methodName);
    }

}
