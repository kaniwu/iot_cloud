package com.iot.nero.api_gateway.service.impl;

import com.iot.nero.api_gateway.core.core.ApiMapping;
import com.iot.nero.api_gateway.core.mock.Entity.ApiMock;
import com.iot.nero.api_gateway.service.IMockService;

import java.util.List;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/5
 * Time   下午1:09
 */
public class MockService implements IMockService {

    @ApiMapping("sys.mock.set")
    public Boolean setMockStatus(String isOpen) {
        return null;
    }

    @ApiMapping("sys.mock.list")
    public List<ApiMock> getMocks() {
        return null;
    }

    @ApiMapping("sys.mock.add")
    public Boolean addMock(ApiMock apiMock) {
        return null;
    }

    @ApiMapping("sys.mock.del")
    public Boolean delMock(String mockName) {
        return null;
    }
}
