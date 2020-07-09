"use strict";
var p;
p = (function () {
    throw new Error('test err');
})();
console.log(p);
