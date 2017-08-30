package com.iot.nero.facade_resources.dao;

import com.iot.nero.parent_resources.entity.Resource;

import java.util.List;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/7/21
 * Time   下午12:18
 */
public interface ResourceDao {
    Resource findByMd5(String md5Code);

    int addResource(String realFileName, String realResourcePath, String md5Code, String s, String fileType, long uploaderId);

    Resource getResourceById(long resourceId);

    List<Resource> getResource();
}
