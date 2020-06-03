const {resolve} = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin')
module.exports={
    // 2. Array型入口
    entry: ['./src/index.js', './src/add.js'],

    output:{
        path: resolve(__dirname, 'build'),
        filename:'[name].js'
    },
    module:{
        rules:[
            {
                oneOf: [    // 文件只会被多个loader匹配一次
                    {
                        test:/\.css$/,
                        use:['style-loader', 'css-loader'], // 使用多个个loader
                    },
                    {
                        test:/\.js$/,               // 设置需要处理的文件
                        exclude: /node_modules/,    // 排除目录
                        include: /src/,             // 只检查 src目录
                        enforce: 'pre',             // 提前执行
                        loader: 'eslint-loader',    // 使用单个loader
                        options:{                   // 设置单个loader的属性
                            fix:true
                        }
                    }
                ]
            }
        ]
    },
    plugins:[
        // 不指定 template，使用插件自动生成的空html
        new HtmlWebpackPlugin()
    ],
    mode: 'development'
}