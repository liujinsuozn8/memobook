package com.ljs.learn.mybatis.mapperXml.paging;

import com.ljs.learn.mybatis.common.bean.User02;

import java.util.List;
import java.util.Map;

public interface UserMapper13 {
    // 通过sql完成分页
    List<User02> getUserByLimit(Map<String, Object> map);

    // 使用RowsBounds来做分页
    List<User02> getUserByRowBounds();
}
