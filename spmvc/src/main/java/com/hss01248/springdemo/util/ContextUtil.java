package com.hss01248.springdemo.util;

import java.io.File;

/**
 * Created by Administrator on 2017/1/7 0007.
 */
public class ContextUtil {

    public static  String rootPath;
    public static  String imageDir;

    public static String getRootPath(){
        if(rootPath == null){
            rootPath = System.getProperty("hss01248Web.root");
        }

        return rootPath;

    }

    public static String getImageDir(){
        if(imageDir == null){
            imageDir = getRootPath()+ File.separator+"WEB-INF"+ File.separator+"image";
        }

        return imageDir;
    }
}
