package com.iot.nero.api_gateway.core.firewall;


public class IpCacheTest {

    public static void main(String[] args){
        IpCache ipCache = new IpCache();
        ipCache.createBlankIP("2.12.123.24");
    }
}