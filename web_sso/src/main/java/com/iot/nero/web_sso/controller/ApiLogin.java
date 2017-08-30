package com.iot.nero.web_sso.controller;


import com.iot.nero.dto.Result;
import com.iot.nero.parent_balance.entity.Balance;
import com.iot.nero.parent_sso.constant.CONSTANT;
import com.iot.nero.parent_sso.dto.DeveloperInfo;
import com.iot.nero.parent_sso.entity.Developer;
import com.iot.nero.utils.verifycode.Verify;
import com.iot.nero.utils.verifycode.exception.VerifyFailedException;
import com.iot.nero.web_sso.Consumer;
import com.iot.nero.service.IAuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/6/26
 * Time   下午10:18
 */

@Controller
@RequestMapping("/login")
public class ApiLogin{


    private IAuthService authService;


    /**
     * 获取验证码
     */
    @RequestMapping(value = "/{random}/create",
            method = RequestMethod.GET)
    public void getVerify(@PathVariable("random") String random,
                          HttpServletRequest request,
                          HttpServletResponse response) throws IOException {

        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);

        //表明生成的响应是图片
        response.setContentType("image/png");
        Verify verify = new Verify(request);
        BufferedImage image = verify.createImage(60,200,80,true);
        ImageIO.write(image, "png", response.getOutputStream());
    }

    @RequestMapping(value = "/{code}/check",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result<Boolean> authCode(@PathVariable("code") String code,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws VerifyFailedException {

        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);
        Verify verify = new Verify(request);
        try{
            verify.checkVerify(code);
            return new Result<Boolean>(true,true);
        }catch (VerifyFailedException e){
            return new Result<Boolean>(false, CONSTANT.VERIFY_CODE_INCORRECT);
        }
    }

    @RequestMapping(value = "/{email}/{pwd}/auth/{code}/login",
            method = RequestMethod.GET,
    produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result<DeveloperInfo> login(@PathVariable("email") String email,
                                       @PathVariable("pwd") String pwd,
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
            return authService.login(email, pwd);
        }catch(IllegalStateException e){
            return new Result<DeveloperInfo>(false, CONSTANT.SSO_SERVICE_EXCEPTION);
        }catch (VerifyFailedException e){
            return new Result<DeveloperInfo>(false, CONSTANT.VERIFY_CODE_INCORRECT);
        }
    }


    @RequestMapping(value = "/{id}/{token}/auth",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result<DeveloperInfo> auth(@PathVariable("id") Integer id,
                                       @PathVariable("token") String token) {

        try {
            authService = (IAuthService) Consumer.singleton().getBean("IAuthService");
            return  authService.auth(id, token);
        }catch(IllegalStateException e){
            return new Result<DeveloperInfo>(false, CONSTANT.SSO_SERVICE_EXCEPTION);
        }
    }


    @RequestMapping(value = "/{id}/{token}/balance",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Result<Balance> getBalance(@PathVariable("id") Integer id,
                                      @PathVariable("token") String token){
        try {
            authService = (IAuthService) Consumer.singleton().getBean("IAuthService");
            return  authService.getBalance(id, token);
        }catch(IllegalStateException e){
            return new Result<Balance>(false, CONSTANT.SSO_SERVICE_EXCEPTION);
        }
    }



}
