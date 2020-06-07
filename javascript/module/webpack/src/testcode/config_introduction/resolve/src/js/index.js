// import '../css/index.css'
import '$css/index.css'
// 省略后缀名 .json，由 resolve.extensions 自动匹配
import testJson from  '$json/test_json'

console.log('this is config_introduction entry')
for (let k in testJson) {
    console.log(`key=${k}, value=${testJson[k]}`)
}
console.log(testJson)