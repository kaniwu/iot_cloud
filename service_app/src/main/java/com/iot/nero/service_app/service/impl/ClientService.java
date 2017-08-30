package com.iot.nero.service_app.service.impl;

import com.iot.nero.dto.Result;
import com.iot.nero.facade.*;
import com.iot.nero.parent_app.entity.Application;
import com.iot.nero.parent_app.entity.ClientConn;
import com.iot.nero.parent_app.exception.*;
import com.iot.nero.parent_data.entity.MQMessage;
import com.iot.nero.parent_log.constant.CONSTANT;
import com.iot.nero.parent_log.dto.LList;
import com.iot.nero.parent_log.entity.ClientLog;
import com.iot.nero.parent_sso.exception.DeveloperNotExistsException;
import com.iot.nero.parent_sso.exception.TokenExpiredException;
import com.iot.nero.service.IClientService;
import com.iot.nero.service_app.Consumer;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.iot.nero.parent_app.constant.CONSTANT.CONN_NOT_IN_LIST;
import static com.iot.nero.parent_app.constant.CONSTANT.MESSAGE_SAVE_FAILED;
import static com.iot.nero.parent_log.constant.CONSTANT.*;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/7/4
 * Time   上午9:45
 */
@Service
public class ClientService implements IClientService {


    private IDeveloperFacade developerFacade;
    private IApplicationFacade applicationFacade;
    private IClientFacade clientFacade;
    private IDataFacade dataFacade;
    private ILogFacade logFacade;

    /**
     * 智能客户端连接认证
     *
     * @param clientId
     * @param productKey
     * @param productSecret
     * @return
     */
    public synchronized Result<ClientConn> clientAuth(String clientId, String productKey, String productSecret) {
        applicationFacade = null;
        clientFacade = null;
        logFacade = null;
        try {
            logFacade = (ILogFacade) Consumer.singleton().getBean("ILogFacade");
        } catch (IllegalStateException e) {
            return new Result<ClientConn>(false, com.iot.nero.parent_sso.constant.CONSTANT.LOG_FACADE_EXCEPTION);
        }

        try {
            applicationFacade = (IApplicationFacade) Consumer.singleton().getBean("IApplicationFacade");
            clientFacade = (IClientFacade) Consumer.singleton().getBean("IClientFacade");

            //1.认证
            Application application = applicationFacade.findAppByProductKey(productKey);
            if (application == null) {
                throw new AppNotExistsException(com.iot.nero.parent_app.constant.CONSTANT.APPLICATION_NOT_EXISTS);
            }
            if (!application.getProductSecret().equals(productSecret)) {
                throw new ProductSecretIncorrectException(com.iot.nero.parent_app.constant.CONSTANT.CLIENT_AUTH_FAILED);
            }
            //2.检测该程序是否在连接设备列表中
            String clientPoolTableName = "developer_" + application.getDId() + "_client";
            ClientConn clientConn = clientFacade.findClientByClientID(clientPoolTableName, clientId);
            //3.在则连接，并修改设备连接表状态
            if (clientConn != null) {
                if (clientFacade.onLine(clientPoolTableName, clientId) < 1) {
                    throw new ClientOnlineException(com.iot.nero.parent_app.constant.CONSTANT.CLIENT_ONLINE_EXCEPTION);
                }
            } else {
                //4.不在，检测该程序最大连接数是否已满
                Integer currentConnCount = clientFacade.getCurrentConnCount(clientPoolTableName, application.getId());
                if (currentConnCount >= application.getMaxConnect()) {
                    throw new AppMaxConnLimitException(com.iot.nero.parent_app.constant.CONSTANT.MAX_CONN_FILLED);
                }
                //5.够，登记，连接
                if (clientFacade.checkIn(application.getDId(), application.getId(), clientId) < 1) {
                    throw new ClientCheckInException(com.iot.nero.parent_app.constant.CONSTANT.CLIENT_CHECK_IN_EXCEPTION);
                }
            }
            logFacade.clientLog(productKey, LOG_CLIENT_ONLINE, LOG_INFO, CONSTANT.CLIENT + clientId + CONSTANT.CLIENT_ONLINE);
            return new Result<ClientConn>(true, clientConn);
        } catch (IllegalStateException e) {
            logFacade.clientLog(productKey, LOG_CLIENT_AUTH, LOG_ERROR, CONSTANT.CLIENT + clientId + e.getMessage());
            return new Result<ClientConn>(false, e.getMessage());
        } catch (ProductSecretIncorrectException e) {
            logFacade.clientLog(productKey, LOG_CLIENT_ONLINE, LOG_ERROR, CONSTANT.CLIENT + clientId + e.getMessage());
            return new Result<ClientConn>(false, e.getMessage());
        } catch (ClientOnlineException e) {
            logFacade.clientLog(productKey, LOG_CLIENT_ONLINE, LOG_ERROR, CONSTANT.CLIENT + clientId + e.getMessage());
            return new Result<ClientConn>(false, e.getMessage());
        } catch (AppMaxConnLimitException e) {
            logFacade.clientLog(productKey, LOG_CLIENT_ONLINE, LOG_WARNING, CONSTANT.CLIENT + clientId + e.getMessage());
            return new Result<ClientConn>(false, e.getMessage());
        } catch (ClientCheckInException e) {
            logFacade.clientLog(productKey, LOG_CLIENT_ONLINE, LOG_ERROR, CONSTANT.CLIENT + clientId + e.getMessage());
            return new Result<ClientConn>(false, e.getMessage());
        } catch (AppNotExistsException e) {
            logFacade.clientLog(productKey, LOG_CLIENT_AUTH, LOG_WARNING, CONSTANT.CLIENT + clientId + e.getMessage());
            return new Result<ClientConn>(false, e.getMessage());
        }
    }

