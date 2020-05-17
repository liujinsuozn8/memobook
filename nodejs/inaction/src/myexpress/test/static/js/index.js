window.onload = function(){
    let buttons = document.getElementsByTagName("button");
    Array.from(buttons).forEach((item,idx)=>{
        item.onclick=()=>{console.log("this is btn: "+idx)}
    })
}