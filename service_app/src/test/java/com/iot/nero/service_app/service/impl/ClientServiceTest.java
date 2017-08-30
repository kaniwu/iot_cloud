package com.iot.nero.service_app.service.impl;

import com.iot.nero.service.IClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/7/11
 * Time   下午2:16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:service_app/dubbo/*.xml")
public class ClientServiceTest {



    @Autowired
    private IClientService clientService;



    @Test
    public void clientOffLine() throws Exception {
        System.out.println(clientService.clientOffLine("ZyQg4+dgiwDwGkG0GW2JCg==","virtual"));
    }

    @Test
    public void sendMessage() throws Exception {
            System.out.println(clientService.sendMessage("ZyQg4+dgiwDwGkG0GW2JCg==", "Hiw9Bo", "virtual", "1", "test message_"));
    }


    @Test
    public void getClientSendMessageCount() throws Exception {
        System.out.println(clientService.getClientSendMessageCount(1,"asdasd",17,"Hiw9Bo"));
    }

    @Test
    public void getClientSendMessage() throws Exception {
        System.out.println(clientService.getClientSendMessage(1,"asdasd",17,"Hiw9Bo",0,10));
    }

    @Test
    public void getClientReceivedMessage() throws Exception {
        System.out.println(clientService.getClientReceivedMessage(1,"asdasd",17,"virtual",0,10));
    }


}