    /**
     * 客户端离线
     *
     * @param productKey
     * @param clientId
     * @return
     */
    public Result<ClientConn> clientOffLine(String productKey, String clientId) {
        applicationFacade = null;
        clientFacade = null;
        logFacade = null;

        try {
            logFacade = (ILogFacade) Consumer.singleton().getBean("ILogFacade");
        } catch (IllegalStateException e) {
            return new Result<ClientConn>(false, com.iot.nero.parent_sso.constant.CONSTANT.LOG_FACADE_EXCEPTION);
        }

        try {
            applicationFacade = (IApplicationFacade) Consumer.singleton().getBean("IApplicationFacade");
            clientFacade = (IClientFacade) Consumer.singleton().getBean("IClientFacade");

            //1.认证
            Application application = applicationFacade.findAppByProductKey(productKey);
            if (application == null) {
                throw new AppNotExistsException(com.iot.nero.parent_app.constant.CONSTANT.APPLICATION_NOT_EXISTS);
            }

            //2.检测该程序是否在连接设备列表中
            String clientPoolTableName = "developer_" + application.getDId() + "_client";
            ClientConn clientConn = clientFacade.findClientByClientID(clientPoolTableName, clientId);

            //3.在,修改设备连接表状态
            if (clientConn != null) {
                if (clientFacade.offLine(clientPoolTableName, clientId) < 1) {
                    throw new ClientOnlineException(com.iot.nero.parent_app.constant.CONSTANT.CLIENT_ONLINE_EXCEPTION);
                }
            }
            logFacade.clientLog(productKey, LOG_CLIENT_OFFLINE, LOG_INFO, CONSTANT.CLIENT + clientId + CONSTANT.CLIENT_OFFLINE_SUCCESS);
            return new Result<ClientConn>(true, clientConn);
        } catch (IllegalStateException e) {
            logFacade.clientLog(productKey, LOG_CLIENT_OFFLINE, LOG_ERROR, CONSTANT.CLIENT + clientId + e.getMessage());
            return new Result<ClientConn>(false, e.getMessage());
        } catch (ClientOnlineException e) {
            logFacade.clientLog(productKey, LOG_CLIENT_OFFLINE, LOG_WARNING, CONSTANT.CLIENT + clientId + e.getMessage());
            return new Result<ClientConn>(false, e.getMessage());
        } catch (AppNotExistsException e) {
            logFacade.clientLog(productKey, LOG_CLIENT_OFFLINE, LOG_WARNING, CONSTANT.CLIENT + clientId + e.getMessage());
            return new Result<ClientConn>(false, e.getMessage());
        }
    }

