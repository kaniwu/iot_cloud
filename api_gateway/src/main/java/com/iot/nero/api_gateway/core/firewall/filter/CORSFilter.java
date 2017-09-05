package com.iot.nero.api_gateway.core.firewall.filter;


import com.iot.nero.utils.spring.PropertyPlaceholder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Author nero
 * Date   2016/10/20 0020
 * Time   16:15
 * Email  nerosoft@outlook.com
 */
@Component
public class CORSFilter implements Filter {

    private static final String CROS_FILE_DIR = PropertyPlaceholder.getProperty("crosFilter.file").toString();
    private Map<String,String> crosMap = new HashMap<String, String>();
    public void init(FilterConfig filterConfig) throws ServletException {
            File f = new File(CROS_FILE_DIR);
            BufferedReader br = null;
            String line;
            try {
                br = new BufferedReader(new FileReader(f));
                while ((line = br.readLine()) != null) {
                    String key = line.split(":")[0];
                    String value = line.split(":")[1];
                    crosMap.put(key,value);
                }
                br.close();
            }catch (Exception e){
                e.printStackTrace();
            }
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String origin = (String) servletRequest.getRemoteHost()+":"+servletRequest.getRemotePort();
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        if(request.getHeader("Origin")!=null) {
            Set set = crosMap.entrySet();
            Iterator it = set.iterator();
            while (it.hasNext()){
                    Map.Entry entry = (Map.Entry)it.next();
                if (request.getHeader("Origin").contains(entry.getValue().toString())) {
                    response.setHeader("Access-Control-Allow-Origin",request.getHeader ("Origin"));
                }
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
