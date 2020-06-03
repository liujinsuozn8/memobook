const {resolve} = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')
module.exports={
    // 3. Object型入口
    // 3.1 基本用法: 一个key 一个入口
    // entry: {
    //     index: './src/index.js',
    //     add: './src/add.js'
    // },
    // 3.2 特殊用法: 一个key 多个入口
    entry: {
        // 多个入口，最终都会被编译到第一个js中
        index: ['./src/index.js', './src/substract.js'],
        add: './src/add.js'
    },

    output:{
        path: resolve(__dirname, 'build'),
        filename:'[name].js'
    },
    plugins:[
        // 不指定 template，使用插件自动生成的空html
        new HtmlWebpackPlugin()
    ],
    mode: 'development'
}