    /**
     * 消息发送记录
     *
     * @param secretKey
     * @param from
     * @param to
     * @param qos
     * @param message
     */
    public synchronized Result<MQMessage> sendMessage(String secretKey, String from, String to, String qos, String message) {
        applicationFacade = null;
        clientFacade = null;
        dataFacade = null;

        try {
            logFacade = (ILogFacade) Consumer.singleton().getBean("ILogFacade");
        } catch (IllegalStateException e) {
            return new Result<MQMessage>(false, com.iot.nero.parent_sso.constant.CONSTANT.LOG_FACADE_EXCEPTION);
        }
        try {

            applicationFacade = (IApplicationFacade) Consumer.singleton().getBean("IApplicationFacade");
            //1.认证
            Application application = applicationFacade.findAppByProductKey(secretKey);
            if (application == null) {
                throw new AppNotExistsException(com.iot.nero.parent_app.constant.CONSTANT.APPLICATION_NOT_EXISTS);
            }

            //2.检测该程序是否在连接设备列表中
            clientFacade = (IClientFacade) Consumer.singleton().getBean("IClientFacade");

            String clientPoolTableName = "developer_" + application.getDId() + "_client";
            ClientConn clientConn = clientFacade.findClientByClientID(clientPoolTableName, from);
            if (clientConn == null) {
                throw new ClientNotInConnListException(CONN_NOT_IN_LIST);
            }

            //储存消息数据
            dataFacade = (IDataFacade) Consumer.singleton().getBean("IDataFacade");

            if (dataFacade.saveMessage(application.getId(), from, to, qos, message) < 1) {
                throw new MessageSaveException(MESSAGE_SAVE_FAILED);
            }
            logFacade.clientLog(secretKey, LOG_CLIENT_MESSAGE, LOG_INFO, CONSTANT.CLIENT + from + CONSTANT.MESSAGE_SEND_SUCCESS);
            return new Result<MQMessage>(true, new MQMessage(1, application.getId(), from, to, qos, message, ""));/////////
        } catch (IllegalStateException e) {
            logFacade.clientLog(secretKey, LOG_CLIENT_MESSAGE, LOG_ERROR, CONSTANT.CLIENT + from + e.getMessage());
            return new Result<MQMessage>(false, e.getMessage());
        } catch (AppNotExistsException e) {
            logFacade.clientLog(secretKey, LOG_CLIENT_MESSAGE, LOG_ERROR, CONSTANT.CLIENT + from + e.getMessage());
            return new Result<MQMessage>(false, e.getMessage());
        } catch (ClientNotInConnListException e) {
            logFacade.clientLog(secretKey, LOG_CLIENT_MESSAGE, LOG_ERROR, CONSTANT.CLIENT + from + e.getMessage());
            return new Result<MQMessage>(false, e.getMessage());
        } catch (MessageSaveException e) {
            logFacade.clientLog(secretKey, LOG_CLIENT_MESSAGE, LOG_WARNING, CONSTANT.CLIENT + from + e.getMessage());
            return new Result<MQMessage>(false, e.getMessage());
        }
    }

