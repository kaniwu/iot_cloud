package com.iot.nero.api_gateway.core.firewall;

import com.iot.nero.api_gateway.common.Debug;
import com.iot.nero.api_gateway.common.NetUtil;
import com.iot.nero.api_gateway.core.core.ApiStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/8/29
 * Time   下午6:52
 */
public class IpTables {

    public void filter(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String ip = NetUtil.getRealIP(request);

            Debug.debug(ip,response);

            //查黑名单缓存

            //有，拒绝

            //没有，查数据库

            //有，拒绝并加入缓存

            //没有，过

    }
}
