package com.ljs.learn.pattern.composite.base;

import java.util.ArrayList;
import java.util.List;

public class College extends OrganizationComponent {
    // 保存下一级的管理对象：系
    List<OrganizationComponent> componentList = new ArrayList<>();

    public College(String name) {
        super(name);
    }

    @Override
    public void add(OrganizationComponent node) {
        componentList.add(node);
    }

    @Override
    public void remove(OrganizationComponent node) {
        componentList.remove(node);
    }

    @Override
    public void print() {
        System.out.println("---College:" + super.getName() + "---");
        // 循环输出下一级的节点信息
        for (OrganizationComponent node : componentList) {
            node.print();
        }
    }
}
