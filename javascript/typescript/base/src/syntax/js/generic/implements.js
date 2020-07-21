"use strict";
var MySqlDB = /** @class */ (function () {
    function MySqlDB() {
        console.log('MySqlDB is connected');
        this.data = {};
    }
    MySqlDB.prototype.add = function (info) {
        // 如果 id 不存在则添加 info，存在则跳过
        var infoID = info.getID();
        if (this.data.hasOwnProperty(infoID)) {
            console.log("id has been existed");
            return false;
        }
        else {
            this.data[infoID] = info;
            console.log("info has been add");
            return true;
        }
    };
    MySqlDB.prototype.update = function (info) {
        // 如果 id 存在则更新 info
        var infoID = info.getID();
        if (this.data.hasOwnProperty(infoID)) {
            this.data[infoID] = info;
            console.log("info has been update");
            return true;
        }
        else {
            console.log("id is not exist");
            return false;
        }
    };
    MySqlDB.prototype.delete = function (id) {
        // 如果 id 存在则删除
        if (this.data.hasOwnProperty(id)) {
            delete this.data[id];
            console.log("info has been deleted");
            return true;
        }
        else {
            console.log("id is not exist");
            return false;
        }
    };
    MySqlDB.prototype.get = function (id) {
        // 如果 id 存在则返回对应的 info，不存在则返回 null
        if (this.data.hasOwnProperty(id)) {
            return this.data[id];
        }
        else {
            return null;
        }
    };
    MySqlDB.prototype.show = function () {
        for (var k in this.data) {
            console.log("key=" + k + ", info=" + this.data[k]);
        }
    };
    return MySqlDB;
}());
var User = /** @class */ (function () {
    function User() {
        // 将id设为1--20的随机数
        this.id = Math.floor((Math.random() * 20) + 1) + '';
    }
    User.prototype.getID = function () {
        return this.id;
    };
    User.prototype.toString = function () {
        return "User{name=" + this.username + ", address=" + this.address + "}";
    };
    return User;
}());
// 创建两个用户
var user01 = new User();
user01.username = 'testUser01';
user01.address = 'address01';
var user02 = new User();
user02.username = 'testUser02';
user02.address = 'address01';
// 创建DB实例时，固定类型
var mysqldb = new MySqlDB();
// 添加数据
mysqldb.add(user01);
mysqldb.add(user02);
// 打印数据
mysqldb.show();
// 删除数据
mysqldb.delete(user01.id);
console.log(user01);
// 打印数据
mysqldb.show();
