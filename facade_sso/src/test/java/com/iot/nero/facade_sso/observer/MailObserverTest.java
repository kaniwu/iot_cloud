package com.iot.nero.facade_sso.observer;

import com.iot.nero.parent_sso.entity.Developer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/7/24
 * Time   上午10:05
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:facade_sso/spring/*.xml")
public class MailObserverTest {

    @Test
    public void update() throws Exception {
        Developer user = new Developer(1,
                "测试用户名",
                "测试公司",
                "nerosoft@outlook.com",
                "100861112",
                "",
                "",
                "",0);

        MailObserver mailObserver = new MailObserver();

        mailObserver.update(null, user);

    }
}