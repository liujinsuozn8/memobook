const {resolve} = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')
module.exports={
    entry: {
        main: './src/js/index.js',
    },

    output:{
        path: resolve(__dirname, 'build'),
        filename:'js/[name].js'
    },
    module:{
        rules:[
            {
                test:/\.css$/,
                use:['style-loader', 'css-loader'],
            },
        ]
    },
    plugins:[
        new HtmlWebpackPlugin()
    ],
    mode: 'development',
    // 配置模块解析规则
    resolve:{
        alias:{
            $css: resolve(__dirname, 'src/css'),
            $json: resolve(__dirname, 'src/json'),
        },
        extensions: ['.js', '.json'],
        modules:[ resolve(__dirname, '../../node_modules'), "node_modules" ]
    },
    devServer:{
        // 1. 代码运行及监视
        // 运行代码的目录
        contentBase: resolve(__dirname, 'build'),
        // 监视 contentBase 目录下的文件。一旦文件变化，就会reload
        watchContentBase: true,
        watchOptions:{
            // 监视文件时，忽略某些文件
            ignored: /node_modules/,
        },

        // 2. 控制日志输出
        // 不显示启动服务器的日志信息
        clientLogLevel:'none',

        /*
            除了一些基本的启动消息以外，其他内容都不显示
            clientLogLevel:'none' + quiet: true，控制台基本就不会显示什么信息了
        */
        // quiet: true,

        /*
            如果出错了，不要全屏提示，打印到控制台日志中
            如果配置了: clientLogLevel:'none' + quiet: true, 则不会打印到日志
        */
        overlay:false,

        // 3. 访问设置
        // 指定服务端口号
        port: 5000,
        // 指定域名
        host:'localhost',
        // 是否自动打开浏览器
        open: false,
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

        // 4. 优化设置
        // 是否开启gzip压缩
        compress: true,
        // 是否开启HMR功能
        hot: true,
    }

}