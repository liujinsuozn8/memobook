package com.ljs.learn.myspringannotation.autowired.profile;

public class DefaultEnv implements Env {
    @Override
    public String getName() {
        return "DefaultEnv File";
    }
}
