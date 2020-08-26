<template>
    <div>{{count}}</div>
</template>

<script>
export default {
    name:'UseCount',
    // data(){
    //     return {
    //         count:0
    //     }
    // }
    props:['count']
}
</script>