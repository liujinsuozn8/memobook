// 1. 创建一个 4 byte 长的 ArrayBuffer
var buff = new ArrayBuffer(4);

// 2. 创建一个 TypedArray，并使用 buff 的数据
var arr = new Int8Array(buff);
arr[0] = 10;

// 3. 创建一个 DataView，也使用 buff 的数据。
// 4. 现在 arr、view 同时在操作一个数据块，会互相影响
var view = new DataView(buff);

// 5. 从 view 获取通过 arr 设置的数据
console.assert(view.getInt8(0) === 10);

/* 6. 存储一个16进制数，0x1234 相当于存储了两位
    每一位长为 4 bit，两位长 8 bit，即 1byte
    从偏移量0开始，所以:
    arr[0] === 0x12, arr[1] === 0x34
*/
view.setInt16(0, 0x1234);

// 7. 通过 getInt8，去 1 byte 的数据
console.assert(view.getInt8(0).toString(16) === '12');
console.assert(view.getInt8(1).toString(16) === '34');

// 8. 通过 view 设置10进制数据，并通过 arr获取
view.setInt8(0, 12);
console.assert(arr[0] === 12);