var mySymbol = Symbol.for('mySymbol');    // 创建全局符号数据
module.exports = {          // 只导出对象
    [mySymbol]: 'abcd'
};