function* MyGenerator(){
    console.log("generator start")

    yield console.log("first")

    let result01 = yield "aaaaa"
    console.log("result01 =" + result01)

    let result02 = yield "bbbbb"
    console.log("result02 =" + result02)

    console.log("generator end")
}

let g = MyGenerator()
console.log(g)

console.log("create")
console.log(1);
console.log(g.next())
console.log(2);
console.log(g.next())
console.log(3);
console.log(g.next(3333))
console.log(4);
console.log(g.next())
console.log(5);
console.log(g.next())

// Object [Generator] {}
// create
// 1
// generator start
// first
// { value: undefined, done: false }
// 2
// { value: 'aaaaa', done: false }
// 3
// result01 =3333
// { value: 'bbbbb', done: false }
// 4
// result02 =undefined
// generator end
// { value: undefined, done: true }
// 5
// { value: undefined, done: true }