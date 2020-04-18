package com.ljs.learn.mybatis.dynamicSql;

import com.ljs.learn.mybatis.common.bean04.Blog;

import java.util.List;
import java.util.Map;

public interface BlogMapper {
    int addBlog(Blog blog);
    List<Blog> getBlogsIf(Map map);
    List<Blog> getBlogsWhere(Map map);
    List<Blog> getBlogsChoose(Map map);
    List<Blog> getBlogsForeach(Map map);

    int updateBlog(Map map);

}
