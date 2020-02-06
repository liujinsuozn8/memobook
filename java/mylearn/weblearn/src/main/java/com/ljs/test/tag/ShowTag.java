package com.ljs.test.tag;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTag;
import java.io.IOException;

public class ShowTag implements SimpleTag {
    private PageContext pageContext;

    // 添加两个标签属性
    private String value;
    private String count;

    public void setValue(String value) {
        this.value = value;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public void doTag() throws JspException, IOException {
        // 打印两个参数
        System.out.println("value = " + value);
        System.out.println("count = " + count);

        // 循环打印value
        int countInt = Integer.parseInt(count);

        // 从pageContext中获取out对象，并循环打印value
        JspWriter out = pageContext.getOut();
        for (int i = 0; i < countInt; i++) {
            out.println(i + " : " + value);
            out.println("<br>");
        }
    }

    @Override
    public void setParent(JspTag jspTag) {

    }

    @Override
    public JspTag getParent() {
        return null;
    }

    @Override
    public void setJspContext(JspContext jspContext) {
        // 保存pageContext对象
        this.pageContext = (PageContext) jspContext;
    }

    @Override
    public void setJspBody(JspFragment jspFragment) {

    }
}
