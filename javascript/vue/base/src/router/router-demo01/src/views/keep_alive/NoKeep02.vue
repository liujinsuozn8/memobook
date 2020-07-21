<template>
    <div>
        <p>this is nokeep page -- 02</p>
    </div>
</template>

<script>
// 需要从 App.vue 的 keep-alive 中排出
export default {
    name: 'NoKeep02',
    created(){
        console.log('this is nokeep--02 created');
    },
    destroyed(){
        console.log('this is nokeep--02 destroyed');
    }
}
</script>