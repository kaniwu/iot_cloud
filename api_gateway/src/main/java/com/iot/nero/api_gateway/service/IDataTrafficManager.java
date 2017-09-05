package com.iot.nero.api_gateway.service;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/5
 * Time   下午3:04
 */
public interface IDataTrafficManager {

    boolean setTrafficManagerStatus(String isOpen) throws IOException;

    boolean setMaxTraffic(Integer maxPool) throws IOException;

    boolean setAvgTraffic(Integer avgPool) throws IOException;

}
