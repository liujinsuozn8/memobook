"use strict";
// 1. 不设置类型，直接使用dom对象设置样式属性
// let box = document.getElementById('box');
// box.style.color = 'red';
/*
    页面可以正常显示，但是 ts编译器会报错:
    - error TS2531: Object is possibly 'null'.
*/
// 2. 将box设置为 (js中的) Object类型，但是ts中没有Object类型
// let box:Object = document.getElementById('box');
/*
    会产生异常
    - error TS2322: Type 'HTMLElement | null' is not assignable to type 'Object'.
    Type 'null' is not assignable to type 'Object'.
*/
// 3. 将dom对象的类型型设置为 any，编译正常，页面正常显示
var box = document.getElementById('box');
box.style.color = 'red';
