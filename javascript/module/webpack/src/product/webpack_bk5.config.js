const { resolve } = require("path");
// 3.4  html 打包插件
const HtmlWebpackPlugin = require('html-webpack-plugin')
// 7.1 css提取配置
// css提取插件
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
// 7.3 css压缩
const OptimizeCssAssetsWebpackPlugin = require('optimize-css-assets-webpack-plugin')
// 15.1 PWA
const WorkboxWebpackPlugin = require('workbox-webpack-plugin')

// 设置nodejs的环境变量。可影响:
// 7.2 css兼容性配置，postcss: 读取哪种 browserslist 配置
// process.env.NODE_ENV = "development"

// 7. 定义通用 css 处理loader
const commonCssLoader = [
    // 7.1 css提取配置
    // 替换 style-loader，从打包后的js文件中提取css成单独文件
    MiniCssExtractPlugin.loader,
    'css-loader',
    // 7.2 css兼容性配置
    // 使用 postcss 执行兼容性处理
    // 使用 postcss 的默认配置
    // 'postcss-loader'
    // 修改 postcss 的默认配置
    {
        loader: 'postcss-loader',
        options: {
            ident: 'postcss',
            plugins: ()=> [
                // process 的插件
                require('postcss-preset-env')()
            ]
        }
    },
]

module.exports={
    // 1. 入口
    // entry:'./src/js/index.js',
    // 14.1 文件分割配置
    entry:{
        main: './src/js/index.js'
    },

    // 2. 输出
    output:{
        // 输出目录，最好使用绝对路径
        path: resolve( __dirname, 'build'),
        // 指定输出子目录和输出文件名
        // 12. 缓存穿透策略，在文件名中添加一个 hash值
        // 14.2 文件分割配置：[name]
        filename: 'js/[name].[contenthash:10].js'
    },

    // 3. loader
    module: {
        rules: [
            // 8. eslint 语法检查
            {
                test: /\.js$/,
                // 排除第三方代码的检查
                exclude: /node_modules/,
                loader: 'eslint-loader',
                // 需要在 package.json 中指定检查规则
                // 自动修复eslint语法问题，将直接在原代码中进行修改
                options: {
                    fix: true,
                },
                // 提升 eslint 处理的优先级
                enforce: 'pre',
            },

            // 11. 对于每个文件，loader匹配成功之后就停止
            {
                oneOf:[
                    // 3.1 处理css的loader
                    {
                        // 表示匹配哪些文件
                        test: /\.css$/,
                        use: [...commonCssLoader,]
                        // 无法添加 outputPath 属性
                    },

                    // 3.2 处理less的loader
                    {
                        test:/\.less$/,
                        use:[...commonCssLoader, 'less-loader']
                        // 无法添加 outputPath 属性
                    },

                    // 3.3 处理css，js中引用的图片
                    {
                        // 配置处理那些类型的图片
                        test: /\.(jpg|png|gif)$/,
                        // 使用一个loader进行处理
                        loader: "url-loader",
                        options: {
                            // 当图片小于 8kb 时，由base64处理
                            // 优点：减少请求数量，图片体积会变大
                            // 一般只针对 8kb - 12kb 的图片进行处理
                            limit: 8 * 1024,
                            // url-loader 默认使用es6规范，html-loader引入图片是commonjs规范
                            // 解析时会出现问题：[object Module]
                            // 关闭 url-loader 的es6规范，使用commonjs规范解析
                            esModule: false,
                            // 为图片重命名
                            // [hash:10]: 取hash值的前10位
                            // [ext]: 使用文件原来的扩展名
                            name: "[hash:10].[ext]",
                            outputPath: "img"
                        }
                    },

                    // 3.4 解析html中的图片引用，并交给url-loader处理
                    {
                        test: /\.html$/,
                        // 解析html中的图片引用
                        loader: "html-loader",
                        // 无法添加 outputPath 属性
                    },

                    // 3.5 打包其他资源
                    {
                        // 排除 之前所有已经被处理过的文件类型，
                        // 否则会出现异常
                        exclude: /\.(html|css|js|less|json|jpg|png|gif)$/,
                        loader: "file-loader",
                        options:{
                            outputPath: "media"
                        }
                    },

                    // 9. js 兼容性处理
                    {
                        test: /\.js$/,
                        // 排除第三方代码的检查
                        exclude: /node_modules/,
                        loader: 'babel-loader',
                        options: {
                            // 12. 缓存
                            // 开启babel缓存
                            // 在第二次构建时，会读取之前的缓存
                            cacheDirectory: true,

                            // 9.1 只能转换基本的es6语法
                            // presets: ['@babel/preset-env'],

                            // 9.2 `@babel/preset-env` + `core-js`
                            // 预设：指示babel如何做兼容性处理
                            presets: [
                                [
                                    '@babel/preset-env', // 只能转换基本的es6语法
                                    // 按需加载
                                    {
                                        useBuiltIns:'usage',
                                        corejs: {
                                            version:3
                                        },
                                        // 指定兼容性做到浏览器的哪个版本
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
        ]
    },

    // 4. plugin
    plugins: [
        // 3.4  拷贝指定html资源，并自动引入打包生成的所有资源
        new HtmlWebpackPlugin({
            template: './src/hello.html',
            // 10. html压缩配置
            minify: {
                // 移除空格
                collapseWhitespace: true,
                // 移除注释
                removeComments: true,
            }
        }),
        // 7.1  引入css提取插件
        new MiniCssExtractPlugin({
            // 默认生成 main.css
            // 可以指定生成结果的子目录与文件名
            // 12. 缓存穿透策略，在文件名中添加一个 hash值
            filename:'css/built.[contenthash:10].css'
        }),
        // 7.3 css压缩
        new OptimizeCssAssetsWebpackPlugin(),

        // 15.2 开启PWA
        new WorkboxWebpackPlugin.GenerateSW({
            // 删除旧的 serviceworker，使用最新的
            clientsClaim:true,
            // 帮助 serviceworker 快速启动
            skipWaiting:true,
        })
    ],

    // 5. mode
    // mode: 'development',
    mode: 'production',

    // 13. source-map 策略
    // devtool:'cheap-module-source-map',
    devtool:'source-map',

    // 14.3 公共引用打包
    optimization:{
        splitChunks:{
            chunks: 'all'
        }
    },
}