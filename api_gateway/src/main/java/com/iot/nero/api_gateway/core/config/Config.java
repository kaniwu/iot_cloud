package com.iot.nero.api_gateway.core.config;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/8/30
 * Time   下午12:48
 */
public class Config {
    private String userName;
    private String passWord;

    public Config(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "Config{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }

    public void loadConfig(){

    }
}
