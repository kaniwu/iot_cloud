package com.iot.nero.api_gateway.core.core;

import com.alibaba.dubbo.common.json.ParseException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.iot.nero.api_gateway.common.Debug;
import com.iot.nero.api_gateway.common.UtilJson;
import com.iot.nero.api_gateway.core.config.Config;
import com.iot.nero.api_gateway.core.doc.ApiDoc;
import com.iot.nero.api_gateway.core.exceptions.ApiException;
import com.iot.nero.api_gateway.core.exceptions.AuthFailedException;
import com.iot.nero.api_gateway.core.firewall.Admin;
import com.iot.nero.api_gateway.core.firewall.AdminAuth;
import com.iot.nero.api_gateway.core.firewall.IpTables;
import com.iot.nero.api_gateway.core.log.ApiLog;
import com.iot.nero.utils.spring.PropertyPlaceholder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/8/25
 * Time   下午12:41
 */
public class ApiGatewayHandler implements InitializingBean, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ApiGatewayHandler.class);

    private static final String METHOD = "method";
    private static final String PARAMS = "params";

    private ApiDoc apiDoc;
    private ApiLog apiLog;
    private IpTables ipTables;
    private AdminAuth adminAuth;

    ApiStore apiStore;
    final ParameterNameDiscoverer parameterNameDiscoverer;

    public ApiGatewayHandler() {
        parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
<<<<<<< HEAD
        apiDoc  = new ApiDoc();
        apiLog  = new ApiLog();
=======
        apiDoc = new ApiDoc();
        apiLog = new ApiLog();
>>>>>>> gtBailly-master
        ipTables = new IpTables();
        adminAuth = new AdminAuth();
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        apiStore = new ApiStore(applicationContext, parameterNameDiscoverer);
    }

    public void afterPropertiesSet() throws Exception {
        apiStore.loadApiFromSpringBeans();
    }

    public void handle(HttpServletRequest request, HttpServletResponse response) {

        //apiLog.log(request);
<<<<<<< HEAD
        ipTables.filter(request,response);
=======
        ipTables.filter(request, response);
>>>>>>> gtBailly-master

        String method = request.getParameter(METHOD);
        String params = request.getParameter(PARAMS);

        Object result;
        ApiStore.ApiRunnable apiRunnable = null;
<<<<<<< HEAD
        if (method.subSequence(0,3).equals("sys")) {
            Config config= new Config();
            Debug.debug(config.toString(),response);
            //adminAuth.auth(params);
            if(method.equals("sys.doc")){
                apiDoc.genHtml(apiStore.findApiRunnables(),response);
            }
        } else {
            try {
=======
        try {
            if (method.subSequence(0, 3).equals("sys")) {
                Admin admin = new Admin();
                adminAuth.auth(params);
                Debug.debug(admin, response);
                if (method.equals("sys.doc")) {
                    result = apiStore.findApiRunnables();
                }else{
                    result = null;
                }
            } else {
>>>>>>> gtBailly-master
                apiRunnable = sysParamsValdate(request);
                Object[] args = buildParams(apiRunnable, params, request, response);
                result = apiRunnable.run(args);
            }
        } catch (ApiException e) {
            response.setStatus(500);
            result = handleErr(e);
        } catch (IllegalAccessException e) {
            response.setStatus(500);
            result = handleErr(e);
        } catch (InvocationTargetException e) {
            response.setStatus(500);
            result = handleErr(e.getTargetException());
        } catch (ParseException e) {
            response.setStatus(500);
            result = handleErr(e);
        } catch (AuthFailedException e) {
            response.setStatus(500);
            result = handleErr(e);
        }
        returnResult(result, response);

}

    private Object handleErr(Throwable e) {
        String code = "";
        String message = "";
        if (e instanceof ApiException) {
            code = "0001";
            message = e.getMessage();
        } else {
            code = "0002";
            message = e.getMessage();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("error", code);
        result.put("msg", message);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(out);
        e.printStackTrace(stream);
        result.put("track", e.getStackTrace());

        return result;
    }


    private ApiStore.ApiRunnable sysParamsValdate(HttpServletRequest request) throws ApiException {
        String apiName = request.getParameter(METHOD);
        String json = request.getParameter(PARAMS);

        ApiStore.ApiRunnable apiRunnable;

        if (apiName == null || apiName.equals("")) {
            throw new ApiException("调用失败，参数 'method' 为空");
        } else if (json == null) {
            throw new ApiException("调用失败，参数 'params' 为空");
        } else if ((apiRunnable = apiStore.findApiRunnable(apiName)) == null) {
            throw new ApiException("调用失败：指定API不存在，API：" + apiName);
        }
        return apiRunnable;
    }


    private Object[] buildParams(ApiStore.ApiRunnable apiRunnable, String params, HttpServletRequest request, HttpServletResponse response) throws ApiException {
        Map<String, Object> map = null;
        try {
            map = UtilJson.toMap(params);

        } catch (IllegalArgumentException e) {
            throw new ApiException("调用失败：JSON字符串格式化失败，请检查params参数");
        } catch (ParseException e) {
            throw new ApiException("调用失败：JSON字符串toMap失败，请检查params参数");
        }
        logger.error("________________MAP : " + map.toString());
        if (map == null) {
            map = new HashMap<String, Object>();
        }
        Method method = apiRunnable.getTargetMethod();
        List<String> paramsNames = Arrays.asList(parameterNameDiscoverer.getParameterNames(method));
        logger.error("_______________NAMES : " + paramsNames.toString());
        Class<?>[] paramType = method.getParameterTypes();

        for (Map.Entry<String, Object> m : map.entrySet()) {
            if (!paramsNames.contains(m.getKey())) {
                throw new ApiException("调用失败，接口 '" + apiRunnable.getApiName() + "' 对应方法 '" + method.getName() + "' 不存在 '" + m.getKey() + "' 参数");
            }
        }

        Object[] args = new Object[paramType.length];
        for (int i = 0; i < paramType.length; i++) {
            if (paramType[i].isAssignableFrom(HttpServletRequest.class)) {
                args[i] = request;
            } else if (map.containsKey(paramsNames.get(i))) {
                try {
                    args[i] = convertJsonToBean(map.get(paramsNames.get(i)), paramType[i]);
                } catch (Exception e) {
                    throw new ApiException("调用失败：指定参数格式错误或值错误 '" + paramsNames.get(i) + "' :" + e.getMessage());
                }
            } else {
                args[i] = null;
            }
        }
        logger.error("__________ARGS : " + args.toString());
        return args;
    }

    private <T> Object convertJsonToBean(Object val, Class<?> targetClass) {
        Object result = null;
        if (val == null) {
            return null;
        } else if (Integer.class.equals(targetClass)) {
            result = Integer.parseInt(val.toString());
        } else if (Long.class.equals(targetClass)) {
            result = Long.parseLong(val.toString());
        } else if (Date.class.equals(targetClass)) {
            if (val.toString().matches("[0-9]+")) {
                result = new Date(Long.parseLong(val.toString()));
            } else {
                throw new IllegalArgumentException("日期必须是长整型的时间戳");
            }
        } else if (String.class.equals(targetClass)) {
            if (val instanceof String) {
                result = val;
            } else {
                throw new IllegalArgumentException("转换目标类型为字符串");
            }
        } else {
            result = UtilJson.convertValue(val, targetClass);
        }
        return result;
    }


    private void returnResult(Object result, HttpServletResponse response) {
        try {
            UtilJson.JSON_MAPPER.configure(
                    SerializationFeature.WRITE_NULL_MAP_VALUES, true);
            String json = UtilJson.writeValueAsString(result);

            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html/json;charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            if (json != null) {
                response.getWriter().write(json);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
