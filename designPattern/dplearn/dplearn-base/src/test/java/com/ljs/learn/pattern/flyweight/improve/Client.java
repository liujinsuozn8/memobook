package com.ljs.learn.pattern.flyweight.improve;

import org.junit.Test;

public class Client {
    @Test
    public void test01(){
        // 创建一个工厂类
        WebSiteFactory factory = new WebSiteFactory();

        // 用户需要一个以新闻形式发布的网站
        WebSite ws1 = factory.getWebSiteCategory("News");
        ws1.use(new User("aaaa"));

        // 用户需要一个以博客形式发布的网站
        WebSite ws2 = factory.getWebSiteCategory("Blogs");
        ws2.use(new User("bbb"));

        // 用户需要一个以博客形式发布的网站
        WebSite ws3 = factory.getWebSiteCategory("Blogs");
        ws3.use(new User("cccc"));

        // factory获取了三次，但是实际对象只有两个
        System.out.println(factory.getWebSiteCount());
    }
}
