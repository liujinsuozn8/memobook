package com.ljs.test.listener.introduct;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;
import java.io.Serializable;
import java.util.Date;

public class Customer2 implements HttpSessionActivationListener, Serializable {
    private static final long serialVersionUID = -1922108588750068344L;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // HttpSessionActivationListener接口实现
    @Override
    public void sessionWillPassivate(HttpSessionEvent se) {
        System.out.println("Customer2.sessionWillPassivate");
    }

    @Override
    public void sessionDidActivate(HttpSessionEvent se) {
        System.out.println("Customer2.sessionDidActivate");
    }
}
