// 默认导出
export default {
    msg:"default msg",
    foo(){
        console.log("this is module3 default")
    }
}

// 单次导出
export let person = {name:'testName', age:22};

function moduleFn01(){
    console.log('this is moduleFn01');
}

function moduleFn02(){
    console.log('this is moduleFn02');
}

// 统一导出
export {moduleFn01, moduleFn02};