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
(0, _module.bar)();
(0, _module2.foo)();
(0, _module2.bar)();
console.log(_module2.obj);
_module4.default.foo();
module4.fun();
module4.fun2();
