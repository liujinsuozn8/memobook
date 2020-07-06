package com.ljs.learn.myspringannotation.regist.factorybean.color;

import org.springframework.beans.factory.FactoryBean;

// Spring工厂bean的实现类
public class ColorFactoryBean implements FactoryBean<Color> {

    // 返回一个Color对象
    @Override
    public Color getObject() throws Exception {
        return new Color("red");
    }

    // 返回对象类型
    @Override
    public Class<?> getObjectType() {
        return Color.class;
    }

    // 设置为单例
    @Override
    public boolean isSingleton() {
        // return false;
        return true;
    }
}
