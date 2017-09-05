package com.iot.nero.api_gateway.service.impl;

import com.iot.nero.api_gateway.core.core.ApiMapping;
<<<<<<< HEAD:api_gateway/src/main/java/com/iot/nero/api_gateway/core/firewall/service/impl/IpTablesService.java
import com.iot.nero.api_gateway.core.firewall.IpCache;
import com.iot.nero.api_gateway.core.firewall.service.IIpTablesService;
=======
import com.iot.nero.api_gateway.service.IIpTablesService;
>>>>>>> iot_cloud/master:api_gateway/src/main/java/com/iot/nero/api_gateway/service/impl/IpTablesService.java

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

    @ApiMapping("sys.ipTables.set")
    public boolean setIpTableStatus(String isOpen) {
        return false;
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
