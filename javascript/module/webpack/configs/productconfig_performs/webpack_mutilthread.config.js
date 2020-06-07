const { resolve } = require("path");
const HtmlWebpackPlugin = require('html-webpack-plugin')
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const OptimizeCssAssetsWebpackPlugin = require('optimize-css-assets-webpack-plugin')
const WorkboxWebpackPlugin = require('workbox-webpack-plugin')
const TerserWebpackPlugin = require('terser-webpack-plugin')

// dll引入插件
// const webpack = require('webpack')
// const AddAssetHtmlWebpackPlugin = require('add-asset-html-webpack-plugin')

// css兼容性配置，postcss: 读取哪种 browserslist 配置
// process.env.NODE_ENV = "development"

const commonCssLoader = [
    MiniCssExtractPlugin.loader,
    'css-loader',
    {
        loader: 'postcss-loader',
        options: {
            ident: 'postcss',
            plugins: ()=> [
                require('postcss-preset-env')()
            ]
        }
    },
]

module.exports={
    entry:{
        main: './src/js/index.js'
    },

    output:{
        path: resolve( __dirname, 'build'),
        filename: 'js/[name].[contenthash:10].js',
        chunkFilename:'js/[name].[contenthash:10]_chunk.js',
    },

    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                loader: 'eslint-loader',
                options: {
                    fix: true,
                },
                enforce: 'pre',
            },

            {
                oneOf:[
                    {
                        test: /\.css$/,
                        use: [...commonCssLoader,]
                    },

                    {
                        test:/\.less$/,
                        use:[...commonCssLoader, 'less-loader']
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
                        exclude: /\.(html|css|js|less|json|jpg|png|gif)$/,
                        loader: "file-loader",
                        options:{
                            outputPath: "media"
                        }
                    },

                    {
                        test: /\.js$/,
                        exclude: /node_modules/,
                        use:[
                            {
                                loader: 'thread-loader',
                                options:{
                                    workers: 2, // 进程数
                                }
                            },
                            {
                                loader: 'babel-loader',
                                options: {
                                    cacheDirectory: true,

                                    presets: [
                                        [
                                            '@babel/preset-env',
                                            {
                                                useBuiltIns:'usage',
                                                corejs: {
                                                    version:3
                                                },
                                                targets: {
                                                    chrome: '60',
                                                    firefox: '60',
                                                    ie: '9'
                                                }
                                            }
                                        ]
                                    ]
                                }
                            }
                        ],
                    }
                ]
            },
        ]
    },

    plugins: [
        new HtmlWebpackPlugin({
            template: './src/hello.html',
            minify: {
                collapseWhitespace: true,
                removeComments: true,
            }
        }),
        new MiniCssExtractPlugin({
            filename:'css/built.[contenthash:10].css'
        }),
        new OptimizeCssAssetsWebpackPlugin(),

        new WorkboxWebpackPlugin.GenerateSW({
            clientsClaim:true,
            skipWaiting:true,
        }),
        // dll 引入配置
        // 1. 引入dll的映射文件
        // new webpack.DllReferencePlugin({
        //     manifest: resolve(__dirname, 'dll/manifest.json')
        // }),
        // 2. 输出dll的编译结果到打包结果中
        // new AddAssetHtmlWebpackPlugin({
        //     filepath: resolve(__dirname, 'dll/xxxx.js')
        // })
    ],

    mode: 'production',

    devtool:'source-map',

    optimization:{
        splitChunks:{
            chunks: 'all'
        },
        runtimeChunk: {
            name: enrtypoint => `runtime-${enrtypoint.name}`
        },
        minimizer:[
            new TerserWebpackPlugin({
                cache: true,
                parallel: true,
                sourceMap: true
            })
        ],
    },

    resolve:{
        alias:{
            $css: resolve(__dirname, 'src/css'),
            $json: resolve(__dirname, 'src/json'),
        },

        extensions: ['.js', '.json'],

        modules:[ resolve(__dirname, '../../node_modules'), "node_modules"]
    }
}