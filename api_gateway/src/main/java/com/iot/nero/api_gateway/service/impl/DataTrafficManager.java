package com.iot.nero.api_gateway.service.impl;

import com.iot.nero.api_gateway.core.core.ApiMapping;
import com.iot.nero.api_gateway.service.IDataTrafficManager;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/5
 * Time   下午3:06
 */
public class DataTrafficManager implements IDataTrafficManager {

    @ApiMapping("sys.traffic.set")
    public boolean setTrafficManagerStatus(String isOpen) {
        return false;
    }

    @ApiMapping("sys.traffic.max")
    public boolean setMaxTraffic(Integer maxPool) {
        return false;
    }

    @ApiMapping("sys.traffic.avg")
    public boolean setAvgTraffic(Integer avgPool) {
        return false;
    }
}
