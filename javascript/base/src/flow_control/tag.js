///////////////////////////////////////////////////////////////////////////
// 1. break + 标签语句
break_start:
for (let i = 1; i < 5; i++) {
    for (let j = 1; j < 5; j++) {
        if (i == 3 & j == 3){
            // 如果满足条件，则直接退出双重循环
            // 普通的 break 只能退出内层循环，不能退出外部循环
            break break_start; 
        }
        
        console.log(i * j);
    }
}

/* 输出
1 2 3 4
2 4 6 8
3 6
break到标签后，剩余的循环被取消了
*/

console.log('--------------------------');
///////////////////////////////////////////////////////////////////////////
// 2. continue + 标签语句
continue_start:
for (let i = 1; i < 5; i++) {
    for (let j = 1; j < 5; j++) {
        if (i == 3 & j == 3){
            // 如果满足条件，跳出当前内层循环，从下一次外层循环开始继续执行
            continue continue_start; 
        }
        
        console.log(i * j);
    }
}


/* 输出: 
1 2 3  4
2 4 6  8
3 6
4 8 12 16
满足 i=3、j=3时，直接跳出内层循环，继续下一次外层循环，
所以第三次循环应该输出的 9、12 被跳过了
*/

console.log('--------------------------');

///////////////////////////////////////////////////////////////////////////
// 3. break 直接跳出标签逻辑
// 3.1 使用 break 跳出标签语句
let str = 'abcd';
break_tag01:{
    console.log('before');  // 输出: before
    if (str === 'abcd'){
        break break_tag01;    // 直接跳出标签
    }
    console.log('after');   // 在break处跳出标签，不会有机会执行
}

// 3.2 将标签语句执行完
let num = 222;
break_tag02:{
    console.log('before');  // 输出: before
    if (num === 33){        // 不满足条件不会跳出标签
        break break_tag02;    
    }
    console.log('after');   // 输出: after
}

// 3.3 标签语句后面没有加标签名，产生编译异常
// let mark = 'abcd'
// break_tag02:{
//     console.log('before');
//     if (num === 'abcd'){
//         break;              // 编译异常，标签语句内的 break 后，不能省略当前标签名
//     }
//     console.log('after');
// }

