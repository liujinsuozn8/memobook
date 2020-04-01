package com.ljs.learn.mylog4j;

import org.apache.log4j.Logger;

public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class);

    public static void main(String[] args) {
        logger.debug("UserService test debug");
        logger.info("UserService test info");
        logger.error("UserService test error");
        logger.warn("UserService test warn");
        logger.debug("中文测试");
    }
}
