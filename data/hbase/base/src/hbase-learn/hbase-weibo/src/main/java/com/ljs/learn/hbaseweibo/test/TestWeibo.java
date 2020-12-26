package com.ljs.learn.hbaseweibo.test;

import com.ljs.learn.hbaseweibo.dao.HBaseDao;
import com.ljs.learn.hbaseweibo.utils.HBaseUtils;

import java.io.IOException;

import static com.ljs.learn.hbaseweibo.constants.Constants.*;

public class TestWeibo {
    public static void init() throws IOException {
        // 创建命名空间
        // HBaseUtils.createNameSpace(NAMESPACE);
        // 创建表
        HBaseUtils.createTable(
                CONTENT_TABLE,
                CONTENT_TABLE_VERSIONS,
                CONTENT_TABLE_CF
        );
        HBaseUtils.createTable(
                RELATION_TABLE,
                RELATION_TABLE_VERSIONS,
                RELATION_TABLE_CF_1,
                RELATION_TABLE_CF_2
        );
        HBaseUtils.createTable(
                INBOX_TABLE,
                INBOX_TABLE_VERSIONS,
                INBOX_TABLE_CF
        );
    }

    public static void main(String[] args) throws IOException {
        // 初始化
        init();

        // 1001发布微博
        System.out.println("----------------1001发布微博-------------------");
        HBaseDao.publishWeiBo("1001", "content_1001_00001");

        // 1002关注1001和1003
        System.out.println("---------------1002关注1001和1003--------------------");
        HBaseDao.addAttends("1002", "1001","1003");

        // 获取1002初始化页面
        System.out.println("--------------获取1002初始化页面---------------------");
        HBaseDao.getInit("1002");

        // 1003 发布三条微博
        System.out.println("----------------1003 发布三条微博-------------------");
        HBaseDao.publishWeiBo("1003", "content_1003_00001");
        HBaseDao.publishWeiBo("1003", "content_1003_00002");
        HBaseDao.publishWeiBo("1003", "content_1003_00003");

        // 1001 发布2条微博
        System.out.println("-----------------1001 发布2条微博------------------");
        HBaseDao.publishWeiBo("1001", "content_1001_00002");
        HBaseDao.publishWeiBo("1001", "content_1001_00003");

        // 获取1002初始化页面
        System.out.println("----------------获取1002初始化页面-------------------");
        HBaseDao.getInit("1002");

        // 1002 取关1003
        System.out.println("----------------1002 取关1003-------------------");
        HBaseDao.deleteAttends("1002", "1003");

        // 获取1002初始化页面-- 只显示4条数据 1001 * 2 + 1003 * 2
        System.out.println("----------------获取1002初始化页面-------------------");
        HBaseDao.getInit("1002");

        // 1002再次关注1003
        System.out.println("-----------------1002再次关注1003------------------");
        HBaseDao.addAttends("1002", "1003");

        // 获取1002初始化页面
        System.out.println("----------------获取1002初始化页面-------------------");
        HBaseDao.getInit("1002");

        // 获取 1001 微博详情
        System.out.println("------------------获取 1001 微博详情-----------------");
        HBaseDao.getDetail("","1001");
    }
}
