package com.iot.nero.api_gateway.service.impl;

import com.iot.nero.api_gateway.core.core.ApiMapping;
import com.iot.nero.api_gateway.service.IDataTrafficManager;
import com.iot.nero.utils.spring.PropertyPlaceholder;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.Properties;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/5
 * Time   下午3:06
 */
public class DataTrafficManager implements IDataTrafficManager {

    private WebApplicationContext webApplicationContext;
    private ServletContext servletContext;
    private String savePath;

    @ApiMapping("sys.traffic.set")
    public boolean setTrafficManagerStatus(String isOpen) throws IOException {

        webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        servletContext = webApplicationContext.getServletContext();
        savePath = servletContext.getRealPath("/WEB-INF/classes/api_gateway/config/config.properties");

        Properties properties = new Properties();
        InputStream inputStream = new FileInputStream(savePath);
        OutputStream outputStream = new FileOutputStream(savePath);

        properties.load(inputStream);
        properties.setProperty("auth.username", PropertyPlaceholder.getProperty("auth.username").toString());
        properties.setProperty("auth.password", PropertyPlaceholder.getProperty("auth.password").toString());
        properties.setProperty("mock.isOpen", PropertyPlaceholder.getProperty("mock.isOpen").toString());
        properties.setProperty("mock.file", PropertyPlaceholder.getProperty("mock.file").toString());
        properties.setProperty("ipTable.isOpen", PropertyPlaceholder.getProperty("ipTable.isOpen").toString());
        properties.setProperty("ipTable.file", PropertyPlaceholder.getProperty("ipTable.file").toString());
        properties.setProperty("trafficManager.isOpen", isOpen);
        properties.setProperty("trafficManager.maxPool", PropertyPlaceholder.getProperty("trafficManager.maxPool").toString());
        properties.setProperty("trafficManager.avgFlow", PropertyPlaceholder.getProperty("trafficManager.avgFlow").toString());
        properties.store(outputStream, "trafficManager change to "+isOpen);

        return true;
    }

    @ApiMapping("sys.traffic.max")
    public boolean setMaxTraffic(Integer maxPool) throws IOException {

        webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        servletContext = webApplicationContext.getServletContext();
        savePath = servletContext.getRealPath("/WEB-INF/classes/api_gateway/config/config.properties");

        Properties properties = new Properties();
        InputStream inputStream = new FileInputStream(savePath);
        OutputStream outputStream = new FileOutputStream(savePath);

        properties.load(inputStream);
        properties.setProperty("auth.username", PropertyPlaceholder.getProperty("auth.username").toString());
        properties.setProperty("auth.password", PropertyPlaceholder.getProperty("auth.password").toString());
        properties.setProperty("mock.isOpen", PropertyPlaceholder.getProperty("mock.isOpen").toString());
        properties.setProperty("mock.file", PropertyPlaceholder.getProperty("mock.file").toString());
        properties.setProperty("ipTable.isOpen", PropertyPlaceholder.getProperty("ipTable.isOpen").toString());
        properties.setProperty("ipTable.file", PropertyPlaceholder.getProperty("ipTable.file").toString());
        properties.setProperty("trafficManager.isOpen", PropertyPlaceholder.getProperty("trafficManager.isOpen").toString());
        properties.setProperty("trafficManager.maxPool", maxPool.toString());
        properties.setProperty("trafficManager.avgFlow", PropertyPlaceholder.getProperty("trafficManager.avgFlow").toString());
        properties.store(outputStream, "trafficManager.maxPool change to "+maxPool);

        return true;
    }

    @ApiMapping("sys.traffic.avg")
    public boolean setAvgTraffic(Integer avgPool) throws IOException {

        webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        servletContext = webApplicationContext.getServletContext();
        savePath = servletContext.getRealPath("/WEB-INF/classes/api_gateway/config/config.properties");

        Properties properties = new Properties();
        InputStream inputStream = new FileInputStream(savePath);
        OutputStream outputStream = new FileOutputStream(savePath);

        properties.load(inputStream);
        properties.setProperty("auth.username", PropertyPlaceholder.getProperty("auth.username").toString());
        properties.setProperty("auth.password", PropertyPlaceholder.getProperty("auth.password").toString());
        properties.setProperty("mock.isOpen", PropertyPlaceholder.getProperty("mock.isOpen").toString());
        properties.setProperty("mock.file", PropertyPlaceholder.getProperty("mock.file").toString());
        properties.setProperty("ipTable.isOpen", PropertyPlaceholder.getProperty("ipTable.isOpen").toString());
        properties.setProperty("ipTable.file", PropertyPlaceholder.getProperty("ipTable.file").toString());
        properties.setProperty("trafficManager.isOpen", PropertyPlaceholder.getProperty("trafficManager.isOpen").toString());
        properties.setProperty("trafficManager.maxPool", PropertyPlaceholder.getProperty("trafficManager.maxPool").toString());
        properties.setProperty("trafficManager.avgFlow", avgPool.toString());
        properties.store(outputStream, "trafficManager.avgPool change to "+avgPool);

        return true;
    }
}
