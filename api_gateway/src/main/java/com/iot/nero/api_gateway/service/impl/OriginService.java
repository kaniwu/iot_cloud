package com.iot.nero.api_gateway.service.impl;

import com.iot.nero.api_gateway.core.core.ApiMapping;
import com.iot.nero.api_gateway.service.IOriginFilterService;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/5
 * Time   下午4:11
 */
public class OriginService implements IOriginFilterService {

    @ApiMapping("sys.origin.add")
    public boolean addOrigin(String origin) {
        return false;
    }

    @ApiMapping("sys.origin.del")
    public boolean delOrigin(String origin) {
        return false;
    }
}
