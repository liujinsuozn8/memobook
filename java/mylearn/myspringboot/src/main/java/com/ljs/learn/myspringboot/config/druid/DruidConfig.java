package com.ljs.learn.myspringboot.config.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

    // 接管配置文件，使当前类与配置文件绑定
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    // 后台监控功能
    // 以web.xml的方式配置Servlet
    // 由于SpringBoot 内置了servlet，所以没有web，
    // 需要使用ServletRegistrationBean来替代
    @Bean
    public ServletRegistrationBean statViewBean(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        // 后台登录账号密码，与web.xml中的配置类似
        Map<String, String> initParamters = new HashMap<>();
        // 1. 账号密码对应的key是固定
        initParamters.put("loginUsername", "admin");
        initParamters.put("loginPassword", "12345");
        // 2. 允许谁可以访问
        // 如果value为空字符，则任何人都可以访问
        // localhost：只有本机可以访问
        initParamters.put("allow", "localhost");
        bean.setInitParameters(initParamters);
        return bean;
    }

    @Bean
    public FilterRegistrationBean filterBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        // 添加过滤器
        bean.setFilter(new WebStatFilter());
        // 设置过滤请求
        // 可以过滤的内容参考 WebStatFilter 中的关键字
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("exclusions", "*.js，*.css,/druid/*");
        bean.setInitParameters(initParameters);
        return bean;
    }
}
