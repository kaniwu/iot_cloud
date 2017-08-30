package com.iot.nero.web_sso.controller;

import com.iot.nero.dto.Result;
import com.iot.nero.parent_sso.constant.CONSTANT;
import com.iot.nero.parent_sso.dto.DeveloperInfo;
import com.iot.nero.parent_sso.entity.Developer;
import com.iot.nero.service.IAuthService;
import com.iot.nero.utils.verifycode.Verify;
import com.iot.nero.utils.verifycode.exception.VerifyFailedException;
import com.iot.nero.web_sso.Consumer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/7/24
 * Time   下午12:21
 */
@Controller
@RequestMapping("/register")
public class ApiRegister {

    private IAuthService authService;

    @RequestMapping(value = "/{name}/{email}/{password}/{code}/register",
            method = RequestMethod.GET)
    @ResponseBody
    public Result<DeveloperInfo> register(@PathVariable("name") String name,
                                            @PathVariable("email") String email,
                                          @PathVariable("password") String password,
                                          @PathVariable("code") String code,
                                          HttpServletRequest request,
                                          HttpServletResponse response) throws VerifyFailedException {

        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);

        Verify verify = new Verify(request);

        try {
            verify.checkVerify(code);
            authService = (IAuthService) Consumer.singleton().getBean("IAuthService");
            return authService.register(name,email, password);
        }catch(IllegalStateException e){
            return new Result<DeveloperInfo>(false, CONSTANT.SSO_SERVICE_EXCEPTION);
        }catch (VerifyFailedException e){
            return new Result<DeveloperInfo>(false, CONSTANT.VERIFY_CODE_INCORRECT);
        }
    }


    @RequestMapping(value = "/{id}/{token}/active",
            method = RequestMethod.GET)
    @ResponseBody
    public Result<DeveloperInfo> active(@PathVariable("id") Integer id,
                                            @PathVariable("token") String token){
        try {
            authService = (IAuthService) Consumer.singleton().getBean("IAuthService");
            return authService.active(id, token);
        }catch(IllegalStateException e){
            return new Result<DeveloperInfo>(false, CONSTANT.SSO_SERVICE_EXCEPTION);
        }
    }

}
