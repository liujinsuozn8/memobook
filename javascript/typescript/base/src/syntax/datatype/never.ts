let p: never;

p = (()=>{
    throw new Error('test err');
})();

console.log(p);