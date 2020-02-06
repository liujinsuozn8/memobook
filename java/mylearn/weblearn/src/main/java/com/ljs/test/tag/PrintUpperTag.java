package com.ljs.test.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

/*
* 获取标签体的内容然后转换成大写并输出time次*/
public class PrintUpperTag extends SimpleTagSupport {
    String time;

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();

        int timeInt = 0;
        try {
            timeInt = Integer.parseInt(time);
        }catch(Exception e){
            out.println("time is not int");
            return;
        }

        JspFragment jspBody = getJspBody();
        StringWriter sw = new StringWriter();

        // 获取标签体中的内容
        jspBody.invoke(sw);

        String text = sw.toString().toUpperCase();

        for (int i = 0; i < timeInt; i++) {
            out.println(i + " : " + text);
            out.println("<br>");
        }
    }
}
