package com.ljs.test.tag;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTag;
import java.io.IOException;

public class HelloTag implements SimpleTag {
    private PageContext pageContext;

    // 执行标签体实际逻辑
    @Override
    public void doTag() throws JspException, IOException {
        System.out.println("doTag");

        //获取HttpRequest对象，并获取请求参数name
        String name = pageContext.getRequest().getParameter("name");

        //获取out对象，并在页面上打印：Hello : 请求参数name
        pageContext.getOut().println("Hello : " + name);
    }

    @Override
    public JspTag getParent() {
        System.out.println("getParent");
        return null;
    }

    @Override
    public void setParent(JspTag jspTag) {
        System.out.println("setParent");
    }

    @Override
    public void setJspContext(JspContext jspContext) {
        System.out.println(jspContext instanceof PageContext);
        this.pageContext = (PageContext) jspContext;
        System.out.println("setJspContext");
    }

    @Override
    public void setJspBody(JspFragment jspFragment) {
        System.out.println("setJspBody");
    }
}
