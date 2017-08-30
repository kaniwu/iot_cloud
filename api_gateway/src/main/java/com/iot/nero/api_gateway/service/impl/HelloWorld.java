package com.iot.nero.api_gateway.service.impl;

import com.iot.nero.api_gateway.core.core.ApiMapping;
import com.iot.nero.api_gateway.dto.Hello;
import com.iot.nero.api_gateway.service.IHelloWorld;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/8/25
 * Time   下午7:44
 */
public class HelloWorld implements IHelloWorld {

    @ApiMapping("api.hello")
    public Hello sayHello(String name,String msg) {
        return new Hello(name,msg,1);
    }

    @ApiMapping("api.ni")
    public String niHao(String ni) {
        return ni;
    }

}
