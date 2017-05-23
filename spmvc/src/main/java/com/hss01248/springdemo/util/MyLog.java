package com.hss01248.springdemo.util;

import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2017/1/7 0007.
 */
public class MyLog {

    private static Logger logger = Logger.getLogger(MyLog.class);

    public static void d(Object obj){
        logger.debug(obj);
    }
    public static void e(Object obj){
        logger.error(obj);
    }
    public static void i(Object obj){
        logger.info(obj);
    }

}