    /**
     * 获取智能客户端发送消息
     *
     * @param uid
     * @param token
     * @param aid
     * @param clientId
     * @param page
     * @param num
     * @return
     */
    public Result<List<MQMessage>> getClientSendMessage(Integer uid, String token, Integer aid, String clientId, Integer page, Integer num) {
        applicationFacade = null;
        developerFacade = null;
        dataFacade = null;


        try {

            try {
                developerFacade = (IDeveloperFacade) Consumer.singleton().getBean("IDeveloperFacade");
            } catch (IllegalStateException e) {
                return new Result<List<MQMessage>>(false, com.iot.nero.parent_sso.constant.CONSTANT.SSO_FACADE_EXCEPTION);
            }
            boolean auth = developerFacade.isTokenCorrect(uid, token);

            try {
                applicationFacade = (IApplicationFacade) Consumer.singleton().getBean("IApplicationFacade");
            } catch (IllegalStateException e) {
                return new Result<List<MQMessage>>(false, com.iot.nero.parent_sso.constant.CONSTANT.APP_FACADE_EXCEPTION);
            }
            Application application = applicationFacade.authApplication(aid, uid);

            try {
                dataFacade = (IDataFacade) Consumer.singleton().getBean("IDataFacade");
            } catch (IllegalStateException e) {
                return new Result<List<MQMessage>>(false, com.iot.nero.parent_sso.constant.CONSTANT.DATA_FACADE_EXCEPTION);
            }
            List<MQMessage> messageResult = dataFacade.getClientSendMessageByPage(aid, clientId, page, num);

            return new Result<List<MQMessage>>(true, messageResult);
        } catch (AccessWithoutPermissionException e) {
            return new Result<List<MQMessage>>(false, e.getMessage());
        } catch (DeveloperNotExistsException e) {
            return new Result<List<MQMessage>>(false, e.getMessage());
        } catch (AppNotExistsException e) {
            return new Result<List<MQMessage>>(false, e.getMessage());
        } catch (TokenExpiredException e) {
            return new Result<List<MQMessage>>(false, e.getMessage());
        }
    }

    /**
     * 获取客户端接收消息
     *
     * @param uid
     * @param token
     * @param aid
     * @param clientId
     * @param page
     * @param num
     * @return
     */
    public Result<List<MQMessage>> getClientReceivedMessage(Integer uid, String token, Integer aid, String clientId, Integer page, Integer num) {
        applicationFacade = null;
        developerFacade = null;
        dataFacade = null;
        try {

            try {
                developerFacade = (IDeveloperFacade) Consumer.singleton().getBean("IDeveloperFacade");
            } catch (IllegalStateException e) {
                return new Result<List<MQMessage>>(false, com.iot.nero.parent_sso.constant.CONSTANT.SSO_FACADE_EXCEPTION);
            }
            boolean auth = developerFacade.isTokenCorrect(uid, token);

            try {
                applicationFacade = (IApplicationFacade) Consumer.singleton().getBean("IApplicationFacade");
            } catch (IllegalStateException e) {
                return new Result<List<MQMessage>>(false, com.iot.nero.parent_sso.constant.CONSTANT.APP_FACADE_EXCEPTION);
            }
            Application application = applicationFacade.authApplication(aid, uid);

            try {
                dataFacade = (IDataFacade) Consumer.singleton().getBean("IDataFacade");
            } catch (IllegalStateException e) {
                return new Result<List<MQMessage>>(false, com.iot.nero.parent_sso.constant.CONSTANT.DATA_FACADE_EXCEPTION);
            }
            List<MQMessage> messageResult = dataFacade.getClientReceivedMessageByPage(aid, clientId, page, num);

            return new Result<List<MQMessage>>(true, messageResult);
        } catch (AccessWithoutPermissionException e) {
            return new Result<List<MQMessage>>(false, e.getMessage());
        } catch (DeveloperNotExistsException e) {
            return new Result<List<MQMessage>>(false, e.getMessage());
        } catch (AppNotExistsException e) {
            return new Result<List<MQMessage>>(false, e.getMessage());
        } catch (TokenExpiredException e) {
            return new Result<List<MQMessage>>(false, e.getMessage());
        }
    }

