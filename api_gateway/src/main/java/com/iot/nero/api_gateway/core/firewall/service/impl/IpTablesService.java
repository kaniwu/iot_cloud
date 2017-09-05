package com.iot.nero.api_gateway.core.firewall.service.impl;

import com.iot.nero.api_gateway.core.core.ApiMapping;
import com.iot.nero.api_gateway.core.firewall.IpCache;
import com.iot.nero.api_gateway.core.firewall.service.IIpTablesService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/5
 * Time   下午2:56
 */
public class IpTablesService implements IIpTablesService {
    IpCache ipCache;
    IpTablesService() throws IOException{
        ipCache = new IpCache();
    }
    @ApiMapping("sys.ipTables.list")
    public List<String> getIP() {
        return new ArrayList<String>(ipCache.getIPSet());
    }

    @ApiMapping("sys.ipTables.add")
    public boolean addIP(String ip) throws IOException{
        return ipCache.createBlankIP(ip);
    }

    @ApiMapping("sys.ipTables.del")
    public boolean delIP(String ip) throws IOException {
        return ipCache.deleteIP(ip);
    }
}
