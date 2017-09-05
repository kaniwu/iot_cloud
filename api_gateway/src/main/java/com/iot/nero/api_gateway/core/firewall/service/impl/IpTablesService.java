package com.iot.nero.api_gateway.core.firewall.service.impl;

import com.iot.nero.api_gateway.core.core.ApiMapping;
import com.iot.nero.api_gateway.core.firewall.service.IIpTablesService;

import java.util.List;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/5
 * Time   下午2:56
 */
public class IpTablesService implements IIpTablesService {
    @ApiMapping("sys.ipTables.list")
    public List<String> getIP() {
        return null;
    }

    @ApiMapping("sys.ipTables.add")
    public boolean addIP(String ip) {
        return false;
    }

    @ApiMapping("sys.ipTables.del")
    public boolean delIP(String ip) {
        return false;
    }
}
