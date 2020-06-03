const {resolve} = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')
module.exports={
    // 1. String型入口
    entry:'./src/index.js',
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