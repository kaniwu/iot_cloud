package com.iot.nero.api_gateway.service.impl;

import com.iot.nero.api_gateway.core.core.ApiMapping;
import com.iot.nero.api_gateway.core.firewall.IpCache;
import com.iot.nero.api_gateway.service.IIpTablesService;

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

    private IpCache ipCache;

    @ApiMapping("sys.ipTables.set")
    public boolean setIpTableStatus(String isOpen) {
        return false;
    }

    @ApiMapping("sys.ipTables.list")
    public List<String> getIP() throws IOException {
        ipCache = new IpCache();
        return new ArrayList<String>(ipCache.getIPSet());
    }

    @ApiMapping("sys.ipTables.add")
    public boolean addIP(String ip) throws IOException{
        ipCache = new IpCache();
        return ipCache.createBlankIP(ip);
    }

    @ApiMapping("sys.ipTables.del")
    public boolean delIP(String ip) throws IOException {
        ipCache = new IpCache();
        return ipCache.deleteIP(ip);
    }
}
