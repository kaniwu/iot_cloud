package com.iot.nero.api_gateway.core.firewall;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.iot.nero.api_gateway.core.CONSTANT;
import com.iot.nero.api_gateway.core.exceptions.AuthFailedException;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/8/29
 * Time   下午6:57
 */
public class AdminAuth {


    public void auth(String params) throws ParseException, AuthFailedException {

        //加载配置文件中用户名和密码
        Admin admin = new Admin();
        Admin ad = JSON.parse(params,Admin.class);
        //认证
        if(!admin.getUserName().equals(ad.getUserName())){
            throw new AuthFailedException(CONSTANT.ADMIN_NOT_EXISTS);
        }
        if(!admin.getPassWord().equals(ad.getPassWord())){
            throw new AuthFailedException(CONSTANT.ADMIN_PASSWORD_INCORRECT);
        }
    }
}
