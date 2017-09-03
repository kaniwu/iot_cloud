package com.iot.nero.api_gateway.core.mock;

import com.iot.nero.api_gateway.core.core.ApiMapping;
import com.iot.nero.api_gateway.core.core.ApiStore;
import com.iot.nero.api_gateway.core.exceptions.MockApiNotFoundException;
import com.iot.nero.api_gateway.core.mock.Entity.ApiMock;
import com.iot.nero.utils.spring.PropertyPlaceholder;
import javafx.beans.property.Property;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mock {
    private Map<String,ApiMock> apiMockCache;

    public Mock() throws IOException {
        init();
    }
    @PostConstruct
    public void init() throws IOException {
        apiMockCache = new HashMap<String, ApiMock>();
        File file = new File(PropertyPlaceholder.getProperty("mock.file").toString());

        BufferedReader bufr = new BufferedReader(new FileReader(file));

        String line;
        String[] apiMember;
        ApiMock apiMock;

        while((line = bufr.readLine())!=null){
            apiMember = line.split("#");
            apiMock = new ApiMock(apiMember[0], apiMember[1]);
            if(apiMockCache.get(apiMember[0])==null){
                apiMockCache.put(apiMember[0], apiMock);
            }
        }
    }


    public Object run(ApiStore.ApiRunnable apiRunnable) throws MockApiNotFoundException {
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
        }else if(returnType.getName().equals(Character.class.getName())){
            result = 'd';
        }else {
            //如果是其他类型，请自行构造


            //查缓存
            if((result = apiMockCache.get(apiRunnable.getApiName()))!=null){
                return result;
            }else{
                    throw new MockApiNotFoundException("API: "+ apiRunnable.getApiName() +"未找到，请在"+PropertyPlaceholder.getProperty("mock.file").toString()+"中添加！");
                }
            }
        return result;
    }

    public Map<String,ApiMock> getMocks() {

        return this.apiMockCache;
    }
}