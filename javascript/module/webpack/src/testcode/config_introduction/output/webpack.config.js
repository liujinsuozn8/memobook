const {resolve} = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')
module.exports={
    entry: './src/index.js',

    plugins:[
        new HtmlWebpackPlugin()
    ],
    mode: 'development'
}