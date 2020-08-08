function fortime(length) {
    var x = new Array(length).fill(0);  // 构造length长度的数组用于遍历

    console.time('a');
    for (let i = 0; i < length; i++) { }
    console.timeEnd('a');

    console.time('b');
    for (let i in x) { }
    console.timeEnd('b');

    console.time('c');
    for (let i of x) { }
    console.timeEnd('c');
}

fortime(10);
// a: 0.104ms
// b: 0.022ms
// c: 0.008ms

fortime(100);
// a: 0.005ms
// b: 0.018ms
// c: 0.022ms

fortime(1000);
// a: 0.015ms
// b: 0.265ms
// c: 0.065ms

fortime(10000);
// a: 0.14ms
// b: 1.147ms
// c: 3.853ms

fortime(100000);
// a: 3.406ms
// b: 7.744ms
// c: 0.141ms

fortime(1000000);
// a: 0.633ms
// b: 159.481ms
// c: 0.62ms

fortime(10000000);
// a: 5.156ms
// b: 6.407s
// c: 8.121ms

