package com.iot.nero.facade;

import com.iot.nero.dto.Result;
import com.iot.nero.parent_resources.entity.Resource;

import java.util.List;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/7/21
 * Time   下午12:09
 */
public interface IResourcesFacade {
        /**
         * 储存资源
         * @param file
         * @param uploaderId
         * @param fileName
         * @return
         */
        Result<Resource> addImgResource(byte[] file, long uploaderId, String fileName);


        /**
         * 获取资源
         * @param resourceId
         * @return
         */
        Result<Resource> getImgResourceById(long resourceId);

        /**
         * 获取所有资源
         * @return
         */
        Result<List<Resource>> getImgResource();

}
