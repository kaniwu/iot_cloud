package com.iot.nero.api_gateway.core.firewall;
import com.alibaba.com.caucho.hessian.io.InputStreamSerializer;
import com.iot.nero.api_gateway.common.IOUtils;
import com.iot.nero.utils.spring.PropertyPlaceholder;

import java.io.*;
import java.util.HashSet;


/**
 * Author gtBailly
 * Email  bailly.gt@gmail.com
 * Date   2017/8/31
 * Time   上午11:38
 */
public class IpCache {
    private HashSet<String> ipSet = null;
    private static final String IP_CACHE_DIR = PropertyPlaceholder.getProperty("ipTable.file").toString();

    public IpCache() throws IOException {

        ipSet =new HashSet<String>();
        cacheSet();
    }
    public Object findIP(String ip) {
        if(ipSet.contains(ip)) return "拒绝访问";
        else return null;
    }

    private void cacheSet() throws IOException {
       String ips[] =readCacheFile(IP_CACHE_DIR);
       for(int i=0;i<ips.length;i++){
           System.out.println(ips[i]);
            ipSet.add(ips[i]);
       }
    }

    /**
     *读取缓存文件
     * @param dir
     * @return
     */
    private String [] readCacheFile(String dir) throws IOException {
        String line="";
        InputStream inputStream = null;
        InputStreamReader inputStreamReader  = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = this.getClass().getResourceAsStream(IP_CACHE_DIR);
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            line = bufferedReader.readLine();

            String[] ips = line.split(";");
            return ips;
        }catch (IOException e){
            throw e;
        }finally {
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        }
    }

    /**
     * 返回ipSet
     */
     public HashSet<String> getIPSet(){
        return ipSet;
     }
}
