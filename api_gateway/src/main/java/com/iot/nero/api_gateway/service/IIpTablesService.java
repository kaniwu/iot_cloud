package com.iot.nero.api_gateway.service;

import java.util.List;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/5
 * Time   下午2:54
 */
public interface IIpTablesService {

    boolean setIpTableStatus(String isOpen);

    List<String> getIP();

    boolean addIP(String ip);

    boolean delIP(String ip);

}
