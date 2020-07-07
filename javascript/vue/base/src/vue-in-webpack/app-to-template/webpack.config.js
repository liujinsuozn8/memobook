const {resolve} = require('path');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const OptimizeCssAssetsWebpackPlugin = require('optimize-css-assets-webpack-plugin');
const VueLoader = require('vue-loader');
// const commonCssLoader = [
//     MiniCssExtractPlugin.loader,
//     'css-loader',
//     {
//         loader: 'postcss-loader',
//         options: {
//             ident: 'postcss',
//             plugins: ()=>{
//                 require('postcss-preset-env')()
//             }
//         }
//     }
// ]

// // 生成模式配置
// productExport = {
//     entry:{
//         main: './src/js/index.js'
//     },
//     output:{
//         path: resolve(__dirname, 'build'),
//         filename: 'js/[name].[contenthash:10].js',
//         chunkFilename: 'js/[name].[contenthash:10]_chunk.js'
//     },
//     module:{
//         rules:[
//             // {
//             //     test: /\.vue$/,
//             //     loader: 'vue-loader',
//             // },
//             {
//                 test:/\.js/,
//                 exclude: /node_modules/,
//                 loader: 'eslint-loader',
//                 options:{ fix: true},
//                 enforce: 'pre',
//             },
//             {
//                 oneOf:[
//                     {
//                         test:/\.css$/,
//                         use:[...commonCssLoader],
//                     },
//                     {
//                         test:/\.less$/,
//                         use:[...commonCssLoader, 'less-loader']
//                     },
//                     {
//                         test:/\.(jpg|png|gif)$/,
//                         loader: 'url-loader',
//                         options:{
//                             limit: 8 * 1024,
//                             name: '[hash:10].[ext]',
//                             outputPath: 'img',
//                             esModule: false,
//                         }
//                     },
//                     {
//                         test:/\.html$/,
//                         loader: 'html-loader',
//                     },
//                     {
//                         exclude: /\.(html|js|json|css|less|jpg|gif|png|vue)$/,
//                         loader: 'file-loader',
//                         options:{
//                             outputPath: 'media'
//                         }
//                     },
//                     // js 兼容性处理
//                     {
//                         test:/\.js$/,
//                         exclude: /node_modules/,
//                         loader: 'babel-loader',
//                         options:{
//                             cacheDirectory:true,
//                             presets:[
//                                 [
//                                     '@babel/preset-env',
//                                     {
//                                         useBuiltIns:'usage',
//                                         corejs:{version : 3},
//                                         targets:{
//                                             chrome: '60',
//                                             firefox: '60',
//                                             ie:'9',
//                                         }
//                                     }
//                                 ]
//                             ]
//                         }
//                     }
//                 ]
//             },
//             {
//                 test: /\.vue$/,
//                 loader: 'vue-loader',
//             },
//         ]
//     },
//     plugins:[
//         new HtmlWebpackPlugin({
//             template: './src/hello.html',
//             minify: {
//                 collapseWhitespace: true,
//                 removeComments: true,
//             }
//         }),
//         new MiniCssExtractPlugin({
//             filename: 'css/built.[contenthash:10].css'
//         }),
//         new OptimizeCssAssetsWebpackPlugin(),
//         new VueLoader.VueLoaderPlugin()
//     ],
//     mode:'production',
//     optimization:{
//         splitChunks:{chunks:'all'},
//     },
//     // 8. source-map 配置
//     devtool: 'source-map',
//     resolve: {
//         alias: {
//             // 指定使用 `import Vue from 'vue'` 时，引入哪一个版本
//             vue$: 'vue/dist/vue.esm.js',
//         }
//     }
// }

// 开发模式配置
devExport={
    entry:'./src/js/index.js',
    output:{
        path: resolve( __dirname, 'build'),
        filename: 'js/built.js'
    },

    module: {
        rules: [
            {
                oneOf:[
                    {
                        test: /\.css$/,
                        use: ['style-loader', 'css-loader']
                    },
                    {
                        test:/\.less$/,
                        use:[
                            'style-loader',
                            'css-loader',
                            'less-loader',
                        ]
                    },
                    {
                        test: /\.(jpg|png|gif)$/,
                        loader: "url-loader",
                        options: {
                            limit: 8 * 1024,
                            esModule: false,
                            name: "[hash:10].[ext]",
                            outputPath: "img"
                        }
                    },
                    {
                        test: /\.html$/,
                        loader: "html-loader",
                    },
                    {
                        exclude: /\.(html|css|js|less|json|jpg|png|gif|vue)$/,
                        loader: "file-loader",
                        options:{
                            outputPath: "media"
                        }
                    }
                ]
            },
            {
                test: /\.vue$/,
                loader: 'vue-loader',
            },
        ]
    },

    plugins: [
        new HtmlWebpackPlugin({
            template: './src/hello.html'
        }),
        new VueLoader.VueLoaderPlugin()
    ],

    mode: 'development',

    devtool: 'eval-source-map',

    resolve: {
        alias: {
            // 指定使用 `import Vue from 'vue'` 时，引入哪一个版本
            vue$: 'vue/dist/vue.esm.js',
        }
    }
}

module.exports = devExport;