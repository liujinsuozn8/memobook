package com.ljs.learn.myspringannotation.autowired.profile;

public class TestEnv implements Env {
    @Override
    public String getName() {
        return "TestEnv File";
    }
}