    public Result<Integer> getClientSendMessageCount(Integer uid, String token, Integer aid, String clientId) {
        applicationFacade = null;
        developerFacade = null;
        dataFacade = null;
        try {

            try {
                developerFacade = (IDeveloperFacade) Consumer.singleton().getBean("IDeveloperFacade");
            } catch (IllegalStateException e) {
                return new Result<Integer>(false, com.iot.nero.parent_sso.constant.CONSTANT.SSO_FACADE_EXCEPTION);
            }
            boolean auth = developerFacade.isTokenCorrect(uid, token);

            try {
                applicationFacade = (IApplicationFacade) Consumer.singleton().getBean("IApplicationFacade");
            } catch (IllegalStateException e) {
                return new Result<Integer>(false, com.iot.nero.parent_sso.constant.CONSTANT.APP_FACADE_EXCEPTION);
            }
            Application application = applicationFacade.authApplication(aid, uid);

            try {
                dataFacade = (IDataFacade) Consumer.singleton().getBean("IDataFacade");
            } catch (IllegalStateException e) {
                return new Result<Integer>(false, com.iot.nero.parent_sso.constant.CONSTANT.DATA_FACADE_EXCEPTION);
            }
            Integer messageCount = dataFacade.getClientSendMessageCount(aid, clientId);

            return new Result<Integer>(true, messageCount);
        } catch (AccessWithoutPermissionException e) {
            return new Result<Integer>(false, e.getMessage());
        } catch (DeveloperNotExistsException e) {
            return new Result<Integer>(false, e.getMessage());
        } catch (AppNotExistsException e) {
            return new Result<Integer>(false, e.getMessage());
        } catch (TokenExpiredException e) {
            return new Result<Integer>(false, e.getMessage());
        }
    }

    /**
     * 设备上西线，数据发送，认证等日志获取
     *
     * @param uid
     * @param token
     * @param aid
     * @param type
     * @param level
     * @param page
     * @param num
     * @return
     */
    public Result<LList<List<ClientLog>>> getClientLog(Integer uid, String token, Integer aid, Integer type, Integer level, Integer page, Integer num) {
        applicationFacade = null;
        developerFacade = null;
        logFacade = null;
        try {
            logFacade = (ILogFacade) Consumer.singleton().getBean("ILogFacade");
        } catch (IllegalStateException e) {
            return new Result<LList<List<ClientLog>>>(false, com.iot.nero.parent_sso.constant.CONSTANT.LOG_FACADE_EXCEPTION);
        }

        try {

            try {
                developerFacade = (IDeveloperFacade) Consumer.singleton().getBean("IDeveloperFacade");
            } catch (IllegalStateException e) {
                return new Result<LList<List<ClientLog>>>(false, com.iot.nero.parent_sso.constant.CONSTANT.SSO_FACADE_EXCEPTION);
            }
            boolean auth = developerFacade.isTokenCorrect(uid, token);

            try {
                applicationFacade = (IApplicationFacade) Consumer.singleton().getBean("IApplicationFacade");
            } catch (IllegalStateException e) {
                return new Result<LList<List<ClientLog>>>(false, com.iot.nero.parent_sso.constant.CONSTANT.APP_FACADE_EXCEPTION);
            }
            Application application = applicationFacade.authApplication(aid, uid);
            return new Result<LList<List<ClientLog>>>(true, logFacade.getClientLog(application.getProductKey(), type, level, page, num));
        } catch (DeveloperNotExistsException e) {
            return new Result<LList<List<ClientLog>>>(false, e.getMessage());
        } catch (TokenExpiredException e) {
            return new Result<LList<List<ClientLog>>>(false, e.getMessage());
        } catch (AppNotExistsException e) {
            return new Result<LList<List<ClientLog>>>(false, e.getMessage());
        } catch (AccessWithoutPermissionException e) {
            return new Result<LList<List<ClientLog>>>(false, e.getMessage());
        }
    }
}
