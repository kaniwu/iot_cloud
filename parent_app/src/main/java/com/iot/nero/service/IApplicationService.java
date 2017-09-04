package com.iot.nero.service;

import com.iot.nero.dto.Result;
import com.iot.nero.parent_app.dto.ApplicationResult;
import com.iot.nero.parent_app.entity.*;
import com.iot.nero.parent_data.entity.MQMessage;

import java.util.List;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/6/28
 * Time   上午11:15
 */
public interface IApplicationService {
    /**
     * 创建应用
     * @param DId
     * @param name
     * @param type
     * @param tech
     * @param trans
     * @param company
     * @param maxConnect
     * @param desc
     * @return
     */
    Result<Application> createApplication(Integer DId, String token, String name, String type, String tech, Integer trans, String company, Integer maxConnect, String desc);


    /**
     * 获取应用程序
     * @param Uid
     * @param token
     * @return
     */
    Result<List<ApplicationResult>> getApplications(Integer Uid, String token);

    /**
     * 获取真实应用程序 带 secret
     * @param uid
     * @param aid
     * @param token
     * @param pwd
     * @return
     */
    Result<Application> getApplication(Integer uid,Integer aid,String token, String pwd);

    /**
     * 获取数据点
     * @param aid
     * @param token
     * @return
     */
    Result<List<DataPoint>> getDataPoints(Integer uid,Integer aid, String token);

    /**
     * 添加数据点
     * @param aid
     * @param token
     * @return
     */
    Result<DataPoint> addDataPoints(Integer uid,Integer aid, DataPointInfo data, String token);


    /**
     * 获取应用信息，无productSecret
     * @param uid
     * @param aid
     * @param token
     * @return
     */
    Result<ApplicationResult> getApplicationInfo(Integer uid, Integer aid, String token);


    /**
     * 获取应用当前连接信息
     * @param aid
     * @param did
     * @param token
     * @return
     */
    Result<List<ClientConnResult>> getAppCurrentConn(Integer aid,Integer did,String token,Integer page,Integer num);

    /**
     * 删除数据点
     * @param uid
     * @param token
     * @param aid
     * @param pid
     * @return
     */
    Result<DataPoint> deleteDataPoint(Integer uid, String token, Integer aid, Integer pid);

    /**
     * 获取消息记录
     * @param uid
     * @param token
     * @param aid
     * @param page
     * @param num
     * @return
     */
    Result<List<MQMessage>> getMessage(Integer uid, String token, Integer aid, Integer page, Integer num);
}
