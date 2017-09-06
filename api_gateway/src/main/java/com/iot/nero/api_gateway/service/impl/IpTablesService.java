package com.iot.nero.api_gateway.service.impl;
import com.iot.nero.api_gateway.common.ConfigUtil;
import com.iot.nero.api_gateway.core.core.ApiGatewayHandler;
import com.iot.nero.api_gateway.core.core.ApiMapping;
import com.iot.nero.api_gateway.core.firewall.IpCache;
import com.iot.nero.api_gateway.service.IIpTablesService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/9/5
 * Time   下午2:56
 */
public class IpTablesService implements IIpTablesService {
    Map<String, String> configMap;
    @ApiMapping("sys.ipTables.set")
<<<<<<< HEAD
    public boolean setIpTableStatus(String isOpen) throws IOException {
        configMap = ConfigUtil.configToMap();
        configMap.replace("ipTable.isOpen ", configMap.get("ipTable.isOpen "),isOpen);
        ApiGatewayHandler.setIpTableOpen(isOpen);
        return ConfigUtil.mapToConfig(configMap);
=======
    public boolean setIpTableStatus(String isOpen) {
        isOpen = isOpen.trim();
        webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        servletContext = webApplicationContext.getServletContext();

        String path =servletContext.getRealPath("/WEB-INF/classes/api_gateway/config/config.properties");

        File file = new File(path);
        Properties prop = new Properties();// 属性集合对象
        try {
            FileInputStream fis = new FileInputStream(file);
            prop.load(fis);// 将属性文件流装载到Properties对象中
            fis.close();// 关闭流
        } catch (IOException e) {
            return false;
        }
        System.out.println(prop.getProperty("ipTable.isOpen"));
        if (prop.getProperty("ipTable.isOpen").equals(isOpen)) {
            return true;
        } else {
            prop.setProperty("ipTable.isOpen", isOpen);
            // 文件输出流
            try {
                FileOutputStream fos = new FileOutputStream(file);
                // 将Properties集合保存到流中
                prop.store(fos, "firewall:" + isOpen);
                fos.close();// 关闭流
            } catch (IOException e) {
                return false;
            }
            return true;
        }
>>>>>>> iot_cloud/master
    }

    @ApiMapping("sys.ipTables.list")
    public List<String> getIP() throws IOException {
        IpCache ipCache = new IpCache();
        return new ArrayList<String>(ipCache.getIPSet());
    }

    @ApiMapping("sys.ipTables.add")
    public boolean addIP(String ip) throws IOException{
            IpCache ipCache = new IpCache();
            return ipCache.createBlankIP(ip.trim());
    }

    @ApiMapping("sys.ipTables.del")
    public boolean delIP(String ip) throws IOException {
            IpCache ipCache = new IpCache();
            return ipCache.deleteIP(ip.trim());
    }

    private boolean isIP(String addr)
    {
        if(addr.length() < 7 || addr.length() > 15 || "".equals(addr))
        {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(addr);
        boolean ipAddress = mat.find();
        return ipAddress;
    }
}
