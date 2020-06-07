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
        // 配置解析模块路径别名
        alias:{
            // 为 _dirname/src/css 创建一个别名 $css
            // 在入口文件中，即可通过 $css 来使用该路径
            $css: resolve(__dirname, 'src/css'),

            $json: resolve(__dirname, 'src/json'),
        },

        // 配置省略文件路径后缀名的规则
        // 一般不会配置 '.css'，因为css的名字一般会和html、js等同名
        // 如果入口文件中出现同名文件会导致编译异常
        extensions: ['.js', '.json'],

        // 提醒webpack，解析模块时，去哪个目录下搜索外部依赖
        // 默认到同级的 node_modules 目录下搜索，如果当前路径下没有，则到上一级目录搜索
        // 一直搜索到根目录，如果一直都没有搜索到，则报错
        // 通过指定 modules，可以直接到指定目录下搜索

        // 在一个多层嵌套的工程中，通过指定搜索路径可以提升打包速度
        // 一般会在数组最后附加 node_modules，防止找不到的情况
        modules:[ resolve(__dirname, '../../node_modules'), "node_modules"]
    }
}