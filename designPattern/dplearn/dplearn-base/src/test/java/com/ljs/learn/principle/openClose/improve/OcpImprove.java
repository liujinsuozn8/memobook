package com.ljs.learn.principle.openClose.improve;


public class OcpImprove {
    public static void main(String[] args) {
        GraphicEditor graphicEditor = new GraphicEditor();

        graphicEditor.drawShape(new Rectangle());
        graphicEditor.drawShape(new Circle());

        graphicEditor.drawShape(new Triangle());

    }
}

// 创建抽象类
abstract class Shape{
    public abstract void draw();
}

// 个图形分别实现绘制方法
class Rectangle extends Shape {
    @Override
    public void draw() {
        System.out.println("Rectangle");
    }
}

class Circle extends Shape {
    @Override
    public void draw() {
        System.out.println("Circle");
    }
}

//如果新增了一个三角形，则只需要添加图形类即可，不需要修改绘图类
class Triangle extends Shape {
    @Override
    public void draw() {
        System.out.println("Triangle");
    }
}

// 一个用于绘图的类
class GraphicEditor{
    //根据Shape的值，来绘制不通过的图形
    public void drawShape(Shape s) {
        s.draw();
    }
}