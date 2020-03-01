package com.ljs.learn.pattern.prototype.deepCopy.type02;

import java.io.Serializable;

public class DeepCloneableTarget implements Serializable {
    private static final long serialVersionUID = 4688066927065335857L;
    String param1;
    String param2;

    public DeepCloneableTarget(String param1, String param2) {
        this.param1 = param1;
        this.param2 = param2;
    }
}
