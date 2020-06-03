package com.ljs.learn.pattern.flyweight.improve;

import java.util.HashMap;
import java.util.Map;

// 网站工厂类，根据需要返回一个网站实例
public class WebSiteFactory {
    // 集合，充当池
    private Map<String, ConcreteWebSite> pool = new HashMap<>();

    // 根据网站类型，返回一个网站实例
    // 如果池中有，则直接返回；如果池中没有，则创建一个实例，并保存到pool
    public WebSite getWebSiteCategory(String type){
        if (!pool.containsKey(type)){
            pool.put(type, new ConcreteWebSite(type));
        }

        return pool.get(type);
    }

    public int getWebSiteCount(){
        return pool.size();
    }
}
