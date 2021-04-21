- 参考
    - https://www.bilibili.com/video/BV1gW411W7wy

# Spring扩展原理
## BeanFactoryProcessor


# Spring扩展原理
## BeanFactoryPostProcessor

- 执行时间
    - 在BeanFactory标准初始化之后调用
        - 所有bean定义已经保存并加载到beanFactory中
        - 但是bean的实例还未创建
- 执行过程
    1. ioc容器创建
    2. refresh
    3. invokeBeanFactoryPostProcessors(beanFactory);
        - 如何找到所有的 BeanFactoryPostProcessor，并执行其方法
            1. 直接在BeanFactory中找到所有类型是BeanFactoryProcessorPostProcessor的组件，并执行其方法
            2. 会在初始化其他组件之前执行

## BeanDefinitionRegistryPostProcessor

- BeanFactoryPostProcessor 的子接口
- 接口方法: postProcessBeanFactory
- 执行时间
    - 在所有bean定义信息将要被加载，bean实例还未创建时
- 优先于 BeanFactoryPostProcessor 执行
- 可以用于向容器中再添加一些组件
- 执行过程
    1. ioc容器创建
    2. refresh
    3. invokeBeanFactoryPostProcessors(beanFactory);
    4. 从容器获取所有 BeanDefinitionRegistryPostProcessor 的组件





