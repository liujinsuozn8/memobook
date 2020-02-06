package com.ljs.test.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class ReadFileTag extends SimpleTagSupport {
    private String src;

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public void doTag() throws JspException, IOException {
        //获取输入流
        PageContext pageContext = (PageContext) getJspContext();
        InputStream is = pageContext.getServletContext().getResourceAsStream(src);

        //通过转换流将字节流转换为字符流，并创建处理流
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        //按行读取并输出到html
        JspWriter out = pageContext.getOut();
        String text = null;
        while ((text = br.readLine()) != null){
            //转译 < >
            text = text.replaceAll("\\<","&lt;")
                       .replaceAll("\\>","&gt;");
            out.println(text);
            out.println("<br>");
        }

    }
}
