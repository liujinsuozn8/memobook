package com.ljs.learn.hbasemr.file2table;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class FileToTableDriver implements Tool {
    private Configuration configuration;

    @Override
    public int run(String[] args) throws Exception {
        // 1. 获取job对象
        Job job = Job.getInstance(getConf());

        // 2. 设置 Driver 的 jar 的存储位置（通过反射）
        job.setJarByClass(FileToTableDriver.class);

        // 3. 关联 Mapper、Reducer
        job.setMapperClass(FileToTableMapper.class);

        // 4. 设置 Mapper 阶段输出的数据的 key、value类型
        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(Text.class);

        // 5. 设置Reducer
        TableMapReduceUtil.initTableReducerJob(args[1], FileToTableReducer.class, job);

        // 6. 设置输入路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));

        // 7. 提交 job，输出信息
        boolean result = job.waitForCompletion(true);

        // 8. 输出执行是否成功
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

    // arg[0]=filepath arg[1]=tablename
    // yarn jar hbase-base-1.0-SNAPSHOT.jar com.ljs.learn.hbasemr.file2table.FileToTableDriver /test/file.txt student
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.set("hbase.zookeeper.quorum", "zk01");
        try {
            int run = ToolRunner.run(configuration, new FileToTableDriver(), args);
            System.exit(run);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
