package com.iot.nero.api_gateway.core.mock;

import com.iot.nero.api_gateway.core.core.ApiStore;

public class Mock {

    public Object run(ApiStore.ApiRunnable apiRunnable) {
        Object result;
        Class<?> returnType = apiRunnable.getReturnType();
        if(returnType.getName().equals(String.class.getName())){
            result = new String("sdsdsdsd");
        }else if(returnType.getName().equals((Integer.class.getName()))){
            result = new Integer(23333);
        }else if(returnType.getName().equals(Double.class.getName())){
            result = new Double(2.33);
        }else if(returnType.getName().equals(Boolean.class.getName())){
            result = new Boolean(false);
        }else if(returnType.getName().equals(Long.class.getName())){
            result = new Long(23333333);
        }else {
            //如果是其他类型，请自行构造
            
            result = null;
        }
        return result;
    }
}
