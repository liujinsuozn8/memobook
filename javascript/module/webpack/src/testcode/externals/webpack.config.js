const {resolve} = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')

module.exports={
    entry:{
        'main':'./src/js/index.js'
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
    mode:'production',

    // externals配置，打包时忽略某些包
    externals:{
        jquery: "jQuery"
    }
}