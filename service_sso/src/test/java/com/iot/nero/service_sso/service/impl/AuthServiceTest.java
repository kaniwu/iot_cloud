package com.iot.nero.service_sso.service.impl;


import com.iot.nero.entity.SysConfig;
import com.iot.nero.service.IAuthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/6/27
 * Time   下午5:30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:service_sso/dubbo/*.xml")
public class AuthServiceTest {


    @Autowired
    private IAuthService iAuthService;

    @Test
    public void login() throws Exception {
        System.out.println(iAuthService.login("nerosoft@outlook.com","baby..520587"));
    }
    @Test
    public void auth() throws Exception {
        System.out.println(iAuthService.auth(1,iAuthService.login("nerosoft@outlook.com","baby..520587").getData().getToken()));
    }
}