package com.ljs.test.tag;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;

public class ForeachTag extends SimpleTagSupport {
    private Collection<?> items;
    private String var;

    public void setItems(Collection<?> items) {
        this.items = items;
    }

    public void setVar(String var) {
        this.var = var;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspFragment jspBody = getJspBody();
        JspContext pc = getJspContext();

        for (Object item : items) {
            //将元素保存到域对象中
            pc.setAttribute(var, item);

            //解析标签体内容，并打印到html
            jspBody.invoke(null);
        }
    }
}
