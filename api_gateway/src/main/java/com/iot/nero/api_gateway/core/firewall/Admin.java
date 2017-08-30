package com.iot.nero.api_gateway.core.firewall;

import com.iot.nero.utils.spring.PropertyPlaceholder;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

import com.iot.nero.api_gateway.core.core.ApiGatewayHandler;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/8/30
 * Time   下午12:48
 */
<<<<<<< HEAD:api_gateway/src/main/java/com/iot/nero/api_gateway/core/config/Config.java
public class Config implements Serializable {
    private String userName;
=======
public class Admin implements Serializable {

    private String userName ;

>>>>>>> gtBailly-master:api_gateway/src/main/java/com/iot/nero/api_gateway/core/firewall/Admin.java
    private String passWord;
    public Config() {
        loadConfig();
    }

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

<<<<<<< HEAD:api_gateway/src/main/java/com/iot/nero/api_gateway/core/config/Config.java
    private void loadConfig(){
        File file = new File("D:\\XAMPP\\htdocs\\iot_cloud\\api_gateway\\src\\main\\resources\\api_gateway\\config\\AuthConfig.xml");
        this.readXMLFile(file);
    }


     private void readXMLFile(File file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            NodeList usernameList = doc.getElementsByTagName("username");
            NodeList passwordLisr = doc.getElementsByTagName("password");
                Node usernameNode = usernameList.item(0);
                Node passwordNode = passwordLisr.item(0);
                this.userName=usernameNode.getTextContent();
                this.passWord=passwordNode.getTextContent();
        }
            catch (Exception e) {
                e.printStackTrace();
            }
    }
=======
>>>>>>> gtBailly-master:api_gateway/src/main/java/com/iot/nero/api_gateway/core/firewall/Admin.java
}
