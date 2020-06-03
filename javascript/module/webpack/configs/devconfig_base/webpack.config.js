const { resolve } = require("path");
const HtmlWebpackPlugin = require('html-webpack-plugin')

module.exports={
    entry:'./src/js/index.js',
    output:{
        path: resolve( __dirname, 'build'),
        filename: 'js/built.js'
    },

    module: {
        rules: [
            { // css
                test: /\.css$/,
                use: [
                    'style-loader',
                    'css-loader',
                ]
            },

            { // less
                test:/\.less$/,
                use:[
                    'style-loader',
                    'css-loader',
                    'less-loader',
                ]
            },

            { // img
                test: /\.(jpg|png|gif)$/,
                loader: "url-loader",
                options: {
                    limit: 8 * 1024,
                    esModule: false,
                    name: "[hash:10].[ext]",
                    outputPath: "img"
                }
            },

            { // html-img
                test: /\.html$/,
                loader: "html-loader",
            },

            { // others
                exclude: /\.(html|css|js|less|json|jpg|png|gif)$/,
                loader: "file-loader",
                options:{
                    outputPath: "media"
                }
            },
        ]
    },

    plugins: [
        new HtmlWebpackPlugin({
            template: './src/hello.html'
        }),
    ],

    mode: 'development',

    devServer:{
        contentBase: resolve(__dirname, "build"),
        compress: true,
        port: 8888,
        // open: true,
    }
}