const { resolve } = require("path");
const HtmlWebpackPlugin = require('html-webpack-plugin')

module.exports={
    // 1. 入口，指定打包入口js文件
    // 7. 指定开启HMR后，执行热更新的的html
    entry:['./src/js/index.js', './src/hello.html'],

    // 2. 输出
    output:{
        // 输出目录，最好使用绝对路径
        path: resolve( __dirname, 'build'),
        // 指定输出子目录和输出文件名
        filename: 'js/built.js'
    },

    // 3. loader
    module: {
        rules: [
            {
                // 对于每个文件，loader匹配成功之后就停止
                oneOf:[
                    // 详细的loader配置
                    // 处理css的loader
                    {
                        // 表示匹配哪些文件
                        test: /\.css$/,
                        // 使用哪些loader进行处理，使用多个loader进行处理
                        // use数组中loader的执行顺序:从后往前
                        use: [
                            'style-loader', // 在html的head中创建style标签，将js中的样式资源插入
                            'css-loader', // 将css文件变成 commonjs模块加载到js中。模块中的内容是字符串
                        ]
                        // 无法添加 outputPath 属性
                    },
                    // 处理less的loader
                    {
                        test:/\.less$/,
                        use:[
                            'style-loader',
                            'css-loader',
                            'less-loader',
                        ]
                        // 无法添加 outputPath 属性

                    },
                    // 处理css，js中引用的图片
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
                    // 解析html中的图片引用，并交给url-loader处理
                    {
                        test: /\.html$/,
                        // 解析html中的图片引用
                        loader: "html-loader",
                        // 无法添加 outputPath 属性
                    },
                    // 打包其他资源
                    {
                        // 排除 之前所有已经被处理过的文件类型，
                        // 否则会出现异常
                        exclude: /\.(html|css|js|less|json|jpg|png|gif)$/,
                        loader: "file-loader",
                        options:{
                            outputPath: "media"
                        }
                    },
                ]
            }
        ]
    },

    // 4. plugin
    plugins: [
        // 拷贝指定html资源，并自动引入打包生成的所有资源
        new HtmlWebpackPlugin({
            template: './src/hello.html'
        }),
    ],

    // 5. mode
    mode: 'development',

    // 8. source-map 配置
    devtool: 'eval-source-map',

    // 6. 开发服务器 devServer
    devServer:{
        // 6.1. 代码运行及监视
        contentBase: resolve(__dirname, 'build'),
        watchContentBase: true,
        watchOptions:{
            ignored: /node_modules/,    // 监视文件时，忽略某些文件
        },

        // 6.2. 控制日志输出
        clientLogLevel:'none',
        // quiet: true,
        overlay:false,                  // 如果出错了，不要全屏提示，打印到控制台日志中

        // 6.3. 访问设置
        port: 5000,
        host:'localhost',
        open: false,                    // 是否自动打开浏览器
        // 设置服务器代理 --> 用于解决开发环境的跨域问题
        proxy: {
            // 当devServer(5000)服务器接收到 /api/xxx 开头的请求，就会把请求转发到另一个服务器(3000)
            '/api': {
                target: 'http://localhost:3000',
                // 发送请求时，请求路径重写: 将 /api/xx 改成 /xxx
                pathRewrite:{
                    '^api':''
                }
            }
        },

        // 6.4. 优化设置
        compress: true,             // 是否开启gzip压缩
        hot: true,                  // 是否开启HMR功能
    },

    // 9. 配置模块解析规则
    resolve:{
        alias:{                     // 配置解析模块路径别名
            $css: resolve(__dirname, 'src/css'),
            $json: resolve(__dirname, 'src/json'),
        },

        extensions: ['.js', '.json'],   // 配置省略文件路径后缀名的规则

        // 提醒webpack，解析模块时，去哪个目录下搜索外部依赖
        modules:[ resolve(__dirname, '../../node_modules'), "node_modules"]
    }

}