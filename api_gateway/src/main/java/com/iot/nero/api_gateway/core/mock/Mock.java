package com.iot.nero.api_gateway.core.mock;

import com.iot.nero.api_gateway.common.IOUtils;
import com.iot.nero.api_gateway.core.core.ApiMapping;
import com.iot.nero.api_gateway.core.core.ApiStore;
import com.iot.nero.api_gateway.core.exceptions.MockApiNotFoundException;
import com.iot.nero.api_gateway.core.mock.Entity.ApiMock;
import com.iot.nero.utils.spring.PropertyPlaceholder;
import javafx.beans.property.Property;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mock {

    private static Map<String,ApiMock> apiMockCache = new HashMap<String,ApiMock>();
    private static final String MOCK_FILR_DIR = PropertyPlaceholder.getProperty("mock.file").toString();


    public Mock() throws IOException {
        //apiMockCache = new HashMap<String, ApiMock>();
        init();
    }


    public void init() throws IOException {
        String line;
        String[] apiMember;

        InputStream inputStream = null;
        InputStreamReader inputStreamReader  = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = this.getClass().getResourceAsStream(MOCK_FILR_DIR);
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            while ((line = bufferedReader.readLine()) != null) {
                apiMember = line.split("#");
                if (apiMockCache.get(apiMember[0]) == null) {
                    apiMockCache.put(apiMember[0], new ApiMock(apiMember[0], apiMember[1]));
                }
            }
        }catch (IOException e){
            throw e;
        }finally {
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
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
        }else if(returnType.getName().equals(Float.class.getName())) {
            result = new Float(2.33);
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

    public Boolean addApiMocksToCache(ApiMock apiMock){
        apiMockCache.put(apiMock.getApiName(),apiMock);
        return true;
    }
}