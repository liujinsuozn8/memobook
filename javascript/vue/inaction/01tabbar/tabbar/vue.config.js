let path = require('path')

module.exports = {
  // 添加额外的打包配置
  chainWebpack: config => {
    // 自定义路径
    config.resolve.alias
      .set('@assets', path.join(__dirname, './src/assets'))
      .set('@components', path.join(__dirname, './src/components'))
  },
};
