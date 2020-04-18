package com.ljs.learn.mybatis.dynamicSql;

import com.ljs.learn.mybatis.common.bean04.Blog;
import com.ljs.learn.mybatis.common.utils.IDUtils;
import com.ljs.learn.mybatis.common.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.*;

public class BlogMapperTest {
    @Test
    public void testAdd(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        Blog blog = new Blog();
        blog.setId(IDUtils.getUUID());
        blog.setAuthor("mine");
        blog.setTitle("a01");
        blog.setViews(500);
        blog.setCreateTime(new Date());
        mapper.addBlog(blog);

        blog.setId(IDUtils.getUUID());
        blog.setTitle("a02");
        mapper.addBlog(blog);

        blog.setId(IDUtils.getUUID());
        blog.setTitle("a03");
        mapper.addBlog(blog);

        blog.setId(IDUtils.getUUID());
        blog.setTitle("a04");
        mapper.addBlog(blog);

        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testIf(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Map<String,Object> map =  new HashMap<>();

        // 1. 没有条件
        List<Blog> result = mapper.getBlogsIf(map);
        System.out.println(result);

        // 2. 1个条件
        map.put("title", "a01");
        result = mapper.getBlogsIf(map);
        System.out.println(result);

        // 3. 2个条件
        map.put("title", "a02");
        map.put("author", "mine");
        result = mapper.getBlogsIf(map);
        System.out.println(result);

        sqlSession.close();
    }

    @Test
    public void testWhere(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Map<String, Object> map =  new HashMap<>();

        // 1. 没有条件
        List<Blog> result = mapper.getBlogsWhere(map);
        System.out.println(result);

        // 2. 只输入第二个条件
        map.put("author", "mine");
        result = mapper.getBlogsWhere(map);
        System.out.println(result);

        // 3. 输入2个条件
        map.put("title", "a02");
        map.put("author", "mine");
        result = mapper.getBlogsWhere(map);
        System.out.println(result);

        sqlSession.close();
    }

    @Test
    public void testChoose(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        // 1. 只输入第二个条件
        Map<String,Object> map01 =  new HashMap<>();
        map01.put("author", "mine");
        List<Blog> result01 = mapper.getBlogsChoose(map01);
        System.out.println("result01 = " + result01);

        // 2. 输入2个条件
        Map<String,Object> map02 =  new HashMap<>();
        map02.put("title", "a02");
        map02.put("author", "mine");
        List<Blog> result02 = mapper.getBlogsChoose(map02);
        System.out.println("result02 = " + result02);

        // 3. 只输入第3个条件
        Map<String,Object> map03 =  new HashMap<>();
        map03.put("views", 500);
        List<Blog> result03 = mapper.getBlogsChoose(map03);
        System.out.println("result02 = " + result02);
        System.out.println(result03);

        sqlSession.close();
    }

    @Test
    public void tetstUpdate(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        Map<String, Object> map = new HashMap<>();
        map.put("title", "a02");
        map.put("createTime", new Date());
        map.put("views", 600);

        int i = mapper.updateBlog(map);

        if (i > 0){
            sqlSession.commit();
        }

        sqlSession.close();
    }

    @Test
    public void testForeach(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        List<String> titleList = new ArrayList<>(3);
        titleList.add("a01");
        titleList.add("a03");
        titleList.add("a04");

        Map<String, Object> map = new HashMap<>();
        map.put("titleList", titleList);
        List<Blog> result = mapper.getBlogsForeach(map);
        System.out.println(result);

        sqlSession.close();
    }

}
