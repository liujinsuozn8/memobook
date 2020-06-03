const {resolve} = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')
module.exports = {
    // 两种配置单入口的方式
    entry: './src/js/index.js',
    // entry:{
    //     'main':'./src/js/index.js',
    // },
    output:{
        path: resolve(__dirname, 'build'),
        filename: 'js/[name].[contenthash:10].js'
    },
    plugins:[
        new HtmlWebpackPlugin({
            template: './src/hello.html'
        })
    ],

    mode:'production',

    optimization:{
        splitChunks:{
            chunks: 'all'
        }
    },
}