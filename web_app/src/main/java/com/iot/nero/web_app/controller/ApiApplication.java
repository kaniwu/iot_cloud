package com.iot.nero.web_app.controller;

import com.iot.nero.dto.Result;
import com.iot.nero.parent_app.constant.CONSTANT;
import com.iot.nero.parent_app.dto.ApplicationResult;
import com.iot.nero.parent_app.entity.*;
import com.iot.nero.parent_data.entity.MQMessage;
import com.iot.nero.parent_log.dto.LList;
import com.iot.nero.parent_log.entity.ClientLog;
import com.iot.nero.service.IApplicationService;
import com.iot.nero.service.IClientService;
import com.iot.nero.web_app.Consumer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/6/29
 * Time   下午1:43
 */
@Controller
@RequestMapping("/app")
public class ApiApplication {

    private IApplicationService applicationService;
    private IClientService clientService;

    @RequestMapping(value = "/{uid}/{token}/auth/{name}/{type}/{tech}/{trans}/{company}/{maxConn}/{desc}/create",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result<Application> createApplication(@PathVariable("uid") Integer DId,
                                                 @PathVariable("token") String token,
                                                 @PathVariable("name") String name,
                                                 @PathVariable("type") String type,
                                                 @PathVariable("tech") String tech,
                                                 @PathVariable("trans") Integer trans,
                                                 @PathVariable("company") String company,
                                                 @PathVariable("maxConn") Integer maxConnect,
                                                 @PathVariable("desc") String desc){
        try{
            applicationService = (IApplicationService)Consumer.singleton().getBean("IApplicationService");
            return applicationService.createApplication(DId,token,name,type,tech,trans,company,maxConnect,desc);
        }catch (IllegalStateException e) {
            return new Result<Application>(false,CONSTANT.APP_SERVICE_OFFLINE);
        }
    }




    @RequestMapping(value = "/{uid}/{token}/auth/apps",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result<List<ApplicationResult>> getApplications(@PathVariable("uid") Integer Uid,
                                                           @PathVariable("token") String token){
        try{
            applicationService = (IApplicationService)Consumer.singleton().getBean("IApplicationService");
            return applicationService.getApplications(Uid,token);
        }catch (IllegalStateException e)
        {
            return new Result<List<ApplicationResult>>(false,CONSTANT.APP_SERVICE_OFFLINE);
        }
    }




    @RequestMapping(value = "/{uid}/{token}/auth/{pwd}/verify/{aid}/app",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result<Application> getApplication(@PathVariable("uid") Integer uid,
                                              @PathVariable("aid") Integer aid,
                                              @PathVariable("token") String token,
                                              @PathVariable("pwd") String pwd){
        try{
            applicationService = (IApplicationService)Consumer.singleton().getBean("IApplicationService");
            return applicationService.getApplication(uid,aid,token, pwd);
        }catch (IllegalStateException e)
        {
            return new Result<Application>(false,CONSTANT.APP_SERVICE_OFFLINE);
        }
    }




    @RequestMapping(value = "/{uid}/{token}/auth/{aid}/appinfo",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result<ApplicationResult> getApplicationInfo(@PathVariable("uid") Integer uid,
                                              @PathVariable("aid") Integer aid,
                                              @PathVariable("token") String token){
        try{
            applicationService = (IApplicationService)Consumer.singleton().getBean("IApplicationService");
            return applicationService.getApplicationInfo(uid,aid,token);
        }catch (IllegalStateException e)
        {
            return new Result<ApplicationResult>(false,CONSTANT.APP_SERVICE_OFFLINE);
        }
    }




    @RequestMapping(value = "/{uid}/{token}/auth/{aid}/dataPoint/get",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result<List<DataPoint>> getDataPoints(@PathVariable("uid") Integer uid,
                                                 @PathVariable("aid") Integer aid,
                                                 @PathVariable("token") String token){
        try{
            applicationService = (IApplicationService)Consumer.singleton().getBean("IApplicationService");
            return applicationService.getDataPoints(uid, aid, token);
        }catch (IllegalStateException e)
        {
            return new Result<List<DataPoint>>(false,CONSTANT.APP_SERVICE_OFFLINE);
        }
    }

    @RequestMapping(value = "/{uid}/{token}/auth/{aid}/{name}/{type}/{access}/dataPoint/add",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result<DataPoint> addDataPoints(@PathVariable("uid") Integer uid,
                                           @PathVariable("aid") Integer aid,
                                           @PathVariable("name") String name,
                                           @PathVariable("type") String type,
                                           @PathVariable("access") Integer access,
                                           @PathVariable("token") String token){
        try{
            applicationService = (IApplicationService)Consumer.singleton().getBean("IApplicationService");
            return applicationService.addDataPoints(uid, aid, new DataPointInfo(aid,name,type,access), token);
        }catch (IllegalStateException e)
        {
            return new Result<DataPoint>(false,CONSTANT.APP_SERVICE_OFFLINE);
        }
    }



    @RequestMapping(value = "/{uid}/{token}/auth/{aid}/{page}/{num}/connections",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result<List<ClientConnResult>> getCurrentConns(@PathVariable("uid") Integer uid,
                                                  @PathVariable("aid") Integer aid,
                                                  @PathVariable("page") Integer page,
                                                  @PathVariable("num") Integer num,
                                                  @PathVariable("token") String token){
        try{
            applicationService  = (IApplicationService)Consumer.singleton().getBean("IApplicationService");
            return applicationService.getAppCurrentConn(aid,uid,token,page,num);
        }catch (IllegalStateException e) {
            return new Result<List<ClientConnResult>>(false,CONSTANT.APP_SERVICE_OFFLINE);
        }
    }



    @RequestMapping(value = "/{uid}/{token}/auth/{aid}/{pid}/dataPoint/delete",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result<DataPoint> deleteDataPoint(@PathVariable("uid") Integer uid,
                                             @PathVariable("token") String token,
                                             @PathVariable("aid") Integer aid,
                                             @PathVariable("pid") Integer pid){
        try{
            applicationService  = (IApplicationService)Consumer.singleton().getBean("IApplicationService");
            return applicationService.deleteDataPoint(uid,token,aid,pid);
        }catch (IllegalStateException e) {
            return new Result<DataPoint>(false,CONSTANT.APP_SERVICE_OFFLINE);
        }
    }



    @RequestMapping(value = "/{uid}/{token}/auth/{aid}/{page}/{num}/message/get",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result<List<MQMessage>> getMessage(@PathVariable("uid") Integer uid,
                                              @PathVariable("token")String token,
                                              @PathVariable("aid") Integer aid,
                                              @PathVariable("page") Integer page,
                                              @PathVariable("num") Integer num){
        try{
            applicationService  = (IApplicationService)Consumer.singleton().getBean("IApplicationService");
            return applicationService.getMessage(uid,token,aid,page,num);
        }catch (IllegalStateException e) {
            return new Result<List<MQMessage>>(false,CONSTANT.APP_SERVICE_OFFLINE);
        }
    }



    @RequestMapping(value = "/{uid}/{token}/auth/{aid}/{clientId}/{page}/{num}/message/send",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result<List<MQMessage>> getClientSendMessage(@PathVariable("uid") Integer uid,
                                                        @PathVariable("token")String token,
                                                        @PathVariable("aid") Integer aid,
                                                        @PathVariable("clientId") String clientId,
                                                        @PathVariable("page") Integer page,
                                                        @PathVariable("num") Integer num){
        try{
            clientService  = (IClientService) Consumer.singleton().getBean("IClientService");
            return clientService.getClientSendMessage(uid,token,aid,clientId,page,num);
        }catch (IllegalStateException e) {
            return new Result<List<MQMessage>>(false,CONSTANT.APP_SERVICE_OFFLINE);
        }
    }



    @RequestMapping(value = "/{uid}/{token}/auth/{aid}/{clientId}/{page}/{num}/message/received",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result<List<MQMessage>> getClientReceivedMessage(@PathVariable("uid") Integer uid,
                                                            @PathVariable("token")String token,
                                                            @PathVariable("aid") Integer aid,
                                                            @PathVariable("clientId") String clientId,
                                                            @PathVariable("page") Integer page,
                                                            @PathVariable("num") Integer num){
        try{
            clientService  = (IClientService)Consumer.singleton().getBean("IClientService");
            return clientService.getClientReceivedMessage(uid,token,aid,clientId,page,num);
        }catch (IllegalStateException e) {
            return new Result<List<MQMessage>>(false,CONSTANT.APP_SERVICE_OFFLINE);
        }
    }



    @RequestMapping(value = "/{uid}/{token}/auth/{aid}/{clientId}/message/send/count",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result<Integer> getClientSendMessageCount(@PathVariable("uid") Integer uid,
                                                        @PathVariable("token")String token,
                                                        @PathVariable("aid") Integer aid,
                                                        @PathVariable("clientId") String clientId){
        try{
            clientService  = (IClientService) Consumer.singleton().getBean("IClientService");
            return clientService.getClientSendMessageCount(uid,token,aid,clientId);
        }catch (IllegalStateException e) {
            return new Result<Integer>(false,CONSTANT.APP_SERVICE_OFFLINE);
        }
    }


    /**
     * 设备日志列表
     * @param uid
     * @param token
     * @param aid
     * @param type 设备日志类型
     * @param level 设备日志级别
     * @param page
     * @param num
     * @return
     */
    @RequestMapping(value = "/{uid}/{token}/auth/{aid}/app/{type}/{level}/log/{page}/{num}/get",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"}
    )
    @ResponseBody
    public Result<LList<List<ClientLog>>> getLogList(@PathVariable("uid")Integer uid,
                                                     @PathVariable("token") String token,
                                                     @PathVariable("aid") Integer aid,
                                                     @PathVariable("type") Integer type,
                                                     @PathVariable("level") Integer level,
                                                     @PathVariable("page") Integer page,
                                                     @PathVariable("num") Integer num){
        try{
            clientService  = (IClientService) Consumer.singleton().getBean("IClientService");
            return clientService.getClientLog(uid,token,aid,type,level,page,num);
        }catch (IllegalStateException e) {
            return new Result<LList<List<ClientLog>>>(false,CONSTANT.APP_SERVICE_OFFLINE);
        }
    }


}
