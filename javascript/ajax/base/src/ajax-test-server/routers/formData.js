const router = new require('koa-router')();
const {resolve} = require('path');
const fs = require('fs');

router.post('/usageTest', ctx=>{
    // 直接将请求参数返回
    ctx.body=ctx.request.body;
})

// 接受上传的文件，并将文件保存到 upload 目录下
// 浏览器端上传的文件会默认保存到一个临时目录下，需要开启临时文件和一个新文件进行文件读写
router.post('/upload', ctx=>{
    // 默认上传的文件数据保存在 ctx.request.files 中
    const file = ctx.request.files.attr;
    const reader = fs.createReadStream(file.path)

    const staticPath = '/upload/' + file.name;
    const serverPath = resolve(__dirname, '../public' + staticPath);
    const writer = fs.createWriteStream(serverPath);
    reader.pipe(writer);

    // 将服务端保存的地址返回给浏览器
    ctx.body = {staticPath};
})

module.exports = router;