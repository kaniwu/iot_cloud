package com.iot.nero.facade_sso.facade.impl;

import com.iot.nero.dto.Result;
import com.iot.nero.facade.IDeveloperFacade;
import com.iot.nero.facade_sso.dao.DeveloperDao;
import com.iot.nero.parent_sso.constant.CONSTANT;
import com.iot.nero.parent_sso.dto.DeveloperInfo;
import com.iot.nero.parent_sso.entity.Developer;
import com.iot.nero.parent_sso.entity.DeveloperAdds;
import com.iot.nero.parent_sso.exception.*;
import com.iot.nero.utils.md5.MD5;
import com.iot.nero.utils.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.collection.immutable.Stream;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/6/26
 * Time   下午4:51
 */
@Service
public class DeveloperFacade implements IDeveloperFacade {

    @Autowired
    private DeveloperDao developerDao;

    /**
     * 判断开发者是否存在
     * @param email
     * @return 开发者是否存在，否则抛出异常
     * @throws DeveloperNotExistsException
     */
    public boolean isLoginDeveloperExists(String email) throws DeveloperNotExistsException {
        if(developerDao.getDeveloperByEmail(email)==null){
            throw new DeveloperNotExistsException(CONSTANT.DEVELOPER_NOT_EXISTS);
        }
        return true;
    }

    /**
     * 判断密码是否正确
     * 加密方式 Md5(邮件地址+Md5(密码))
     * @param email
     * @param pwd
     * @return
     * @throws PasswordIncorrectException
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws DeveloperNotExistsException
     */
    public boolean isPwdCorrect(String email, String pwd) throws PasswordIncorrectException, NoSuchAlgorithmException, UnsupportedEncodingException, DeveloperNotExistsException {

        Developer developer = developerDao.getDeveloperByEmail(email);

        try {
            if(developer==null){
                throw new DeveloperNotExistsException(CONSTANT.DEVELOPER_NOT_EXISTS);
            }else{
                if(!developer.getPwd().equals(MD5.EncoderByMd5(email+MD5.EncoderByMd5(pwd)))){
                    throw new PasswordIncorrectException(CONSTANT.PASSWORD_INCORRECT);
                }
                return true;
            }
        } catch (NoSuchAlgorithmException e) {
            throw e;
        } catch (UnsupportedEncodingException e) {
            throw e;
        } catch (DeveloperNotExistsException e) {
            throw e;
        }
    }

    /**
     * 获取开发者完整信息
     * @param email
     * @return
     * @throws DeveloperNotExistsException
     */
    public Developer getDeveloperByEmail(String email) throws DeveloperNotExistsException {

        Developer developer = developerDao.getDeveloperByEmail(email);

        if(developer==null){
            throw new DeveloperNotExistsException(CONSTANT.DEVELOPER_NOT_EXISTS);
        }else{
            return developer;
        }
    }

    public Developer getDeveloperByToken(String token) throws DeveloperNotExistsException {
        Developer developer = developerDao.getDeveloperByToken(token);

        if(developer==null){
            throw new DeveloperNotExistsException(CONSTANT.DEVELOPER_NOT_EXISTS);
        }else{
            return developer;
        }
    }
    /**
     * 认证token
     * @param did
     * @param token
     * @return
     */
    public boolean isTokenCorrect(Integer did, String token) throws DeveloperNotExistsException, TokenExpiredException {
        Developer developer = developerDao.getDeveloperByToken(token);
        if(developer==null){
            throw new TokenExpiredException(CONSTANT.TOKEN_EXPIRED);
        }else{
            if(developer.getId()!=did){
                throw new DeveloperNotExistsException(CONSTANT.DEVELOPER_NOT_EXISTS);
            }
            return true;
        }
    }

    /**
     * 更新开发者Token信息
     * Token = Md5(32位随即串+Md5(当前Linux时间戳)+semail)
     * @param email
     * @param token
     * @return
     * @throws DeveloperNotExistsException
     * @throws TokenUpdateException
     */
    public synchronized Developer updateDeveloperToken(String email, Token token) throws DeveloperNotExistsException, TokenUpdateException, UnsupportedEncodingException, NoSuchAlgorithmException {
        Developer developer = developerDao.getDeveloperByEmail(email);

        if(developer==null){
            throw new DeveloperNotExistsException(CONSTANT.DEVELOPER_NOT_EXISTS);
        }else{

            String tokenString = token.genCode();
            if(developerDao.updateDeveloperToken(email, tokenString)<1){
                throw new TokenUpdateException(CONSTANT.TOKEN_UPDATE_EXCEPTION);
            }
            developer.setToken(tokenString);
            return developer;
        }
    }

    public synchronized Developer updateDeveloperTokenById(Integer id, String to) throws DeveloperNotExistsException, UnsupportedEncodingException, NoSuchAlgorithmException, TokenUpdateException {
        Developer developer = developerDao.getDeveloperByToken(to);

        if(developer==null){
            throw new DeveloperNotExistsException(CONSTANT.DEVELOPER_NOT_EXISTS);
        }else{
            Token token = new Token(to);
            String tokenString = token.genCode();
            if(developerDao.updateDeveloperToken(developer.getEmail(), tokenString)<1){
                throw new TokenUpdateException(CONSTANT.TOKEN_UPDATE_EXCEPTION);
            }
            developer.setToken(tokenString);
            return developer;
        }
    }

