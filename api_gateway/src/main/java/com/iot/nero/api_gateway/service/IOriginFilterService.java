package com.iot.nero.api_gateway.service;

import java.util.Map;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/5
 * Time   下午4:10
 */
public interface IOriginFilterService {


    boolean addOrigin(String name,String origin);

    boolean delOrigin(String name,String origin);

    Map getOrigin();
}
