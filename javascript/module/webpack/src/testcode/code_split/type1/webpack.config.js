const {resolve} = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')
module.exports = {
    // 配置多入口
    entry:{
        'main':'./src/js/index.js',
        'test':'./src/js/test.js'
    },
    output:{
        path: resolve(__dirname, 'build'),
        filename: 'js/[name].[contenthash:10].js'
    },
    plugins:[
        new HtmlWebpackPlugin({
            template: './src/hello.html'
        })
    ],
    mode:'development'
}