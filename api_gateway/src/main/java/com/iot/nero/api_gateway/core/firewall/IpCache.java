package com.iot.nero.api_gateway.core.firewall;
import com.alibaba.com.caucho.hessian.io.InputStreamSerializer;
import com.iot.nero.api_gateway.common.IOUtils;
import com.iot.nero.utils.spring.PropertyPlaceholder;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

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
    private static final String IP_CACHE_DIR =PropertyPlaceholder.getProperty("ipTable.file").toString();

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
       if(ips!=null){
           for(int i=0;i<ips.length;i++){
               ipSet.add(ips[i]);
           }
       }
    }

    /**
     *读取缓存文件
     * @param dir
     * @return
     */
    private String [] readCacheFile(String dir) throws IOException {
        String line="";
        String[] ips;
        InputStreamReader inputStreamReader  = null;
        BufferedReader bufferedReader = null;
        try {
            inputStreamReader = new InputStreamReader(new FileInputStream(new File(dir)));
            bufferedReader = new BufferedReader(inputStreamReader);
            line = bufferedReader.readLine();
            if(line==null) ips=null;
            else ips= line.split(";");
            return ips;
        }catch (IOException e){
            throw e;
        }finally {
            bufferedReader.close();
            inputStreamReader.close();
        }
    }

    /**
     * 返回ipSet
     */
     public HashSet<String> getIPSet(){
        return ipSet;
     }

    /**
     * 更新缓存文件ip列表
     */
    public void updateIpInCacheFile(File file,String ip) throws IOException{
        BufferedWriter out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        out.write(ip);
        out.close();
    }

    /**
     * 添加新的ip到黑名单，并更新黑名单缓存
     * @param ip
     */
    public boolean createBlankIP(String ip) throws IOException{
        ip=ip.trim();
        try {
            if(!ipSet.contains(ip)){
                File file =new File(IP_CACHE_DIR);
                if (file.isFile() && file.exists())
                // 判断文件是否存在
                {
                    InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line=bufferedReader.readLine();
                    if(line!=null) line+=";" + ip;
                    else line=ip;
                    bufferedReader.close();
                    inputStreamReader.close();
                    updateIpInCacheFile(file,line);
                    ipSet.add(ip);
                    return true;
                }else {
                    return false;
                }
            }else {
                return false;
            }
        }catch (IOException e)
        {
            throw e;
        }
    }

    /**
     *将ip从黑名单移除
     */
    public boolean deleteIP(String ip) throws IOException{
        ip=ip.trim();
        try{
            if(ipSet.contains(ip)){
                ipSet.remove(ip);
                updateIpInCacheFile(new File(IP_CACHE_DIR),stringSet());
                return true;
            }else {
                return false;
            }
        }catch(IOException e){
            throw e;
        }

    }

    /**
     *连接set
     */
    private String stringSet(){
        if(ipSet.isEmpty()){
            return "";
        }else{
            String ips="";
            for(String ip:ipSet){
                ips+=ip+";";
            }
            return ips.substring(0,ips.length()-1);
        }
    }
}
