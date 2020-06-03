// 混合导出
function foo(){
    console.log("this is module2 foo")
}

function bar(){
    console.log("this is module2 bar")
}

export {foo, bar}

export let obj = {name:"testNamestr"}
