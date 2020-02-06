package com.ljs.test.filter.sample.authority.bean;

public class Authority {
    // 显示到页面上的权限的名字
    private String displayName;

    // 权限对应的一个url
    private String url;

    public Authority(String displayName, String url) {
        this.displayName = displayName;
        this.url = url;
    }

    public Authority() {
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
