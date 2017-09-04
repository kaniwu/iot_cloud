package com.iot.nero.filter;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author nero
 * Date   2016/10/20 0020
 * Time   16:15
 * Email  nerosoft@outlook.com
 */
@Component
public class CORSFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String origin = (String) servletRequest.getRemoteHost()+":"+servletRequest.getRemotePort();
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        if(request.getHeader("Origin")!=null) {
            if (request.getHeader("Origin").contains("localhost")) {
                response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            }else if(request.getHeader("Origin").contains("47.94.251.146")){
                response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            }else if(request.getHeader("Origin").contains("cenocloud")){
                response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            }else if(request.getHeader("Origin").contains("47.94.46.29")){
                response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            }
        }
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization");
        response.setHeader("Access-Control-Allow-Credentials","true");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
