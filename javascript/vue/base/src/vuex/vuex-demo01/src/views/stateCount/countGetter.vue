<template>
  <div class="countGetter">
    <p>counter: {{$store.state.counter}}</p>
    <!-- 通过 getter 获取 count 的平方 -->
    <p>get powerCount: {{$store.getters.powerCount}}</p>
    <h1>countGetter page</h1>
  </div>
</template>
