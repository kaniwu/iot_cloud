package com.iot.nero.api_gateway.core.firewall;
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
    private static HashSet<String> ipSet = null;
    private static final String IP_CACHE_DIR = "D:/XAMPP/htdocs/iot_cloud/api_gateway/src/main/resources/api_gateway/config/ip_tables.txt";//PropertyPlaceholder.getProperty("ipTable.file").toString();

    IpCache(){
       /* if(ipSet==null){
            ipSet =new HashSet<String>();
            cacheSet();
        }
        */
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

    /**
     *读取缓存文件
     * @param dir
     * @return
     */
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
        String [] ips =line.split(";");
        return ips;
    }

    /**
     * 添加新的ip到缓存文件
     */
    public void addIPIntoFile(File file,String ip) throws IOException{
        BufferedWriter out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        out.write(ip);
        out.close();
    }

    /**
     * 添加新的ip到黑名单，并更新黑名单缓存
     * @param ip
     */
    public void createBlankIP(String ip){
        //添加ip到缓存文件中
        try {
            File file =new File(IP_CACHE_DIR);
            if (file.isFile() && file.exists())
            { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(read);
                String line;
                String rightIP="";
                while((line=bufferedReader.readLine())!=null){
                    if(line==""){
                        line+=ip;
                    }
                    else {
                        line+=";" + ip;
                    }
                    rightIP=line;
                    System.out.println(line);
                }
                bufferedReader.close();
                read.close();
                System.out.println(rightIP);
                addIPIntoFile(file,rightIP);
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
        //更新set
        //ipSet.add(ip);
    }


}