    public boolean isUPwdCorrect(Integer uid, String pwd) throws DeveloperNotExistsException, UnsupportedEncodingException, NoSuchAlgorithmException, PasswordIncorrectException {
        Developer developer = developerDao.getDeveloperById(uid);
        if(developer==null){
            throw new DeveloperNotExistsException(CONSTANT.DEVELOPER_NOT_EXISTS);
        }else{
            if(!developer.getPwd().equals(MD5.EncoderByMd5(developer.getEmail()+MD5.EncoderByMd5(pwd)))){
                throw new PasswordIncorrectException(CONSTANT.PASSWORD_INCORRECT);
            }
            return true;
        }
    }


    public Developer isRegisterDeveloperExists(String email) throws DeveloperExistsException, DeveloperActivedException {
        Developer developer = developerDao.getDeveloperByEmail(email);
        if(developer!=null){
            if(developer.getIsActive()==1){
                throw new DeveloperActivedException(CONSTANT.DEVELOPER_ALREADY_ACTIVED);
            }else{
                throw new DeveloperExistsException(CONSTANT.DEVELOPER_ALREADY_EXISTS);
            }
        }
        return developer;
    }


    public Developer addDeveloper(String name,String email, String password,String token) throws UnsupportedEncodingException, NoSuchAlgorithmException, DeveloperAddFailedException {

        String pwd = MD5.EncoderByMd5(email+MD5.EncoderByMd5(password));

        if(developerDao.addDeveloperBase(name,email,pwd,token)<1){
            throw new DeveloperAddFailedException(CONSTANT.DEVELOPER_ADD_FAILED);
        }
        return developerDao.getDeveloperByToken(token);
    }


    public Developer updateDeveloper(String name,String email, String password,String token) throws UnsupportedEncodingException, NoSuchAlgorithmException, DeveloperUpdateFailedException {
        String pwd = MD5.EncoderByMd5(email+MD5.EncoderByMd5(password));

        if(developerDao.updateDeveloperBase(name,email,pwd,token)<1){
            throw new DeveloperUpdateFailedException(CONSTANT.DEVELOPER_UPDATE_FAILED);
        }
        return developerDao.getDeveloperByToken(token);
    }

    public Boolean sendActiveEmail(String name, String email, Token token) {
        return null;
    }

    public DeveloperAdds getDeveloperAdds(Integer did) throws DeveloperAddNotFoundException {
        DeveloperAdds developerAdds = developerDao.getDeveloperAdds(did);
        if(developerAdds==null){
            throw new DeveloperAddNotFoundException(CONSTANT.ADDS_NOT_FOUND);
        }
        return developerAdds;
    }

    public DeveloperAdds setDeveloperAdds(Integer did,String dProfession, String dBussiness, String dWebsite, String dCountry, String dAddress, String dStreet, String dTel, String dFax) throws DeveloperAddsAddFailedException, DeveloperAddsUpdateFailedException {
        DeveloperAdds developerAdds = developerDao.getDeveloperAdds(did);
        if(developerAdds==null){
            if(developerDao.insertDeveloperAdds(did, dProfession,  dBussiness,  dWebsite,  dCountry,  dAddress,  dStreet,  dTel,  dFax)<1){
                throw new DeveloperAddsAddFailedException(CONSTANT.ADDS_ADD_FAILED);
            }
        }else{
            if(developerDao.updateDeveloperAdds(did, dProfession,  dBussiness,  dWebsite,  dCountry,  dAddress,  dStreet,  dTel,  dFax)<1){
                throw new DeveloperAddsUpdateFailedException(CONSTANT.ADDS_UPDATE_FAILED);
            }
        }
       return developerDao.getDeveloperAdds(did);
    }

    public DeveloperInfo changeName(Integer did, String name, String company) throws DeveloperNameChangeFailedException {
        if(developerDao.changeName(did,name,company)<1){
            throw new DeveloperNameChangeFailedException(CONSTANT.NAME_CHANGE_FAILED);
        }
        return developerDao.getDeveloperInfoByEmail(developerDao.getDeveloperById(did).getEmail());
    }

    public DeveloperInfo changePwd(Integer did,  String oldPwd, String newPwd) throws PasswordChangeFailedException, DeveloperNotExistsException, UnsupportedEncodingException, NoSuchAlgorithmException, PasswordIncorrectException {
        Developer developer = developerDao.getDeveloperById(did);
        if(developer==null){
            throw new DeveloperNotExistsException(CONSTANT.DEVELOPER_NOT_EXISTS);
        }
        if(developer.getPwd().equals(MD5.EncoderByMd5(developer.getEmail()+MD5.EncoderByMd5(oldPwd)))){
            throw new PasswordIncorrectException(CONSTANT.PASSWORD_INCORRECT);
        }
        if(developerDao.changePwd(did,newPwd)<1){
            throw new PasswordChangeFailedException(CONSTANT.PASSWORD_CHANGE_FAILED);
        }
        return developerDao.getDeveloperInfoByEmail(developerDao.getDeveloperById(did).getEmail());
    }
}
