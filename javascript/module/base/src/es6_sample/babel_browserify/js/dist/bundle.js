(function(){function r(e,n,t){function o(i,f){if(!n[i]){if(!e[i]){var c="function"==typeof require&&require;if(!f&&c)return c(i,!0);if(u)return u(i,!0);var a=new Error("Cannot find module '"+i+"'");throw a.code="MODULE_NOT_FOUND",a}var p=n[i]={exports:{}};e[i][0].call(p.exports,function(r){var n=e[i][1][r];return o(n||r)},p,p.exports,r,e,n,t)}return n[i].exports}for(var u="function"==typeof require&&require,i=0;i<t.length;i++)o(t[i]);return o}return r})()({1:[function(require,module,exports){
"use strict";

var _module = require("./module1");

var _module2 = require("./module2");

var _module3 = require("./module3");

var _module4 = _interopRequireDefault(_module3);

var _module5 = require("./module4");

var module4 = _interopRequireWildcard(_module5);

function _interopRequireWildcard(obj) { if (obj && obj.__esModule) { return obj; } else { var newObj = {}; if (obj != null) { for (var key in obj) { if (Object.prototype.hasOwnProperty.call(obj, key)) newObj[key] = obj[key]; } } newObj.default = obj; return newObj; } }

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

// 无法通过模块名引入
// import module1 from "./module1"
// import module2 from "./module2"

// console.log(module1,module2)

// 使用解构赋值的方式来引入
(0, _module.foo)();
// 使用别名解决命名冲突

(0, _module.bar)();
(0, _module2.foo)();
(0, _module2.bar)();
console.log(_module2.obj);
_module4.default.foo();
module4.fun();
module4.fun2();
},{"./module1":2,"./module2":3,"./module3":4,"./module4":5}],2:[function(require,module,exports){
"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});
exports.foo = foo;
exports.bar = bar;
// 多次导出
function foo() {
    console.log("this is mdoule1 foo");
}

function bar() {
    console.log("this is mdoule1 bar");
}

var arr = exports.arr = [1, 2, "eee", 55];
},{}],3:[function(require,module,exports){
"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});
// 混合导出
function foo() {
    console.log("this is module2 foo");
}

function bar() {
    console.log("this is module2 bar");
}

exports.foo = foo;
exports.bar = bar;
var obj = exports.obj = { name: "testNamestr" };
},{}],4:[function(require,module,exports){
"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});
// 默认导出
exports.default = {
    msg: "default msg",
    foo: function foo() {
        console.log("this is module3 default");
    }
};
},{}],5:[function(require,module,exports){
"use strict";

Object.defineProperty(exports, "__esModule", {
    value: true
});
// 混合导出
function fun() {
    console.log("this is module4 fun");
}

function fun2() {
    console.log("this is module4 fun2");
}

exports.fun = fun;
exports.fun2 = fun2;
},{}]},{},[1]);
