package com.iot.nero.api_gateway.core.mock.service;

import com.iot.nero.api_gateway.core.mock.Entity.ApiMock;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/5
 * Time   下午1:09
 */
public interface IMockService {

    Boolean addMock(ApiMock apiMock);

    Boolean delMock(String mockName);

}
