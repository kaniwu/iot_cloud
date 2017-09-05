package com.iot.nero.api_gateway.service.impl;
import com.iot.nero.api_gateway.core.core.ApiMapping;
import com.iot.nero.api_gateway.core.firewall.IpCache;
import com.iot.nero.api_gateway.service.IIpTablesService;
import com.iot.nero.utils.spring.PropertyPlaceholder;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/5
 * Time   下午2:56
 */
public class IpTablesService implements IIpTablesService {
    ServletContext servletContext;
    WebApplicationContext webApplicationContext;


    @ApiMapping("sys.ipTables.set")
    public boolean setIpTableStatus(String isOpen) {
        isOpen=isOpen.trim();
        webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        servletContext = webApplicationContext.getServletContext();
        String path =servletContext.getRealPath("/api_gateway/config/config.properties");
        File file = new File(path);
        Properties prop = new Properties();// 属性集合对象
        try {
            FileInputStream fis = new FileInputStream(file);
            prop.load(fis);// 将属性文件流装载到Properties对象中
            fis.close();// 关闭流
        }catch(IOException e) {
            return false;
        }
        System.out.println(prop.getProperty("ipTable.isOpen"));
        if(prop.getProperty("ipTable.isOpen").equals(isOpen)){
            return true;
        }else{
            prop.setProperty("ipTable.isOpen", isOpen);
            // 文件输出流
            try {
                FileOutputStream fos = new FileOutputStream(file);
                // 将Properties集合保存到流中
                prop.store(fos, "firewall:"+isOpen);
                fos.close();// 关闭流
            }catch (IOException e) {
                return false;
            }
            return true;
        }
    }

    @ApiMapping("sys.ipTables.list")
    public List<String> getIP() throws IOException {
        IpCache ipCache = new IpCache();
        return new ArrayList<String>(ipCache.getIPSet());
    }

    @ApiMapping("sys.ipTables.add")
    public boolean addIP(String ip) throws IOException{
        IpCache ipCache = new IpCache();
        return true;
        //return ipCache.createBlankIP(ip);
    }

    @ApiMapping("sys.ipTables.del")
    public boolean delIP(String ip) throws IOException {
        IpCache ipCache = new IpCache();
        return true;
        //return ipCache.deleteIP(ip);
    }
}
