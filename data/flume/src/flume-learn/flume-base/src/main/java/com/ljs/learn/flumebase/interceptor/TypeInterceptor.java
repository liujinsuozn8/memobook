package com.ljs.learn.flumebase.interceptor;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TypeInterceptor implements Interceptor {
    // 创建一个集合来保存 event
    private List<Event> events;

    @Override
    public void initialize() {
        events = new ArrayList<>();
    }

    // 单个 event 拦截
    @Override
    public Event intercept(Event event) {
        // 1. 获取 event 中的 header
        Map<String, String> headers = event.getHeaders();

        // 2. 获取 event 中的body。需要将 []byte 转换为字符串
        String body = new String(event.getBody());

        // 3. 根据body中的数据添加头信息
        if (body.contains("hello")){
            headers.put("type", "str");
        } else {
            headers.put("type","num");
        }

        return event;
    }

    // 批量 event 拦截
    @Override
    public List<Event> intercept(List<Event> list) {
        // 1. 清空集合
        events.clear();

        // 2. 遍历 event，给每一个 event 添加头信息
        for (Event e : list) {
            events.add(intercept(e));
        }

        // 3. 返回结果
        return events;
    }

    @Override
    public void close() {

    }

    // 启动类
    public static class Builder implements Interceptor.Builder{

        @Override
        public Interceptor build() {
            return new TypeInterceptor();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
