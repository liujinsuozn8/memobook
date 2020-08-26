<template>
  <div>
    <p>goods show page</p>
    <p>total Price: {{$store.getters.goodsTotalPrice.toFixed(2)}}</p>
    <p>goods count: {{$store.getters.goodsCount}}</p>
    <p>average price: {{$store.getters.goodsAvgPrice.toFixed(2)}}</p>
    <ul>
      <li>index--name--price--count</li>
      <!-- <li v-for='(item, index) in $store.state.goodsList' :key='index'>{{index+1}}--{{item.name}}--{{item.price}}--{{item.count}}</li> -->
      <li v-for="(item, index) in $store.state.goodsList" :key="index">{{index+1}}--{{item.name}}--{{item.price.toFixed(2)}}--{{item.count}}</li>
    </ul>
    <hr />
    <!-- 添加状态数据 -->
    <div>
      goodsName:<input type="text" name="goodsName" v-model="goodsName" autocomplete="off" /><br/>
      goodsPrice:<input type="number" name="goodsPrice" v-model="goodsPrice"/><br/>
      goodsCount:<input type="number" name="goodsCount" v-model="goodsCount"/><br/>
      <!-- 通过普通方式提交 -->
      <button @click="append">append</button>
      <!-- 通过 type 属性提交 -->
      <button @click="appendByType">appendByType</button>
      <br>
    </div>
    <hr>
    <!-- 响应式的添加与删除State中的状态 -->
    <div>
      <p>address: {{$store.state.addressInfo.address}}</p>
      <p>other: {{$store.state.other}}</p>
      <button @click='deleteAddress'>deleteAddress</button>
      <button @click='addAddress'>addAddress</button>
      <button @click='addother'>addother</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "GoodsShow",
  data() {
    return {
      goodsName: "",
      goodsPrice: 0,
      goodsCount: 0
    };
  },
  methods: {
    // 通过普通方式提交
    append() {
      // 调用 mutations，传递商品对象，并执行添加操作
      this.$store.commit("appendGoods", {
        name: this.goodsName,
        price: Number(this.goodsPrice),
        count: Number(this.goodsCount)
      });

      this.dataInit();
    },
    // 通过 type 属性提交
    appendByType() {
      // 调用 mutations，传递商品对象，并执行添加操作
      this.$store.commit({
        type: "appendGoodsByType",
        name: this.goodsName,
        price: Number(this.goodsPrice),
        count: Number(this.goodsCount)
      });
      this.dataInit();
    },
    // 初始化数据
    dataInit(){
      this.goodsName = "";
      this.goodsPrice = 0;
      this.goodsCount = 0;
    },
    // 响应式的向 state 中添加、删除属性
    deleteAddress(){
      this.$store.commit('deleteAddress');
    },
    addAddress(){
      this.$store.commit('addAddress');
    },
    addother(){
      this.$store.commit('addOther');
    }
  }
};
</script>