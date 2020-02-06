package com.ljs.test.tag.choose;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class WhenTag extends SimpleTagSupport {
    boolean test;

    public void setTest(boolean test) {
        this.test = test;
    }

    @Override
    public void doTag() throws JspException, IOException {
        // 获取父标签的引用
        ChooseTag parentTag = (ChooseTag) getParent();

        // 父标签中的变量还处于未匹配的状态
        if (parentTag.getFlg()) {
            // 当前子标签的状态是true，则表示条件匹配成功，输出子标签的标签体内容
            // 并将父标签中的匹配状态设为false，阻止后续的匹配
            if (test) {
                getJspBody().invoke(null);
                parentTag.setFlg(false);
            }
        }
    }
}
