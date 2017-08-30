package com.iot.nero.facade_data.facade.impl;


import com.iot.nero.facade.IDataFacade;
import com.iot.nero.facade_data.dao.DataDao;
import com.iot.nero.parent_data.entity.MQMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/7/11
 * Time   下午1:23
 */
@Service
public class DataFacade implements IDataFacade {

    @Autowired
    private DataDao dataDao;

    /**
     * 记录发送消息
     * @param aid
     * @param from
     * @param to
     * @param qos
     * @param message
     * @return
     */
    public Integer saveMessage(Integer aid, String from, String to, String qos, String message){

        String table = "data_p_"+String.valueOf(aid);
        try {
            return dataDao.saveMessage(table, aid, from, to, qos, message);
        }catch (BadSqlGrammarException e){
            dataDao.createTable(table);
            return dataDao.saveMessage(table, aid, from, to, qos, message);
        }
    }

    /**
     * 获取消息记录
     * @param aid
     * @param page
     * @param num
     * @return
     */
    public List<MQMessage> getMessageByPage(Integer aid, Integer page, Integer num) {
        String table = "data_p_"+String.valueOf(aid);
        try {
            return dataDao.getMessageByPage(table, aid, page*num, num);
        }catch (BadSqlGrammarException e){
            dataDao.createTable(table);
            return dataDao.getMessageByPage(table, aid, page*num, num);
        }
    }

    /**
     * 获取客户端发送消息
     * @param aid
     * @param clientId
     * @param page
     * @param num
     * @return
     */
    public List<MQMessage> getClientSendMessageByPage(Integer aid, String clientId, Integer page, Integer num) {
        String table = "data_p_"+String.valueOf(aid);
        try {
            return dataDao.getSendMessageByPage(table, aid, clientId,page*num, num);
        }catch (BadSqlGrammarException e){
            dataDao.createTable(table);
            return dataDao.getSendMessageByPage(table, aid, clientId,page*num, num);
        }
    }

    /**
     * 获取客户端接收消息
     * @param aid
     * @param clientId
     * @param page
     * @param num
     * @return
     */
    public List<MQMessage> getClientReceivedMessageByPage(Integer aid, String clientId, Integer page, Integer num) {
        String table = "data_p_"+String.valueOf(aid);
        try {
            return dataDao.getReceivedMessageByPage(table, aid, clientId,page*num, num);
        }catch (BadSqlGrammarException e){
            dataDao.createTable(table);
            return dataDao.getReceivedMessageByPage(table, aid, clientId,page*num, num);
        }
    }

    /**
     * 获去智能设备发送数据条数
     * @param aid
     * @param clientId
     * @return
     */
    public Integer getClientSendMessageCount(Integer aid, String clientId) {
        String table = "data_p_"+String.valueOf(aid);
        try {
            return dataDao.getSendMessageCount(table, aid, clientId);
        }catch (BadSqlGrammarException e){
            dataDao.createTable(table);
            return dataDao.getSendMessageCount(table, aid, clientId);
        }
    }

    /**
     * 获取产品总消息
     * @param uid
     * @param id
     * @return
     */
    public Integer getSendMessageCount(Integer uid, Integer id) {
        String table = "data_p_"+String.valueOf(id);
        try {
            return dataDao.getAppSendMessageCount(table, id);
        }catch (BadSqlGrammarException e){
            dataDao.createTable(table);
            return dataDao.getAppSendMessageCount(table, id);
        }
    }
}
