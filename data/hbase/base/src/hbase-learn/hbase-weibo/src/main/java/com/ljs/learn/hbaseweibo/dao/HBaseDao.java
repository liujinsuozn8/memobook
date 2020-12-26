package com.ljs.learn.hbaseweibo.dao;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.ljs.learn.hbaseweibo.constants.Constants.*;

/**
 * 发布微博
 * 关注用户
 * 取关用户
 * 获取某个用户的初始化页面
 * 获取某个用户的所有微博详情
 */
public class HBaseDao {
    /**
     * 发布微博
     *
     * 1. 在 content 表中插入 uid 用户的数据，并保存其 rowkey
     * 2. 在 relation 表中检索 uid 的数据，获取其 fans 数据
     * 3. 在 inbox 表中，向每个 fans 中插入 uid 在 content 表中最新的rowkey
     *
     * @param uid     用户id
     * @param content 微博内容
     */
    public static void publishWeiBo(String uid, String content) throws IOException {
        // 获取connection
        Connection connection = ConnectionFactory.createConnection(CONF);

        // 1. 操作微博内容表
        Table contentTable = connection.getTable(TableName.valueOf(CONTENT_TABLE));
        // 创建rowkey=时间戳
        // TODO 需要将时间戳反转，防止【关注用户】时，插入过多的数据
        String rowKey = uid + "_" + System.currentTimeMillis();

        Put put = new Put(Bytes.toBytes(rowKey));
        put.addColumn(Bytes.toBytes(CONTENT_TABLE_CF), Bytes.toBytes("content"), Bytes.toBytes(content));
        contentTable.put(put);

        // 2. 从用户关系表中获取 fans 数据
        Table relationTable = connection.getTable(TableName.valueOf(RELATION_TABLE));
        Get relationGet = new Get(Bytes.toBytes(uid));
        // 只获取fans列族的数据
        relationGet.addFamily(Bytes.toBytes(RELATION_TABLE_CF_2));
        // 没给cell中保存的是 fans 的 uid
        Result fansResult = relationTable.get(relationGet);

        // 3. 如果有粉丝，则继续操作
        Cell[] fans = fansResult.rawCells();
        if (fans.length > 0) {
            // 遍历时，创建插入 inbox 表的put对象
            List<Put> inboxPutList = new ArrayList<>(fans.length);
            for (Cell fan : fans) {
                // 以当前 fans 的uid为rowkey创建put对象
                Put inboxPut = new Put(CellUtil.cloneQualifier(fan));
                // 将当前用户的uid，和content作为数据写入
                inboxPut.addColumn(Bytes.toBytes(INBOX_TABLE_CF), Bytes.toBytes(uid), Bytes.toBytes(rowKey));

                inboxPutList.add(inboxPut);
            }

            // 4. 将数据插入收件箱表
            Table inboxTable = connection.getTable(TableName.valueOf(INBOX_TABLE));
            inboxTable.put(inboxPutList);

            inboxTable.close();
        }

        relationTable.close();
        contentTable.close();
        connection.close();
    }

    /**
     * 关注用户
     *
     * 1. 先向 relation 表添加数据，需要插入用户自己的关注数据和被关注者的fans数据
     * 2. 从 content 表中获取 attend 的最近的微博信息
     * 3. 将 attend 及其在 content 表中的 rowkey 插入inbox表
     *
     * @param uid     用户id
     * @param attends 关注的用户
     */
    public static void addAttends(String uid, String... attends) throws IOException {
        if (attends.length <= 0) {
            System.out.println("must set attends");
            return;
        }

        Connection connection = ConnectionFactory.createConnection(CONF);

        // 1. 向用户关系表中插入数据
        Table relationTable = connection.getTable(TableName.valueOf(RELATION_TABLE));

        List<Put> attendPutList = new ArrayList<>();
        Put uidPut = new Put(Bytes.toBytes(uid));
        for (String attend : attends) {
            uidPut.addColumn(Bytes.toBytes(RELATION_TABLE_CF_1), Bytes.toBytes(attend), Bytes.toBytes(attend));

            // 1.1. 被关注者，需要将uid添加到fans列
            Put attendPut = new Put(Bytes.toBytes(attend));
            attendPut.addColumn(Bytes.toBytes(RELATION_TABLE_CF_2), Bytes.toBytes(uid), Bytes.toBytes(uid));
            attendPutList.add(attendPut);
        }

        // 1.2. 插入uid的关注数据，插入被关注者的fans数据
        relationTable.put(uidPut);
        relationTable.put(attendPutList);

        // 2. 向inbox表中插入数据
        Table contentTable = connection.getTable(TableName.valueOf(CONTENT_TABLE));

        // 2.1 从 content 表中获取 attend 对应的最近2条数据

        Put inboxUidPut = new Put(Bytes.toBytes(uid));

        for (String attend : attends) {
            // 通过scan获取 attend 的数据，从 xxx_ 开始，到 xxx| 结束
            // 因为 xxx_ 比所有的 xxx_yyy 都小
            // xxx| 比所有的 xxx_yyy 都大
            // 所有可以作为上下界限使用
            Scan attendContentScan = new Scan(
                    Bytes.toBytes(attend + "_"),
                    Bytes.toBytes(attend + "|"));
            ResultScanner attendContentScanner = contentTable.getScanner(attendContentScan);

            long ts = System.currentTimeMillis();

            // inbox中只保存attendid及其在content中保存的rowkey
            for (Result result : attendContentScanner) {
                // 需要附加时间戳，防止最终只显示一条数据
                inboxUidPut.addColumn(
                        Bytes.toBytes(INBOX_TABLE_CF),
                        Bytes.toBytes(attend),
                        ts++,
                        result.getRow() // 获取rowkey
                );
            }
        }

        if (!inboxUidPut.isEmpty()) {
            Table inboxTable = connection.getTable(TableName.valueOf(INBOX_TABLE));

            inboxTable.put(inboxUidPut);

            inboxTable.close();
        }

        contentTable.close();
        relationTable.close();
        connection.close();
    }

