package com.iot.nero.api_gateway.service.impl;

import com.iot.nero.api_gateway.common.ConfigUtil;
import com.iot.nero.api_gateway.core.core.ApiGatewayHandler;
import com.iot.nero.api_gateway.core.core.ApiMapping;

import com.iot.nero.api_gateway.service.IDataTrafficManagerService;
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
public class DataTrafficManager implements IDataTrafficManagerService {

    Map<String, String> configMap;

    @ApiMapping("sys.traffic.status.set")
    public boolean setTrafficManagerStatus(String isOpen) throws IOException {

        configMap = ConfigUtil.configToMap();

        configMap.replace("trafficManager.isOpen ", configMap.get("trafficManager.isOpen "),isOpen);
        ApiGatewayHandler.setTrafficOpen(isOpen);

        return ConfigUtil.mapToConfig(configMap);
    }

    @ApiMapping("sys.traffic.max")
    public boolean setMaxTraffic(Integer maxPool) throws IOException {

        configMap = ConfigUtil.configToMap();

        configMap.replace("trafficManager.maxPool ", configMap.get("trafficManager.maxPool "),maxPool.toString());
        ApiGatewayHandler.setTrafficMax(maxPool.toString());

        return ConfigUtil.mapToConfig(configMap);
    }

    @ApiMapping("sys.traffic.avg")
    public boolean setAvgTraffic(Integer avgFlow) throws IOException {

        configMap = ConfigUtil.configToMap();

        configMap.replace("trafficManager.avgFlow ", configMap.get("trafficManager.avgFlow "),avgFlow.toString());
        ApiGatewayHandler.setTrafficAvg(avgFlow.toString());

        return ConfigUtil.mapToConfig(configMap);
    }
}
