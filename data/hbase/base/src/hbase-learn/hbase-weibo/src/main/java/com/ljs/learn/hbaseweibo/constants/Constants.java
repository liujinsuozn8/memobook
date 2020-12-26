package com.ljs.learn.hbaseweibo.constants;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

public final class Constants {
    public static final Configuration CONF = HBaseConfiguration.create();

    // 命名空间
    public static final String NAMESPACE="weibo";

    // 表名
    // 微博内容表
    public static final String CONTENT_TABLE = NAMESPACE + ":content";
    public static final String CONTENT_TABLE_CF = "info";
    public static final int CONTENT_TABLE_VERSIONS = 1;

    // 用户关系表
    public static final String RELATION_TABLE= NAMESPACE + ":relation";
    // 用户关注的列表
    public static final String RELATION_TABLE_CF_1= "attends";
    // 关注用户的列表
    public static final String RELATION_TABLE_CF_2= "fans";
    public static final int RELATION_TABLE_VERSIONS = 1;

    // 收件箱表
    public static final String INBOX_TABLE= NAMESPACE + ":inbox";
    public static final String INBOX_TABLE_CF = "info";
    public static final int INBOX_TABLE_VERSIONS = 2;

}
