const {resolve} = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const webpack = require('webpack')
// 在打包时，将某个文件输出到打包结果中，并在html中自动引入该文件
const AddAssetHtmlWebpackPlugin = require('add-asset-html-webpack-plugin')

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
        }),

        /*
            告诉webpack哪些库不参与打包，并且引用时的名称需要改成映射文件中的名称
            但是这些不参与打包的内容不会输出到打包结果中，所以页面还无法使用这些内容

            还需要使用: add-asset-html-webpack-plugin 插件
        */
        new webpack.DllReferencePlugin({
            manifest: resolve(__dirname, 'dll/manifest.json')
        }),
        // 在打包时，将某个文件输出到打包结果中，并在html中自动引入该文件
        new AddAssetHtmlWebpackPlugin({
            filepath: resolve(__dirname, 'dll/jquery.js')
        })
    ],
    mode:'production',
}