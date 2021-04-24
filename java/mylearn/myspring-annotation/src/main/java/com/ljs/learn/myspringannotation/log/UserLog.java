package com.ljs.learn.myspringannotation.log;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserLog {
    private static final Logger logger = LoggerFactory.getLogger(UserLog.class);

    public static void main(String[] args) {
        logger.info("user info log test");
        logger.error("user error log test");
    }
}
