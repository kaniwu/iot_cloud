package com.iot.nero.facade_log;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        new ClassPathXmlApplicationContext(new String[]{"facade_log/dubbo/facade.xml","facade_log/spring/spring-dao.xml","facade_log/spring/spring-service.xml"});

        LogConsumer logConsumer  = new LogConsumer();
        logConsumer.listen();

        while (true) {
        }
    }
}
