package com.ljs.learn.myspringannotation.autowired.profile;

public class DevEnv implements Env {

    @Override
    public String getName() {
        return "DevEnv File";
    }
}
