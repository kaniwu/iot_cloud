package com.iot.nero.facade_app.facade.impl;

import com.iot.nero.facade.IApplicationFacade;
import com.iot.nero.parent_app.dto.ApplicationInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/6/28
 * Time   下午4:41
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:facade_app/spring/*.xml")
public class ApplicationFacadeTest {


    @Autowired
    private IApplicationFacade applicationFacade;

    @Test
    public void createApplication() throws Exception {
        System.out.println(applicationFacade.createApplication(new ApplicationInfo(2,
                "demo",
                "smart light",
                "Wi-Fi",
                1,
                "NodeX",
                8000,
                "this is desc ")));
    }

    @Test
    public void getDataPoint() throws Exception {
        applicationFacade.getDataPoint(13);
    }

}