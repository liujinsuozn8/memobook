package com.ljs.learn.principle.openClose.origian;


public class OcpOrigian {
    public static void main(String[] args) {
        GraphicEditor graphicEditor = new GraphicEditor();

        graphicEditor.drawShape(new Rectangle());
        graphicEditor.drawShape(new Circle());

        graphicEditor.drawShape(new Triangle());

    }
}

class Shape{
    int m_type;
}

class Rectangle extends Shape{
    public Rectangle() {
        super.m_type = 1;
    }
}

class Circle extends Shape {
    public Circle() {
        super.m_type = 2;
    }
}

// 新增一个三角形
class Triangle extends Shape{
    public Triangle() {
        super.m_type = 3;
    }
}

// 一个用于绘图的类
class GraphicEditor{
    //根据Shape的值，来绘制不通过的图形
    public void drawShape(Shape s){
        if (s.m_type == 1)
            drawRectangle(s);
        else if (s.m_type == 2)
            drawCircle(s);
        //如果新增了一个三角形，则需要添加判断逻辑
        else if (s.m_type == 3)
            drawTriangle(s);
    }

    public void drawRectangle(Shape r){
        System.out.println("Rectangle");
    }

    public void drawCircle(Shape r){
        System.out.println("Circle");
    }

    //如果新增了一个三角形，则需要单独添加处理方法
    public void drawTriangle(Shape s){
        System.out.println("Triangle");
    }
}