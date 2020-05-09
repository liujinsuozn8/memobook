package com.ljs.learn.myspringbootsample.config;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest req) {
        // 获取请求参数中的语言参数
        String lang = req.getParameter("l");
        Locale locale; //如果没有就使用默认的

        // 如果请求链接中携带了国际化的参数
        if (!StringUtils.isEmpty(lang)){
            // 将国际化参数分解成：国家、地区
            String[] split = lang.split("_");
            locale = new Locale(split[0], split[1]);
        }else{
            locale = Locale.getDefault();
        }

        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
