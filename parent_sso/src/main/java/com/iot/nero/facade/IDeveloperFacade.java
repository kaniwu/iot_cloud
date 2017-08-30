package com.iot.nero.facade;

import com.iot.nero.dto.Result;
import com.iot.nero.parent_sso.dto.DeveloperInfo;
import com.iot.nero.parent_sso.entity.Developer;
import com.iot.nero.parent_sso.entity.DeveloperAdds;
import com.iot.nero.parent_sso.exception.*;
import com.iot.nero.utils.token.Token;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/6/26
 * Time   下午4:15
 */
public interface IDeveloperFacade {
    /**
     * 判断开发者是否存在
     * @param email
     * @return
     * @throws DeveloperNotExistsException
     */
    boolean isLoginDeveloperExists(String email) throws DeveloperNotExistsException;

    /**
     * 检测密码是否正确
     * @param email
     * @param pwd
     * @return
     * @throws PasswordIncorrectException
     */
    boolean isPwdCorrect(String email, String pwd) throws PasswordIncorrectException, NoSuchAlgorithmException, UnsupportedEncodingException, DeveloperNotExistsException;

    /**
     * 获取开发者信息
     * @param email
     * @return
     * @throws DeveloperNotExistsException
     */
    Developer getDeveloperByEmail(String email) throws DeveloperNotExistsException;

    /**
     * 获取开发者信息
     * @param token
     * @return
     * @throws DeveloperNotExistsException
     */
    Developer getDeveloperByToken(String token) throws DeveloperNotExistsException;

    /**
     * 判断token是否正确
     * @param did
     * @param token
     * @return
     */
    boolean isTokenCorrect(Integer did, String token) throws DeveloperNotExistsException, TokenExpiredException;

    /**
     * 更新DeveloperToken信息，每次登录或者注销更新
     * @param email
     * @param token
     * @return
     * @throws DeveloperNotExistsException
     */
    Developer updateDeveloperToken(String email, Token token) throws DeveloperNotExistsException, TokenUpdateException, UnsupportedEncodingException, NoSuchAlgorithmException;

    /**
     * 更新DeveloperToken信息，每次认证
     * @param id
     * @param token
     * @return
     * @throws DeveloperNotExistsException
     */
    Developer updateDeveloperTokenById(Integer id, String token) throws DeveloperNotExistsException, UnsupportedEncodingException, NoSuchAlgorithmException, TokenUpdateException;

    /**
     * 检验用户密码是否正确
     * @param uid
     * @param pwd
     * @return
     */
    boolean isUPwdCorrect(Integer uid, String pwd) throws DeveloperNotExistsException, UnsupportedEncodingException, NoSuchAlgorithmException, PasswordIncorrectException;

    /**
     * 检查邮箱是否注册
     * @param email
     * @return
     * @throws DeveloperNotExistsException
     */
    Developer isRegisterDeveloperExists(String email) throws DeveloperExistsException, DeveloperActivedException;

    /**
     * 添加新开发者
     * @param email
     * @param password
     * @return
     */
    Developer addDeveloper(String name,String email, String password,String token) throws UnsupportedEncodingException, NoSuchAlgorithmException, DeveloperAddFailedException;

    /**
     * 从新发送邮件
     * @param email
     * @param password
     * @return
     */
    Developer updateDeveloper(String name,String email, String password,String token) throws UnsupportedEncodingException, NoSuchAlgorithmException, DeveloperUpdateFailedException;

    /**
     * 发送激活邮件
     * @param name
     * @param email
     * @param token
     * @return
     */
    Boolean sendActiveEmail(String name, String email, Token token);

    /**
     * 获取附加信息
     * @param id
     * @return
     */
    DeveloperAdds getDeveloperAdds(Integer id) throws DeveloperAddNotFoundException;

    /**
     * 设置附加信息
     * @param id
     * @param dProfession
     * @param dBussiness
     * @param dWebsite
     * @param dCountry
     * @param dAddress
     * @param dStreet
     * @param dTel
     * @param dFax
     * @return
     */
    DeveloperAdds setDeveloperAdds(Integer id, String dProfession, String dBussiness, String dWebsite, String dCountry, String dAddress, String dStreet, String dTel, String dFax) throws DeveloperAddsAddFailedException, DeveloperAddsUpdateFailedException;

    /**
     * 修改昵称
     * @param id
     * @param name
     * @param company
     * @return
     */
    DeveloperInfo changeName(Integer id, String name, String company) throws DeveloperNameChangeFailedException;

    /**
     * 修改密码
     * @param id
     * @param oldPwd
     * @param newPwd
     * @return
     */
    DeveloperInfo changePwd(Integer id,  String oldPwd, String newPwd) throws PasswordChangeFailedException, DeveloperNotExistsException, UnsupportedEncodingException, NoSuchAlgorithmException, PasswordIncorrectException;
}
