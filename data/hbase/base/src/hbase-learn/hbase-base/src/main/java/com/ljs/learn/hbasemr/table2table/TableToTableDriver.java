package com.ljs.learn.hbasemr.table2table;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class TableToTableDriver implements Tool {
    private Configuration configuration;

    @Override
    public int run(String[] args) throws Exception {
        // 1. 获取job对象
        Job job = Job.getInstance(getConf());

        // 2. 设置 Driver 的 jar 的存储位置（通过反射）
        job.setJarByClass(TableToTableDriver.class);

        // 3. 关联 Mapper
        TableMapReduceUtil.initTableMapperJob(
                args[0],
                new Scan(),
                TableToTableMapper.class,
                ImmutableBytesWritable.class,
                Put.class,
                job
        );

        // 4. 设置 Reducer
        TableMapReduceUtil.initTableReducerJob(
                args[1],
                TableToTableReducer.class,
                job
        );

        // 5. 提交 job，输出信息
        boolean result = job.waitForCompletion(true);

        // 6. 输出执行是否成功
        return result ? 0 : 1;
    }

    @Override
    public void setConf(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Configuration getConf() {
        return configuration;
    }

    // arg[0]=fromTable arg[1]=toTable
    // yarn jar hbase-base-1.0-SNAPSHOT.jar com.ljs.learn.hbasemr.table2table.TableToTableDriver student student2
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.set("hbase.zookeeper.quorum", "zk01");
        try {
            int run = ToolRunner.run(configuration, new TableToTableDriver(), args);
            System.exit(run);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
