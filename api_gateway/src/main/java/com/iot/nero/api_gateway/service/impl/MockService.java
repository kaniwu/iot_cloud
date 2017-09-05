package com.iot.nero.api_gateway.service.impl;

import com.iot.nero.api_gateway.core.core.ApiMapping;
import com.iot.nero.api_gateway.core.mock.Entity.ApiMock;
import com.iot.nero.api_gateway.core.mock.Mock;
import com.iot.nero.api_gateway.service.IMockService;
import com.iot.nero.utils.spring.PropertyPlaceholder;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.List;
import java.util.Map;

import java.util.Properties;
/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/5
 * Time   下午1:09
 */
public class MockService implements IMockService {

    private static final String MOCK_FILR_DIR = PropertyPlaceholder.getProperty("mock.file").toString();

    private WebApplicationContext webApplicationContext;
    private ServletContext servletContext;
    private String savePath;

    @ApiMapping("sys.mock.set")
    public Boolean setMockStatus(String isOpen) throws IOException {

        webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        servletContext = webApplicationContext.getServletContext();
        savePath = servletContext.getRealPath("/WEB-INF/classes/api_gateway/config/config.properties");

        Properties properties = new Properties();
        InputStream inputStream = new FileInputStream(savePath);
        OutputStream outputStream = new FileOutputStream(savePath);

        properties.load(inputStream);
        properties.setProperty("auth.username", PropertyPlaceholder.getProperty("auth.username").toString());
        properties.setProperty("auth.password", PropertyPlaceholder.getProperty("auth.password").toString());
        properties.setProperty("mock.isOpen", isOpen);
        properties.setProperty("mock.file", PropertyPlaceholder.getProperty("mock.file").toString());
        properties.setProperty("ipTable.isOpen", PropertyPlaceholder.getProperty("ipTable.isOpen").toString());
        properties.setProperty("ipTable.file", PropertyPlaceholder.getProperty("ipTable.file").toString());
        properties.setProperty("trafficManager.isOpen", PropertyPlaceholder.getProperty("trafficManager.isOpen").toString());
        properties.setProperty("trafficManager.maxPool", PropertyPlaceholder.getProperty("trafficManager.maxPool").toString());
        properties.setProperty("trafficManager.avgFlow", PropertyPlaceholder.getProperty("trafficManager.avgFlow").toString());
        properties.store(outputStream, "mockState change to "+isOpen);

        return true;
    }

    @ApiMapping("sys.mock.list")

    public Map<String, ApiMock> getMocks() throws IOException {
        Mock mock = new Mock();
        return mock.getMocks();
    }

    public Boolean addMock(ApiMock apiMock) {
        return null;
    }

    @ApiMapping("sys.mock.add")
    public Boolean addMock(String apiName, String apiReturn) throws IOException {

        webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        servletContext = webApplicationContext.getServletContext();
        savePath = servletContext.getRealPath("/WEB-INF/classes"+MOCK_FILR_DIR);

        BufferedWriter bufferedWriter = null;
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;


            Mock mock = new Mock();
            if(mock.getMocks().get(apiName)==null){

                outputStream = new FileOutputStream(savePath);
                outputStreamWriter = new OutputStreamWriter(outputStream);
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                for(Map.Entry<String, ApiMock> entry : mock.getMocks().entrySet()){
                    bufferedWriter.write(entry.getKey()+"#"+entry.getValue().getApiReturn()+"\n");
                    bufferedWriter.flush();
                }

                bufferedWriter.write(apiName+"#"+apiReturn+"\n");
                bufferedWriter.flush();

                bufferedWriter.close();
                outputStreamWriter.close();
                outputStream.close();
                return true;
            }

        return false;
    }

    @ApiMapping("sys.mock.del")
    public Boolean delMock(String mockName) throws IOException {

        webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        servletContext = webApplicationContext.getServletContext();
        savePath = servletContext.getRealPath("/WEB-INF/classes"+MOCK_FILR_DIR);

        BufferedWriter bufferedWriter = null;
        OutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;

            Mock mock = new Mock();

            Map<String, ApiMock> apiMocks = mock.getMocks();
            if(apiMocks.get(mockName) != null){

                apiMocks.remove(mockName);

                outputStream = new FileOutputStream(savePath);
                outputStreamWriter = new OutputStreamWriter(outputStream);
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                for(Map.Entry<String, ApiMock> entry : apiMocks.entrySet()){
                    bufferedWriter.write(entry.getKey()+"#"+entry.getValue().getApiReturn()+"\n");
                    bufferedWriter.flush();
                }

                bufferedWriter.close();
                outputStreamWriter.close();
                outputStream.close();
                return true;
            }




        return false;
    }


}
