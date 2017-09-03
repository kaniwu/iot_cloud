package com.iot.nero.facade_app.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/6/28
 * Time   下午3:19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:facade_app/spring/*.xml")
public class AppDaoTest {


    @Autowired
    private AppDao appDao;

    @Test
    public void findAppByUid() throws Exception {
        System.out.println(appDao.findAppByUid(2));
    }

    @Test
    public void findAppByKey() throws Exception {
        System.out.println(appDao.findAppByProductKey("12"));
    }

    @Test
    public void findAppById() throws Exception {
        System.out.println(appDao.findAppById(1));
    }

    @Test
    public void findKeySecretByKey() throws Exception {

    }

    @Test
    public void createApplication() throws Exception {

    }

}