package com.iot.nero.api_gateway.service.impl;

import com.iot.nero.api_gateway.core.core.ApiMapping;
import com.iot.nero.api_gateway.service.IOriginFilterService;
import com.iot.nero.utils.spring.PropertyPlaceholder;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;
/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/5
 * Time   下午4:11
 */
public class OriginService implements IOriginFilterService {
    private static final String CROS_FILE_DIR = PropertyPlaceholder.getProperty("crosFilter.file").toString();

    private WebApplicationContext webApplicationContext;
    private ServletContext servletContext;
    private String savePath;

    @ApiMapping("sys.origin.add")
    public boolean addOrigin(String name,String origin) {
        webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        servletContext = webApplicationContext.getServletContext();
        savePath = servletContext.getRealPath("/WEB-INF/classes"+CROS_FILE_DIR);
        String encoding = "utf-8";
        Map<String ,String >crosMap = new HashMap<String,String>();
        File f = new File(savePath);
        try {
            String line;
            if (f.isFile()&&f.exists()){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(f), encoding));
                //判断配置文件中是否已存在信任站点
                while ((line = bufferedReader.readLine())!=null){
                    String key = line.split(":")[0];
                    String value = line.split(":")[1];
                    if (origin.equals(value)){
                        return false;
                    }
                    crosMap.put(key,value);
                }
                crosMap.put(name,origin);
                Set set = crosMap.entrySet();
                Iterator iterator = set.iterator();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),encoding));
                while (iterator.hasNext()){
                    Map.Entry entry = (Map.Entry)iterator.next();
                    String add = entry.getKey().toString()+":"+entry.getValue().toString();
                    bufferedWriter.write(add);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                }
                //若配置文件中不存在则写入配置文件
                bufferedWriter.close();
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @ApiMapping("sys.origin.del")
    public boolean delOrigin(String name,String origin) {
        File f = new File(CROS_FILE_DIR);
        Map<String ,String >crosMap = new HashMap<String,String>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
            String line;
            //将从配置文件中读取的不删除的信息加入map中
            while ((line=bufferedReader.readLine())!=null){
                String key = line.split(":")[0];
                String value = line.split(":")[1];
                if (!(key.equals(name)&&value.equals(origin))){
                    crosMap.put(key,value);
                }
                Set set = crosMap.entrySet();
                Iterator iterator = set.iterator();
                Writer writer = new FileWriter(f);
                while (iterator.hasNext()){
                    Map.Entry entry = (Map.Entry)iterator.next();
                    String context = entry.getKey().toString()+":"+entry.getValue().toString();
                    writer.write(context);
                    writer.flush();
                }
                writer.close();
            }
            return true;

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
