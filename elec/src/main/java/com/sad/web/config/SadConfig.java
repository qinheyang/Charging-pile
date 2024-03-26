package com.sad.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 全局配置类
 * 
 * @author ruoyi
 */
@Component
@ConfigurationProperties(prefix = "sad")
public class SadConfig
{
    /** 项目名称 */
    private static String name;



    /** 上传路径 */
    private static String profile;



    public static String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        SadConfig.name = name;
    }


    public static String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        SadConfig.profile = profile;
    }


    /**
     * 获取导入上传路径
     */
    public static String getImportPath()
    {
        return getProfile() + "/import";
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }
}
