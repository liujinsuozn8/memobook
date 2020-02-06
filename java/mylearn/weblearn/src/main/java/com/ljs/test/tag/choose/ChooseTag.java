package com.ljs.test.tag.choose;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/*
* Choose When Otherwise 的父标签*/
public class ChooseTag extends SimpleTagSupport {
    //如果父标签的flg=false，则表示匹配成功，否则继续匹配
    private boolean flg = true;

    public boolean getFlg() {
        return flg;
    }

    public void setFlg(boolean flg) {
        this.flg = flg;
    }

    @Override
    public void doTag() throws JspException, IOException {
        //输出标签体
        JspFragment jspBody = getJspBody();
        jspBody.invoke(null);
    }
}
