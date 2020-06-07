const {resolve} = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const TerserWebpackPlugin = require('terser-webpack-plugin')
module.exports={
    entry: {
        main: './src/js/index.js',
    },

    output:{
        path: resolve(__dirname, 'build'),
        filename:'js/[name].[contenthash:10].js',
        chunkFilename:'js/[name].[contenthash:10]_chunk.js',
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
    mode: 'production',
    // 配置模块解析规则
    resolve:{
        alias:{
            $css: resolve(__dirname, 'src/css'),
            $json: resolve(__dirname, 'src/json'),
        },
        extensions: ['.js', '.json'],
        modules:[ resolve(__dirname, '../../node_modules'), "node_modules"]
    },

    optimization:{
        /*
            1. 将每个模块中记录的其他模块的hash单独打包为一个文件: runtime，防止页面缓存失效
            用于解决:
                - 前提: 在 a 中引入 b。编译后，a 会包含 b 的hash值
                - 修改 b 会导致 a 中包含的hash值发生变化，使 a 的页面缓存失效
        */
        runtimeChunk: {
            name: enrtypoint => `runtime-${enrtypoint.name}`
        },

        // 2. 配置生产环境的压缩方案，包括: js、css
        // 默认使用 terser包 来执行 js 压缩
        minimizer:[
            new TerserWebpackPlugin({
                // 开启缓存
                cache: true,
                // 开启多进程打包
                parallel: true,
                // 使用source map，否则会被压缩掉
                sourceMap: true
            })
        ],
        // 3. 提取第三方的公共依赖
        splitChunks: {
            chunks:'all',
            // 一下都是默认值，一般可以不写
            // chunk打包的公共规则
            minSize: 30 * 1024,             // 分割chunk的最小容量为30kb，小于minSize就不会分割
            maxSize: 0,                     // 分割chunk的最大容量，0 表示没有限制
            minChunks:1,                    // 如果要提取chunk，至少要被引用 1 次
            maxAsyncRequests: 5,            // 按需加载时，并行加载的最大文件数
            maxInitialRequests: 3,           // 入口js文件最大并行请求数量
            automaticNameDelimiter: '~',    // 提取后，文件名的连接符，如: vendors~main.238036fce6.js
            name: true,                     // 可以使用命名规则
            cacheGroups: {                  //分割 chunk 的组
                // node_modules中的文件会被打包到 vendors 组的 chunk 中: vendors~xxx.js
                // 打包到分组之前，需要满足之前的公共规则
                vendors: {
                    test: /[\\/]node_modules[\\/]/,
                    // 打包优先级
                    priority: -10,
                },
                default: {
                    minChunks: 2,   // 如果要提取chunk，至少要被引用2次。会覆盖公共规则
                    // 打包优先级
                    priority: -20,

                    // 开启代码复用：
                    // 如果当前要打包的模块，和之前已经被提取的模块是同一个，就会复用
                    // 不会重新打包
                    reuseExistingChunk: true
                }
            }
        },
    }
}