package com.ljs.test.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class MaxTag extends SimpleTagSupport {
    private String num1;
    private String num2;

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    public void setNum2(String num2) {
        this.num2 = num2;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = this.getJspContext().getOut();

        int num1Int = 0;
        int num2Int = 0;
        try {
            num1Int = Integer.parseInt(num1);
            num2Int = Integer.parseInt(num2);
            out.println("maxValue = " + (num1Int > num2Int? num1Int:num2Int));
        }catch (Exception e){
            out.println("属性的格式不正确");
        }

        out.println("<br>");
    }
}
