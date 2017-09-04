package com.iot.nero.service_sso.service.impl;

import com.iot.nero.dto.Result;
import com.iot.nero.facade.IBalanceFacade;
import com.iot.nero.parent_balance.entity.Balance;
import com.iot.nero.parent_sso.constant.CONSTANT;
import com.iot.nero.parent_sso.dto.DeveloperInfo;
import com.iot.nero.parent_sso.entity.Developer;
import com.iot.nero.parent_sso.entity.DeveloperAdds;
import com.iot.nero.parent_sso.exception.*;
import com.iot.nero.service_sso.Consumer;
import com.iot.nero.facade.IDeveloperFacade;
import com.iot.nero.service.IAuthService;
import com.iot.nero.utils.rendom.RandomString;
import com.iot.nero.utils.token.TOKEN_TYPE;
import com.iot.nero.utils.token.Token;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/6/26
 * Time   下午5:20
 */
@Service
public class AuthService implements IAuthService {


    private IDeveloperFacade developerFacade;
    private IBalanceFacade balanceFacade;

    public synchronized Result<DeveloperInfo> login(String email, String pwd) {


        developerFacade = null;
        try {
            developerFacade = (IDeveloperFacade)Consumer.singleton().getBean("IDeveloperFacade");
            boolean isDeveloperExists = developerFacade.isLoginDeveloperExists(email);
            boolean isPwdCorrect =  developerFacade.isPwdCorrect(email,pwd);
            Developer developer = developerFacade.updateDeveloperToken(email,new Token(pwd, TOKEN_TYPE.MD5));
            return new Result<DeveloperInfo>(true,new DeveloperInfo(
                    developer.getId(),
                    developer.getName(),
                    developer.getCompany(),
                    developer.getEmail(),
                    developer.getPhone(),
                    developer.getToken(),
                    developer.getCreateTime()
            ));
        } catch (DeveloperNotExistsException e) {
            return new Result<DeveloperInfo>(false,e.getMessage());
        } catch (PasswordIncorrectException e) {
            return new Result<DeveloperInfo>(false, e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            return new Result<DeveloperInfo>(false, e.getMessage());
        } catch (UnsupportedEncodingException e) {
            return new Result<DeveloperInfo>(false, e.getMessage());
        } catch (TokenUpdateException e) {
            return new Result<DeveloperInfo>(false, e.getMessage());
        }catch (IllegalStateException e){
            return new Result<DeveloperInfo>(false, e.getMessage());
        }
    }

    public Result<DeveloperInfo> auth(Integer did, String token) {

        developerFacade = null;
        try {
            developerFacade = (IDeveloperFacade)Consumer.singleton().getBean("IDeveloperFacade");
            boolean auth = developerFacade.isTokenCorrect(did,token);
            Developer developer = developerFacade.getDeveloperByToken(token);
            return new Result<DeveloperInfo>(true,new DeveloperInfo(developer.getId(),
                    developer.getName(),
                    developer.getCompany(),
                    developer.getEmail(),
                    developer.getPhone(),
                    developer.getToken(),
                    developer.getCreateTime()));
        } catch (DeveloperNotExistsException e) {
            return new Result<DeveloperInfo>(false, e.getMessage());
        }catch (IllegalStateException e){
            return new Result<DeveloperInfo>(false, e.getMessage());
        } catch (TokenExpiredException e) {
            return new Result<DeveloperInfo>(false, e.getMessage());
        }
    }

    public Result<Balance> getBalance(Integer id, String token) {

        try {
            developerFacade = (IDeveloperFacade) Consumer.singleton().getBean("IDeveloperFacade");
        } catch (IllegalStateException e) {
            return new Result<Balance>(false, CONSTANT.SSO_FACADE_EXCEPTION);
        }

        try {
            boolean auth = developerFacade.isTokenCorrect(id, token);
        } catch (DeveloperNotExistsException e) {
            return new Result<Balance>(false, e.getMessage());
        } catch (TokenExpiredException e) {
            return new Result<Balance>(false, e.getMessage());
        }

        try {
            balanceFacade = (IBalanceFacade) Consumer.singleton().getBean("IBalanceFacade");
        } catch (IllegalStateException e) {
            return new Result<Balance>(false, CONSTANT.BALANCE_FACADE_EXCEPTION);
        }

        Balance balance = balanceFacade.getBalanceByDId(id);

        if(balance==null){
            return new Result<Balance>(false,CONSTANT.BALANCE_NOT_FOUND);
        }
        return new Result<Balance>(true,balance);
    }

    public Result<DeveloperInfo> register(String name,String email, String password) {
        developerFacade = null;
        Token token = new Token(email);
        try {
            developerFacade = (IDeveloperFacade)Consumer.singleton().getBean("IDeveloperFacade");

            Developer isDeveloperExists = developerFacade.isRegisterDeveloperExists(email);

            Developer developer = developerFacade.addDeveloper(name,email,password,token.genCode());

            Boolean mail = developerFacade.sendActiveEmail(developer.getName(),developer.getEmail(),token);

            return new Result<DeveloperInfo>(true,new DeveloperInfo(developer.getId(),
                    developer.getName(),
                    developer.getCompany(),
                    developer.getEmail(),
                    developer.getPhone(),
                    developer.getToken(),
                    developer.getCreateTime()));
        } catch (DeveloperActivedException e) {
            return new Result<DeveloperInfo>(false,e.getMessage());
        } catch (DeveloperExistsException e) {
            Developer developer = null;
            try {
                developer = developerFacade.updateDeveloper(name,email,password,token.genCode());

                Boolean mail = developerFacade.sendActiveEmail(developer.getName(),developer.getEmail(),token);

            } catch (UnsupportedEncodingException e1) {
                return new Result<DeveloperInfo>(false,e.getMessage());
            } catch (NoSuchAlgorithmException e1) {
                return new Result<DeveloperInfo>(false,e.getMessage());
            } catch (DeveloperUpdateFailedException e1) {
                return new Result<DeveloperInfo>(false,e.getMessage());
            }

            return new Result<DeveloperInfo>(true,new DeveloperInfo(developer.getId(),
                    developer.getName(),
                    developer.getCompany(),
                    developer.getEmail(),
                    developer.getPhone(),
                    developer.getToken(),
                    developer.getCreateTime()));
        } catch (DeveloperAddFailedException e) {
            return new Result<DeveloperInfo>(false,e.getMessage());
        } catch (UnsupportedEncodingException e) {
            return new Result<DeveloperInfo>(false,e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            return new Result<DeveloperInfo>(false,e.getMessage());
        }
    }

    public Result<DeveloperInfo> active(Integer id, String token) {
        return null;
    }


    public Result<DeveloperAdds> getDeveloperAdds(Integer id, String token) {
        try {
            developerFacade = (IDeveloperFacade) Consumer.singleton().getBean("IDeveloperFacade");
        } catch (IllegalStateException e) {
            return new Result<DeveloperAdds>(false, CONSTANT.SSO_FACADE_EXCEPTION);
        }

        try {
            boolean auth = developerFacade.isTokenCorrect(id, token);

            return new Result<DeveloperAdds>(true,developerFacade.getDeveloperAdds(id));

        } catch (DeveloperNotExistsException e) {
            return new Result<DeveloperAdds>(false, e.getMessage());
        } catch (TokenExpiredException e) {
            return new Result<DeveloperAdds>(false, e.getMessage());
        } catch (DeveloperAddNotFoundException e) {
            return new Result<DeveloperAdds>(false, e.getMessage());
        }

    }

    public Result<DeveloperAdds> setDeveloperAdds(Integer id, String token, String dProfession, String dBussiness, String dWebsite, String dCountry, String dAddress, String dStreet, String dTel, String dFax) {
        try {
            developerFacade = (IDeveloperFacade) Consumer.singleton().getBean("IDeveloperFacade");
        } catch (IllegalStateException e) {
            return new Result<DeveloperAdds>(false, CONSTANT.SSO_FACADE_EXCEPTION);
        }

        try {
            boolean auth = developerFacade.isTokenCorrect(id, token);

            return new Result<DeveloperAdds>(true,developerFacade.setDeveloperAdds(id,
                    dProfession,
                    dBussiness,
                    dWebsite,
                    dCountry,
                    dAddress,
                    dStreet,
                    dTel,
                    dFax));

        } catch (DeveloperNotExistsException e) {
            return new Result<DeveloperAdds>(false, e.getMessage());
        } catch (TokenExpiredException e) {
            return new Result<DeveloperAdds>(false, e.getMessage());
        } catch (DeveloperAddsAddFailedException e) {
            return new Result<DeveloperAdds>(false, e.getMessage());
        } catch (DeveloperAddsUpdateFailedException e) {
            return new Result<DeveloperAdds>(false, e.getMessage());
        }
    }

    public Result<DeveloperInfo> changeName(Integer id, String token, String name, String company) {
        try {
            developerFacade = (IDeveloperFacade) Consumer.singleton().getBean("IDeveloperFacade");
        } catch (IllegalStateException e) {
            return new Result<DeveloperInfo>(false, CONSTANT.SSO_FACADE_EXCEPTION);
        }

        try {
            boolean auth = developerFacade.isTokenCorrect(id, token);

            return new Result<DeveloperInfo>(true,developerFacade.changeName(id,name,company));

        } catch (DeveloperNotExistsException e) {
            return new Result<DeveloperInfo>(false, e.getMessage());
        } catch (TokenExpiredException e) {
            return new Result<DeveloperInfo>(false, e.getMessage());
        } catch (DeveloperNameChangeFailedException e) {
            return new Result<DeveloperInfo>(false, e.getMessage());
        }
    }

    public Result<DeveloperInfo> changeAvatar(Integer id, String token, String filename, MultipartFile avatar) {
        try {
            developerFacade = (IDeveloperFacade) Consumer.singleton().getBean("IDeveloperFacade");
        } catch (IllegalStateException e) {
            return new Result<DeveloperInfo>(false, CONSTANT.SSO_FACADE_EXCEPTION);
        }

        try {
            boolean auth = developerFacade.isTokenCorrect(id, token);

            return null;/////////////////////////////////////////////////////

        } catch (DeveloperNotExistsException e) {
            return new Result<DeveloperInfo>(false, e.getMessage());
        } catch (TokenExpiredException e) {
            return new Result<DeveloperInfo>(false, e.getMessage());
        }
    }

    public Result<DeveloperInfo> changePwd(Integer id, String token, String oldPwd, String newPwd) {
        try {
            developerFacade = (IDeveloperFacade) Consumer.singleton().getBean("IDeveloperFacade");
        } catch (IllegalStateException e) {
            return new Result<DeveloperInfo>(false, CONSTANT.SSO_FACADE_EXCEPTION);
        }

        try {
            boolean auth = developerFacade.isTokenCorrect(id, token);

            return new Result<DeveloperInfo>(true,developerFacade.changePwd(id,oldPwd,newPwd));

        } catch (DeveloperNotExistsException e) {
            return new Result<DeveloperInfo>(false, e.getMessage());
        } catch (TokenExpiredException e) {
            return new Result<DeveloperInfo>(false, e.getMessage());
        } catch (PasswordIncorrectException e) {
            return new Result<DeveloperInfo>(false, e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            return new Result<DeveloperInfo>(false, e.getMessage());
        } catch (UnsupportedEncodingException e) {
            return new Result<DeveloperInfo>(false, e.getMessage());
        } catch (PasswordChangeFailedException e) {
            return new Result<DeveloperInfo>(false, e.getMessage());
        }
    }
}
