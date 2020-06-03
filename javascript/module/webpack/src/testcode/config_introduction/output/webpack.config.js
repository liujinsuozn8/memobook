const {resolve} = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')
module.exports={
    entry: './src/index.js',

    output:{
        path: resolve(__dirname, 'build'),  // 设置所有资源输出的公共目录
        filename:'[name].js',               // 指定bundle输出的名称和目录
        publicPath: '/',                    // 设置所有资源的公共路径前缀
        chunkFilename: '[name]_chunk.js',   // 设置非入口chunk文件的命名规则
        library: '[name]_[hash:10]',        // 设置入口文件打包后，暴露给外部使用的变量名
        libraryTarget: 'window',            // 将library暴露的变量名添加到 window 对象上
    },
    plugins:[
        new HtmlWebpackPlugin()
    ],
    mode: 'development'
}