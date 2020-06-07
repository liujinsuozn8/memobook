console.log("this is index.js");

import(/* webpackChunkName: 'add' */'./add').then(
    ({add})=>{
        console.log(add(2,5));
    }
).catch( ()=>console.log("import error") );