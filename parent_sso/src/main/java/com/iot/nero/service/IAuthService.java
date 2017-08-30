package com.iot.nero.service;

import com.iot.nero.dto.Result;
import com.iot.nero.parent_balance.entity.Balance;
import com.iot.nero.parent_sso.dto.DeveloperInfo;
import com.iot.nero.parent_sso.entity.Developer;
import com.iot.nero.parent_sso.entity.DeveloperAdds;
import org.springframework.web.multipart.MultipartFile;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/6/26
 * Time   下午4:15
 */
public interface IAuthService {

    /**
     * 开发者登录
     * @param email
     * @param pwd
     * @return
     */
    Result<DeveloperInfo> login(String email, String pwd);

    /**
     * 认证token是否正确
     * @param did
     * @param token
     * @return
     */
    Result<DeveloperInfo> auth(Integer did, String token);

    /**
     * 获取用户消费信息
     * @param id
     * @param token
     * @return
     */
    Result<Balance> getBalance(Integer id, String token);

    /**
     * 新开发者注册
     * @param email
     * @param password
     * @return
     */
    Result<DeveloperInfo> register(String name,String email, String password);

    /**
     * 开发者注册激活
     * @param id
     * @param token
     * @return
     */
    Result<DeveloperInfo> active(Integer id, String token);

    /**
     * 获取开发者附加信息
     * @param id
     * @param token
     * @return
     */
    Result<DeveloperAdds> getDeveloperAdds(Integer id, String token);

    /**
     * 设置开发者附加信息
     * @param id
     * @param token
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
    Result<DeveloperAdds> setDeveloperAdds(Integer id, String token, String dProfession, String dBussiness, String dWebsite, String dCountry, String dAddress, String dStreet, String dTel, String dFax);

    /**
     * 修改开发者昵称
     * @param id
     * @param token
     * @param name
     * @param company
     * @return
     */
    Result<DeveloperInfo> changeName(Integer id, String token, String name, String company);

    /**
     * 修改头像
     * @param id
     * @param token
     * @param filename
     * @param avatar
     * @return
     */
    Result<DeveloperInfo> changeAvatar(Integer id, String token, String filename, MultipartFile avatar);

    /**
     * 修改密码
     * @param id
     * @param token
     * @param oldPwd
     * @param newPwd
     * @return
     */
    Result<DeveloperInfo> changePwd(Integer id, String token, String oldPwd, String newPwd);
}
