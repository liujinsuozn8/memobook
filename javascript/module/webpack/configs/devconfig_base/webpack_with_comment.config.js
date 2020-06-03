const { resolve } = require("path");
const HtmlWebpackPlugin = require('html-webpack-plugin')

module.exports={
    // 1. 入口，指定打包入口js文件
    entry:'./src/index.js',

    // 2. 输出
    output:{
        // 输出文件名
        filename: 'built.js',
        // 输出目录，最好使用绝对路径
        path: resolve( __dirname, 'build')
    },

    // 3. loader
    module: {
        rules: [
            // 处理css的loader
            {
                // 表示匹配哪些文件
                test: /\.css$/,
                // 使用哪些loader进行处理，使用多个loader进行处理
                // use数组中loader的执行顺序:从后往前
                use: [
                    'style-loader', // 在html的head中创建style标签，将js中的样式资源插入
                    'css-loader', // 将css文件变成 commonjs模块加载到js中。模块中的内容是字符串
                ],
            },
            // 处理less的loader
            {
                test:/\.less$/,
                use:[
                    'style-loader',
                    'css-loader',
                    'less-loader',
                ]
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
                    name: "[hash:10].[ext]"
                }
            },
            // 处理html中的引用的图片
            {
                test: /\.html$/,
                // 处理html中引用的图片，从而能被url-loader处理
                loader: "html-loader",
            },
            // 打包其他资源
            {
                // 排除 之前所有已经被处理过的文件类型，
                // 否则会出现异常
                exclude: /\.(html|css|js|less|json|jpg|png|gif)$/,
                loader: "file-loader",
                options: {
                    // 为文件重命名
                    // [hash:10]: 取hash值的前10位
                    // [ext]: 使用文件原来的扩展名
                    name:"[hash:10].[ext]"
                }
            },
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

    // 6. 开发服务器 devServer
    devServer:{
        // 指定编译结果目录
        contentBase: resolve(__dirname, "build"),
        // 开启gzip压缩，使结果更小更快
        compress: true,
        // 指定服务器的端口号
        port: 8888,
        // 启动后，自动打开浏览器
        // open: true,
    }
}