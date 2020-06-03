// tree shaking 测试
// 同时向外暴露两个方法：mul、minus
// 但是只使用一个来测试 tree shaking 的打包结果

export function mul(x, y) {
  return x * y;
}

export function minus(x, y) {
  return x - y;
}
