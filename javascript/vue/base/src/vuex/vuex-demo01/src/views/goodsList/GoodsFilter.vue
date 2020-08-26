<template>
    <div>
        <!-- 输出过滤条件 -->
        minPrice: <input type="number" name="minPrice" v-model="minPrice">
        minCount: <input type="number" name="minCount" v-model="minCount">
        <!-- 点击按钮，从getters 中获取闭包函数，并进行过滤处理 -->
        <button @click='search'>search</button>
        <p v-show='!isShowResult'>no goods</p>
        <!-- 显示处理结果 -->
        <ul v-show="isShowResult">
            <li v-for='(item, index) in filterResult' :key='index'>{{index+1}}--{{item.name}}--{{item.price.toFixed(2)}}--{{item.count}}</li>
        </ul>
    </div>
</template>

<script>
export default {
    name: 'GoodsFilter',
    data(){
        return {
            minPrice:0,         // 过滤条件
            minCount:0,         // 过滤条件
            filterResult:null,  // 保存过滤结果
        }
    },
    methods:{
        search(){
            // 1. 通过 getters 获取闭包函数
            const filter = this.$store.getters.goodsFilter;
            // 2. 通过闭包函数处理过滤参数，并保存结果
            this.filterResult = filter(this.minPrice, this.minCount);
        }
    },
    computed:{
        // 根据过滤结果的数量来决定显示内容
        isShowResult(){
            return this.filterResult && this.filterResult.length > 0;
        }
    }
}
</script>