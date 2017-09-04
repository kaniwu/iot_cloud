package com.iot.nero.facade_resources.facade.impl;

import com.iot.nero.dto.Result;
import com.iot.nero.facade.IResourcesFacade;
import com.iot.nero.facade_resources.dao.ResourceDao;
import com.iot.nero.parent_resources.entity.Resource;
import com.iot.nero.parent_resources.constant.CONSTANT;
import com.iot.nero.utils.file.FileType;
import com.iot.nero.utils.md5.FileDiffUtil;
import com.iot.nero.utils.rendom.RandomString;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author neroyang
 * Email  nerosoft@outlook.com
 * Date   2017/7/21
 * Time   下午12:08
 */
public class ResourcesFacade implements IResourcesFacade {


        @Autowired
        private ResourceDao resourceDao;



        public boolean isImgTypeAllow(String type){

            Map<String,Boolean> allowMap = new HashMap<String, Boolean>();

            allowMap.put("jpg",true);                               //允许jpg
            allowMap.put("png",true);                               //允许png

            if(allowMap.get(type)!=null && allowMap.get(type)){
                return true;
            }
            return false;
        }

        public Result<Resource> addImgResource(byte[] file, long uploaderId, String realFileName) {


            try{
                String fileType = FileType.getFileTypeByStream(file);//文件真实格式
                System.out.println("format:"+fileType);

                if(isImgTypeAllow(fileType)){

                    String md5Code = FileDiffUtil.getMD5(file);//获取文件MD5
                    Resource resource = resourceDao.findByMd5(md5Code);
                    String filePath = "/home/click_data";                   //读取commom.properties获得
                    String fileName;
                    String realResourcePath;                //文件系统资源地址

                    if(resource!=null){//MD5校验码一致，不必磁盘储存，添加数据库记录即可。
                        realResourcePath =resource.getFilepath();
                        md5Code = resource.getHashCode();

                    }else{
                        RandomString randomString = new RandomString();

                        //产生随机文件名称
                        String dbFilePath = randomString.getRandomString(64)+String.valueOf(System.currentTimeMillis());
                        //文件名称MD5
                        fileName = FileDiffUtil.getMD5(dbFilePath.getBytes());//数据库资源名称

                        realResourcePath = filePath+ File.separator+fileName+"."+fileType;

                        System.out.println(realResourcePath);
                        //储存文件
                        FileType.getFile(file,filePath,fileName+"."+fileType);
                    }
                    //添加数据库记录
                    if(resourceDao.addResource(realFileName,realResourcePath,md5Code,String.valueOf(file.length),fileType,uploaderId)<1){
                        return new Result<Resource>(false, CONSTANT.FILE_UPLOAD_FAILED);
                    }
                    return new Result<Resource>(true,new Resource(
                            realFileName,                   //文件名
                            realResourcePath,               //文件地址
                            md5Code,                        //hashCodo
                            null,                           //文件大小
                            fileType,                       //文件真实格式
                            uploaderId,                     //上传者id
                            null,                           //上传时间
                            -1                              //资源id
                    ));
                }
                return new Result<Resource>(false,CONSTANT.FILE_FORMAT_UNSUPPORED);//格式不让上传
            } catch (IOException e) {
                return new Result<Resource>(false,CONSTANT.FILE_ERROR);
            } catch (Exception e) {
                e.printStackTrace();
                return new Result<Resource>(false, CONSTANT.FILE_STORE_FAILED);
            }
        }


        public Result<Resource> getImgResourceById(long resourceId) {
            return new Result<Resource>(true,resourceDao.getResourceById(resourceId));
        }

        public Result<List<Resource>> getImgResource() {
            return new Result<List<Resource>>(true,resourceDao.getResource());
        }

}
