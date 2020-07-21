<template>
    <div>
        <p>this is nokeep page</p>
    </div>
</template>

<script>
// 需要从 App.vue 的 keep-alive 中排出
export default {
    name: 'NoKeep',
    created(){
        console.log('this is nokeep created');
    },
    destroyed(){
        console.log('this is nokeep destroyed');
    }
}
</script>