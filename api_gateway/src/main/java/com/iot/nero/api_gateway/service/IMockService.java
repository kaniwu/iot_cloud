package com.iot.nero.api_gateway.service;

import com.iot.nero.api_gateway.core.mock.Entity.ApiMock;

import java.util.List;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/5
 * Time   下午1:09
 */
public interface IMockService {

    Boolean setMockStatus(String isOpen);

    List<ApiMock> getMocks();

    Boolean addMock(ApiMock apiMock);

    Boolean delMock(String mockName);

}
