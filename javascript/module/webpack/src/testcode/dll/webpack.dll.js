const {resolve} = require('path')
const webpack = require('webpack');
/*
    使用dll对某些库进行单独打包
        一般是第三方库
*/
module.exports={
    // 1.1 外部依赖打包为dll的配置
    entry:{
        // jquery 表示最终打包生成的 [name] 是 jquery
        // ['jquery'] 表示哪些库参与打包
        jquery:['jquery', ],

        // 实际应用时，可以将多个相关的包配置在一起，打包成一个文件
        // react: ['react', 'react-dom','react-router-dom'],
    },
    // 1.2 外部依赖打包为dll的配置，打包结果向外暴露的名字为: [name]_[hash:10]
    output:{
        // 此处[name] 就是 entry 中配置的入口名
        filename: '[name].js',
        path: resolve(__dirname, 'dll'),

        // 打包的库里面，向外暴露的内容的名字
        // 添加hash，保证每次编译结果的文件名不同
        // 这样配置文件名后，就不能只通过 jquery来引用了，还需要附加 hash值
        library: '[name]_[hash:10]',
    },

    /*
        2.1 创建一个映射关系的json文件
            - 提供的映射关系
                - enrty中的入口名 [name]: 编译后向外暴露的名字[name]_[hash:10]
            - 可以缓解1.2 的问题
                - 编译结果附加hash值后，不方便使用，每次重新编译都需要修改引用内容
                - 仍然以 [name] 的方式引用
                - 编译时读取映射文件，找到真实的文件名
    */
    plugins:[
        new webpack.DllPlugin({
            // 映射库的暴露的内容的名称
            name: '[name]_[hash:10]',
            // 输出文件路径
            path: resolve(__dirname, 'dll/manifest.json')
        })
    ],
    mode:'production'
}