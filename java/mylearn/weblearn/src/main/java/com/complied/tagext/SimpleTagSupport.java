package javax.servlet.jsp.tagext;

import java.io.IOException;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;

public class SimpleTagSupport implements SimpleTag {
    private JspTag parentTag;
    private JspContext jspContext;
    private JspFragment jspBody;

    public SimpleTagSupport() {
    }

    public void doTag() throws JspException, IOException {
    }

    public void setParent(JspTag parent) {
        this.parentTag = parent;
    }

    public JspTag getParent() {
        return this.parentTag;
    }

    public void setJspContext(JspContext pc) {
        this.jspContext = pc;
    }

    protected JspContext getJspContext() {
        return this.jspContext;
    }

    public void setJspBody(JspFragment jspBody) {
        this.jspBody = jspBody;
    }

    protected JspFragment getJspBody() {
        return this.jspBody;
    }

    public static final JspTag findAncestorWithClass(JspTag from, Class<?> klass) {
        boolean isInterface = false;
        if (from == null || klass == null || !JspTag.class.isAssignableFrom(klass) && !(isInterface = klass.isInterface())) {
            return null;
        } else {
            while(true) {
                JspTag parent = null;
                if (from instanceof SimpleTag) {
                    parent = ((SimpleTag)from).getParent();
                } else if (from instanceof Tag) {
                    parent = ((Tag)from).getParent();
                }

                if (parent == null) {
                    return null;
                }

                if (parent instanceof TagAdapter) {
                    parent = ((TagAdapter)parent).getAdaptee();
                }

                if (isInterface && klass.isInstance(parent) || klass.isAssignableFrom(parent.getClass())) {
                    return (JspTag)parent;
                }

                from = parent;
            }
        }
    }
}
