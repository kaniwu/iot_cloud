package com.iot.nero.api_gateway.service;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/5
 * Time   下午3:04
 */
public interface IDataTrafficManager {

    boolean setTrafficManagerStatus(String isOpen);

    boolean setMaxTraffic(Integer maxPool);

    boolean setAvgTraffic(Integer avgPool);

}
