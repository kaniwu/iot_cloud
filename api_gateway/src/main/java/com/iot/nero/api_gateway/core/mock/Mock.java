package com.iot.nero.api_gateway.core.mock;

import com.iot.nero.api_gateway.core.core.ApiStore;
import com.iot.nero.api_gateway.core.exceptions.MockApiNotFoundException;
import com.iot.nero.utils.spring.PropertyPlaceholder;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Mock {
    private Map<String,String> apiMockCache;

    public Mock(){
        init();
    }
    @PostConstruct
    public void init(){
        apiMockCache = new HashMap<String, String>();
    }


    public Object run(ApiStore.ApiRunnable apiRunnable) throws IOException, MockApiNotFoundException {
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


            //查缓存
            if((result = apiMockCache.get(apiRunnable.getApiName()))!=null){
                return result;
            }else{
                File file = new File(PropertyPlaceholder.getProperty("mock.file").toString());

                FileReader fileReader = new FileReader(file);

                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String line;
                Boolean isApiFound = false;
                while((line = bufferedReader.readLine())!=null){
                    String[] api_mapper = line.split("#");
                    if(apiRunnable.getApiName().equals(api_mapper[0])){
                        isApiFound = true;
                        return api_mapper[1];
                    }
                    apiMockCache.put(api_mapper[0],api_mapper[1]);
                }
                if(!isApiFound){
                    throw new MockApiNotFoundException("API: "+ apiRunnable.getApiName() +"未找到，请在"+PropertyPlaceholder.getProperty("mock.file").toString()+"中添加！");
                }
            }
        }
        return result;
    }
}