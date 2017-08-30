package com.iot.nero.api_gateway.core.firewall;

import com.iot.nero.utils.spring.PropertyPlaceholder;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/8/30
 * Time   下午12:48
 */
public class Admin implements Serializable {

    private String userName ;

    private String passWord;

    public Admin() {
        this.userName = PropertyPlaceholder.getProperty("auth.username").toString();
        this.passWord = PropertyPlaceholder.getProperty("auth.password").toString();
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    @Override
    public String toString() {
        return "Config{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }

}
