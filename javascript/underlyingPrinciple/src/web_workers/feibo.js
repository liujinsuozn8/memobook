function feibo(n){
    return n <=2? 1: feibo(n-1) + feibo(n-2)
}

var onmessage = function (event){
    var num = event.data
    console.log("this is onmessage")
    var result = feibo(num)
    postMessage(result)
}
