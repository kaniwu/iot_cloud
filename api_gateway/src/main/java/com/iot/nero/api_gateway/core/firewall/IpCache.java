package com.iot.nero.api_gateway.core.firewall;
import java.io.*;
import java.util.HashSet;


/**
 * Author gtBailly
 * Email  bailly.gt@gmail.com
 * Date   2017/8/31
 * Time   上午11:38
 */
public class IpCache {
    private static HashSet<String> ipSet = null;
    private static final String IP_CACHE_DIR ="";

    IpCache(){
        if(ipSet==null){
            ipSet =new HashSet<String>();
            cacheSet();
        }
    }
    public Object findIP(String ip) {
        if(ipSet.contains(ip)) return null;
        else return "sussess";
    }

    //读取缓存文件中的数据到集合中
    private void cacheSet(){
       String ips[] =readCacheFile(IP_CACHE_DIR);
       for(int i=0;i<ips.length;i++){
           ipSet.add(ips[i]);
       }
    }
    //读取缓存文件
    private String [] readCacheFile(String dir){
        String line="";
        try {
            File file =new File(dir);
            if (file.isFile() && file.exists())
            { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(read);
                line = bufferedReader.readLine();
                bufferedReader.close();
                read.close();
            }
            else
            {
                System.out.println("找不到指定的文件");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        String ips[] =line.split(";");
        return ips;
    }

    public void createBlankIP(String ip){
        //连接数据库，将IP加入数据库
        //加入缓存
        //更新set
    }


}
