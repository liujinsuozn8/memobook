// 将组件从 index.js 中剥离
export default {
template:`
<div>
    msg: <p>{{msg}}</p>
    <button @click='btnClick'>btn</button>
</div>
`,
data(){
    return {
    msg: 'webpack test msg',
    }
},
methods:{
    btnClick(){
    console.log('btn click');
    }
}
}