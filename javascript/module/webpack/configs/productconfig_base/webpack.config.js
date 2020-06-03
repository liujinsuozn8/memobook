const { resolve } = require("path");
const HtmlWebpackPlugin = require('html-webpack-plugin')
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const OptimizeCssAssetsWebpackPlugin = require('optimize-css-assets-webpack-plugin')

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
    entry:'./src/js/index.js',
    output:{
        path: resolve( __dirname, 'build'),
        filename: 'js/built.js'
    },

    module: {
        rules: [
            { // css
                test: /\.css$/,
                use: [...commonCssLoader,]
            },

            { // less
                test:/\.less$/,
                use:[...commonCssLoader, 'less-loader']
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

            { // eslint
                test: /\.js$/,
                exclude: /node_modules/,
                loader: 'eslint-loader',
                options: {
                    fix: true,
                },
                enforce: 'pre',
            },

            { // js兼容性处理
                test: /\.js$/,
                exclude: /node_module/,
                loader: 'babel-loader',
                options: {
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
            filename:'css/built.css'
        }),
        new OptimizeCssAssetsWebpackPlugin(),
    ],

    mode: 'production',
}