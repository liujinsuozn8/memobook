const router = require('koa-router')();
const fs = require('fs');
const { resolve } = require('path');

// 上传文件测试
// 文件数据的请求参数: attr
router.post('/addFile', async ctx => {
    try {
        // 获取临时文件路径
        const file = ctx.request.files.attr;
        console.log(file.path);
        // 创建输入流
        const reader = fs.createReadStream(file.path);

        // 拼接输出文件路径
        const staticPath = `/upload/${file.name}`;
        const serverPath = resolve(__dirname, '../public' + staticPath);

        console.log(serverPath);
        // 创建输出流
        const writer = fs.createWriteStream(serverPath);
        // 拷贝文件
        reader.pipe(writer);

        // 返回上传结果
        ctx.body = { msg: 'saved', staticPath};
    } catch (e) {
        // 返回上传结果
        console.log(e);
        ctx.body = { msg: 'error', error: e}
    }
})

module.exports = router;