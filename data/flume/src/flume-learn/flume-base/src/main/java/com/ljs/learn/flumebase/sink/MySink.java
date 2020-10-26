package com.ljs.learn.flumebase.sink;

import org.apache.flume.*;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.plaf.nimbus.State;

public class MySink extends AbstractSink implements Configurable {
    // 定义前缀、后缀
    private String prefix;
    private String suffix;

    // 获取 logger 对象
    private Logger logger = LoggerFactory.getLogger(MySink.class);

    @Override
    public void configure(Context context) {
        prefix = context.getString("prefix");
        suffix = context.getString("suffix", "xxxx");
    }

    @Override
    public Status process() throws EventDeliveryException {
        Status status = null;

        // 1. 获取 channel
        Channel channel = getChannel();

        // 2. 从 channel 获取事务及数据
        Transaction transaction = channel.getTransaction();

        // 3. 开启事务
        transaction.begin();

        try {
            // 4. 从 channel 获取数据
            Event event = channel.take();

            // 5. 处理 event
            // 获取数据
            if (event != null){
                String body = new String(event.getBody());
                logger.info(prefix + body + suffix);
            }

            // 6. 提交事务
            transaction.commit();

            // 7. 成功提交
            status = Status.READY;
        } catch (ChannelException e) {
            e.printStackTrace();

            // 8. 提交失败，回滚事务
            transaction.rollback();

            // 9. 返回失败状态
            status = Status.BACKOFF;
        } finally {
            // 10. 关闭事务
            transaction.close();
        }
        return status;
    }
}
