package com.iot.nero.api_gateway.service.impl;

import com.iot.nero.api_gateway.common.ConfigUtil;
import com.iot.nero.api_gateway.core.core.ApiMapping;
import com.iot.nero.api_gateway.service.IDataTrafficManager;
import com.iot.nero.utils.spring.PropertyPlaceholder;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/5
 * Time   下午3:06
 */
public class DataTrafficManager implements IDataTrafficManager {

    private ConfigUtil configUtil;
    Map<String, String> configMap;

    @ApiMapping("sys.traffic.set")
    public boolean setTrafficManagerStatus(String isOpen) throws IOException {

        configUtil = new ConfigUtil();
        configMap = configUtil.configToMap();

        configMap.replace("trafficManager.isOpen ", configMap.get("trafficManager.isOpen "),isOpen);

        return configUtil.mapToConfig(configMap);
    }

    @ApiMapping("sys.traffic.max")
    public boolean setMaxTraffic(Integer maxPool) throws IOException {

        configUtil = new ConfigUtil();
        configMap = configUtil.configToMap();

        configMap.replace("trafficManager.maxPool ", configMap.get("trafficManager.maxPool "),maxPool.toString());

        return configUtil.mapToConfig(configMap);
    }

    @ApiMapping("sys.traffic.avg")
    public boolean setAvgTraffic(Integer avgPool) throws IOException {

        configUtil = new ConfigUtil();
        configMap = configUtil.configToMap();

        Boolean b = configMap.replace("trafficManager.avgPool ", configMap.get("trafficManager.avgPool "),avgPool.toString());
        System.out.println(b);

        return configUtil.mapToConfig(configMap);
    }
}
