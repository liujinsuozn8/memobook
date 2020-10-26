package com.ljs.learn.flumebase.source;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.PollableSource;
import org.apache.flume.conf.Configurable;
import org.apache.flume.event.SimpleEvent;
import org.apache.flume.source.AbstractSource;

public class MySource extends AbstractSource implements Configurable, PollableSource {
    // 定义全局的前缀和后缀
    private String prefix;
    private String suffix;

    @Override
    public void configure(Context context) {
        // 读取配置信息
        // 没有默认值
        prefix = context.getString("prefix");

        // 有默认值
        suffix = context.getString("suffix", "testsuf");
    }

    @Override
    public Status process() throws EventDeliveryException {
        Status status = null;

        // 1. 手动创建数据，来模拟接收数据
        try {
            for (int i = 0; i < 5; i++) {
                // 2. 将数据封装为 event
                // 创建 event 对象
                SimpleEvent event = new SimpleEvent();
                // 设置 body
                event.setBody((prefix + "---" + i + "-----"+suffix).getBytes());
                // 3. 将 event 发送到 channel
                getChannelProcessor().processEvent(event);
            }
            status = Status.READY;
        } catch (Exception e) {
            status = Status.BACKOFF;
            e.printStackTrace();
        }


        // 每2s 输出5条数据
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public long getBackOffSleepIncrement() {
        return 0;
    }

    @Override
    public long getMaxBackOffSleepInterval() {
        return 0;
    }

}