package com.iot.nero.facade_sso.observer;

import com.iot.nero.parent_sso.entity.Developer;
import com.iot.nero.utils.email.MailSenderFactory;
import com.iot.nero.utils.email.MailSenderType;
import com.iot.nero.utils.email.SimpleMailSender;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * author： nero
 * email: nerosoft@outlook.com
 * data: 16-10-4
 * time: 下午5:04.
 */
public class MailObserver implements Observer {
    public void update(Observable o, Object arg) {
        SimpleMailSender simpleMailSender = MailSenderFactory.getSender(MailSenderType.SERVICE);
        List<String> receivedUsers = new ArrayList<String>();
        Developer developer = null;
        if(arg instanceof Developer){
            developer = (Developer)arg;
        }

        receivedUsers.add(developer.getEmail());

        try {
            simpleMailSender.send(receivedUsers,"Hi "+developer.getName()+",Welcome to CenoCloud !\n",
                            "Thanks for joining us.\nPlease click the link below to active your account http://localhost:8080/"+developer.getToken()+".This link will lost after 30 minutes。\nIf this is not your operate,please ingore。\n");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
