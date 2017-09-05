package com.iot.nero.api_gateway.service;

import com.iot.nero.api_gateway.core.mock.Entity.ApiMock;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/5
 * Time   下午1:09
 */
public interface IMockService {

    Map<String, ApiMock> getMocks() throws IOException;

    Boolean addMock(String apiName, String apiReturn) throws IOException;

    Boolean delMock(String mockName) throws IOException;

    Boolean setMockState(String mockState);

}
