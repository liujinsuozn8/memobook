HttpSession对象生命周期测试
- 第一次进入：noSession，显示null
- 进入：hasSession，创建一个HttpSession对象
- 再次进入：noSession，获取到一个与第二步相同的HttpSession对象