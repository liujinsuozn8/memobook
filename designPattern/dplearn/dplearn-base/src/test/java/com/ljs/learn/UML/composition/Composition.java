package com.ljs.learn.UML.composition;

public class Composition {
}

// 笔记本和屏幕是组合关系，和鼠标是聚合关系
class Laptop {
    private Mouse mouse;
    private Monitor monitor = new Monitor();
}

class Monitor {}
class Mouse{}
