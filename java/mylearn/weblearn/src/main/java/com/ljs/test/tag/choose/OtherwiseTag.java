package com.ljs.test.tag.choose;

import javax.servlet.jsp.JspException;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class OtherwiseTag extends SimpleTagSupport {
    @Override
    public void doTag() throws JspException, IOException {
        // 获取父标签的引用
        ChooseTag parent = (ChooseTag) getParent();
        // 如果父标签仍然未匹配成功，则输出子标签的标签体内容
        if (parent.getFlg()){
            getJspBody().invoke(null);
        }
    }
}