    /**
     * 取关用户，可以批量取关
     *
     * 1. 从 relation 表中删除 uid 中的 attends
     * 2. 从 relation 表中的每个 attend 中删除 uid 中的
     * 3. 从 inbox 表中删除 uid 中的每个 attends 的所有数据
     */
    public static void deleteAttends(String uid, String... attends) throws IOException {
        if (attends.length <= 0) {
            System.out.println("must set attends");
            return;
        }

        Connection connection = ConnectionFactory.createConnection(CONF);
        Table relationTable = connection.getTable(TableName.valueOf(RELATION_TABLE));
        Table inboxTable = connection.getTable(TableName.valueOf(INBOX_TABLE));

        Delete relationUidDelete = new Delete(Bytes.toBytes(uid));
        Delete inboxUidDelete = new Delete(Bytes.toBytes(uid));

        List<Delete> relationAttendDeletes = new ArrayList<>(attends.length);
        for (String attend : attends) {
            // 1. 从 relation 表中删除 uid 中的 attends
            // 通过 addColumns 添加列，可以防止异常数据，如同一个人被关注了两次
            // 能够保证将所有的关注数据全部删除
            relationUidDelete.addColumns(
                    Bytes.toBytes(RELATION_TABLE_CF_1),
                    Bytes.toBytes(attend)
            );

            // 2. 从 relation 表中的每个 attend 中删除 uid 中的
            Delete relationAttendDelete = new Delete(Bytes.toBytes(attend));
            relationAttendDelete.addColumn(
                    Bytes.toBytes(RELATION_TABLE_CF_2),
                    Bytes.toBytes(uid)
            );
            relationAttendDeletes.add(relationAttendDelete);

            // 3. 从 inbox 表中删除 uid 中的每个 attends 的所有数据
            inboxUidDelete.addColumns(
                    Bytes.toBytes(INBOX_TABLE_CF),
                    Bytes.toBytes(attend)
            );
        }

        if (!relationUidDelete.isEmpty()) {
            relationTable.delete(relationUidDelete);
            relationTable.delete(relationAttendDeletes);
            inboxTable.delete(inboxUidDelete);
        }


        relationTable.close();
        inboxTable.close();
        connection.close();
    }

    /**
     * 获取某个用户的初始化页面
     * 1. 从 inbox 获取 uid 行
     * 2. 遍历这一行的每一个列，即每一个 attend
     * 3. 返回每一个 attend 的微博内容 rowkey
     * 4. 从 content 表中，根据 rowkey 获取微博内容
     */
    public static void getInit(String uid) throws IOException {
        Connection connection = ConnectionFactory.createConnection(CONF);

        Table inboxTable = connection.getTable(TableName.valueOf(INBOX_TABLE));
        Table contentTable = connection.getTable(TableName.valueOf(CONTENT_TABLE));

        // 1. 从 inbox 获取 uid 行
        Get inboxUidGet = new Get(Bytes.toBytes(uid));
        inboxUidGet.setMaxVersions(INBOX_TABLE_VERSIONS);
        Result inboxUidResult = inboxTable.get(inboxUidGet);

        // 2. 遍历每一个列，即每一个 attend
        for (Cell cell : inboxUidResult.rawCells()) {
            // 3. 返回每一个 attend 的微博内容 rowkey
            // 4. 从 content 表中，根据 rowkey 获取微博内容
            Get contentGet = new Get(CellUtil.cloneValue(cell));

            Result connectResult = contentTable.get(contentGet);
            for (Cell connectCell : connectResult.rawCells()) {
                System.out.println(
                        "rowKey="+ Bytes.toString(CellUtil.cloneRow(connectCell)) +
                        " , value="+Bytes.toString(CellUtil.cloneValue(connectCell))
                );
            }
        }

        inboxTable.close();
        contentTable.close();
        connection.close();
    }

    /** 获取某个用户的所有微博详情（使用过滤器获取）
     *
     * 1. 获取 targetUid 在 content 表中的数据
     * @param uid 发送请求的用户id
     * @param targetUid 需要查看的用户id
     */
    public static void getDetail(String uid, String targetUid) throws IOException {
        Connection connection = ConnectionFactory.createConnection(CONF);
        Table contentTable = connection.getTable(TableName.valueOf(CONTENT_TABLE));

        // 1. 创建过滤器
        Scan scan = new Scan();
        // 检索包含 xxx_ 的数据
        RowFilter rowFilter = new RowFilter(
                CompareFilter.CompareOp.EQUAL,
                new SubstringComparator(targetUid + "_")
        );
        scan.setFilter(rowFilter);
        
        // 2. 获取数据
        ResultScanner scanner = contentTable.getScanner(scan);
        for (Result row : scanner) {
            for (Cell cell : row.rawCells()) {
                System.out.println(
                        "rowkey=" + Bytes.toString(CellUtil.cloneRow(cell)) +
                        ", family=" + Bytes.toString(CellUtil.cloneFamily(cell)) +
                        ", qualifier=" + Bytes.toString(CellUtil.cloneQualifier(cell)) +
                        ", value=" + Bytes.toString(CellUtil.cloneValue(cell))
                );
            }
        }

        contentTable.close();
        connection.close();
    }
}
