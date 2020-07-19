// 1. 相等比较
console.log( NaN == NaN);   // false
console.log( NaN != NaN);   // true

// 2. 严格相等比较
console.log( NaN === NaN);  // false
console.log( NaN !== NaN);  // true

// 3. 大小比较
console.log( NaN < NaN);    // false
console.log( NaN <= NaN);   // false
console.log( NaN > NaN);    // false
console.log( NaN >= NaN);   // false

// 4. 使用 Object.is 进行比较
console.log( Object.is(NaN, NaN) ); // true