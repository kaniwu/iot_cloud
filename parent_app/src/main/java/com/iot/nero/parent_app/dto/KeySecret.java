package com.iot.nero.parent_app.dto;

import java.io.Serializable;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/6/28
 * Time   上午9:46
 */
public class KeySecret implements Serializable {
    private String Key;
    private String Secret;

    public KeySecret() {
    }

    public KeySecret(String key, String secret) {
        Key = key;
        Secret = secret;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getSecret() {
        return Secret;
    }

    public void setSecret(String secret) {
        Secret = secret;
    }

    @Override
    public String toString() {
        return "KeySecret{" +
                "Key='" + Key + '\'' +
                ", Secret='" + Secret + '\'' +
                '}';
    }
}
