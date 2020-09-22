<span id="catalog"></span>

<span style='font-size:18px'>目录---spark核心应用<span>

- [wordcount的实现](#wordcount的实现)
- [构建开发框架](#构建开发框架)
    - [框架层次划分](#框架层次划分)
    - [从各个层中剥离上下文对象](#从各个层中剥离上下文对象)
    - [在application层控制执行的流程](#在application层控制执行的流程)
    - [其他层](#其他层)
- [用户行为日志分析](#用户行为日志分析)
    - [日志格式](#日志格式)
    - [需求1--分析热门品类前10](#需求1--分析热门品类前10)
        - [需求1--需求分析](#需求1--需求分析)
        - [基本实现--按项目读取再合并](#基本实现--按项目读取再合并)
        - [优化1--添加缓存、去除join](#优化1--添加缓存、去除join)
        - [优化2--只读取一次文件](#优化2--只读取一次文件)
        - [优化3--累加器替代reduceByKey](#优化3--累加器替代reduceByKey)
    - [需求2--统计热门品类前10中每个品类下的活跃session前10](#需求2--统计热门品类前10中每个品类下的活跃session前10)
        - [需求2--需求分析](#需求2--需求分析)
        - [需求2--基本实现](#需求2--基本实现)
        - [优化--使用广播变量](#优化--使用广播变量)
    - [需求3--计算单跳转换率](#需求3--计算单跳转换率)
        - [需求3--需求分析](#需求3--需求分析)
        - [需求3--基本实现](#需求3--基本实现)
        - [优化--只保留有意义的页面ID](#优化--只保留有意义的页面ID)
- [](#)

# wordcount的实现
[top](#catalog)
- groupBy
- groupByKey
- reduceByKey
- aggregateByKey
- foldByKey
- combineByKey
- countByKey
- countByValue
- reduce
- fold
- aggregate
- 参考
    - /myspark/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/wordcount/WordCount03.scala
    - ??????

# 构建开发框架
## 框架层次划分
[top](#catalog)
- dao层
    - 负责数据连接操作，包括: 从文件、数据库、HDFS 读写数据
- service
    - 调用 dao 获取数据
    - 负责主要的业务逻辑，处理从 dao 中获取的数据
- controller
    - 调用 service 获取业务处理的结果
    - 负责如何调用 service，如何将复杂的业务拆分到多个 service 并有序的调用
- application
    - 调用 controller
    - 负责整体流程的控制

## 从各个层中剥离上下文对象
[top](#catalog)
- 剥离方式
    1. 创建一个工具类 `EnvUtil`，通过静态方法来获取上下文对象
    2. 将上下文对象保存在`线程内存`中，任何层获取时，都通过工具类，从线程内存中获取对象
    3. 使用懒加载的方式创建对象
        - 即第一次使用时创建上下文对象，并保持到线程内存
        - 后续再使用时，都从线程内存中获取
- 参考
    - [src/spark-learn/spark-summer-framework/src/main/scala/com/ljs/learn/sparksummer/framework/util/EnvUtil.scala](src/spark-learn/spark-summer-framework/src/main/scala/com/ljs/learn/sparksummer/framework/util/EnvUtil.scala)
- 代码内容
    ```scala
    object EnvUtil {
      private val scLocal = new ThreadLocal[SparkContext]
      def getEnv():SparkContext={
        // 从线程的共享内存空间中获取对象
        var sc: SparkContext = scLocal.get()
        if (sc == null){
          // 如果内存中没有，则创建并保存
          val conf = new SparkConf().setMaster("local").setAppName("sparkApplication")
          sc = new SparkContext(conf)
          scLocal.set(sc)
        }
        sc
      }

      def clear(): Unit ={
        // 关闭spark上下文
        getEnv().stop()
        // 清除共享内存中的数据
        scLocal.remove()
      }
    }
    ```

## 在application层控制执行的流程
[top](#catalog)

- 参考
    - [src/spark-learn/spark-summer-framework/src/main/scala/com/ljs/learn/sparksummer/framework/core/TApplication.scala](src/spark-learn/spark-summer-framework/src/main/scala/com/ljs/learn/sparksummer/framework/core/TApplication.scala)
- 通过`控制抽象`，固定整体的执行逻辑
    - 在启动时，尝试创建上下文对象
    - 在结束时，关闭上下文对象
- 代码内容
    ```scala
    trait TApplication {
      var envData: Any = null

      // 通过控制抽象结构整体流程和具体操作
      def start()(op: => Unit): Unit = {
        // val conf = new SparkConf().setMaster("local").setAppName("sparkApplication")
        // envData = new SparkContext(conf)

        // 启动时，尝试获取spark上下文对象，相当于创建的上下文对象
        envData = EnvUtil.getEnv()
        try {
          op
        } catch {
          case ex: Exception => println(s"job failure: ${ex.getMessage}")
        }

        // envData.asInstanceOf[SparkContext].stop()
        EnvUtil.clear()
      }
    }
    ```
- application层的使用方式
    ```scala
    object XxxApplication extends App with TApplication{
      start(){
        // 业务逻辑
      }
    }
    ```

## 其他层
[top](#catalog)
- 参考
    - [src/spark-learn/spark-summer-framework/src/main/scala/com/ljs/learn/sparksummer/framework/core/TController.scala](src/spark-learn/spark-summer-framework/src/main/scala/com/ljs/learn/sparksummer/framework/core/TController.scala)
    - [src/spark-learn/spark-summer-framework/src/main/scala/com/ljs/learn/sparksummer/framework/core/TService.scala](src/spark-learn/spark-summer-framework/src/main/scala/com/ljs/learn/sparksummer/framework/core/TService.scala)
    - [src/spark-learn/spark-summer-framework/src/main/scala/com/ljs/learn/sparksummer/framework/core/TDao.scala](src/spark-learn/spark-summer-framework/src/main/scala/com/ljs/learn/sparksummer/framework/core/TDao.scala)

- controller 层
    ```scala
    trait TController {
      // 执行控制
      def execute():Unit
    }
    ```
- service 层
    ```scala
    trait TService {
      // 负责调用 dao，并处理数据
      def analysis():Any={
      }

      // 处理数据时，需要其他 service 的处理结果
      def analysis(data:Any):Any={
      }
    }
    ```
- dao 层
    ```scala
    trait TDao {
      // 从文本文件中读取数据
      def readFile(path: String): RDD[String] = {
        val fileRDD: RDD[String] = EnvUtil.getEnv().textFile(path)
        fileRDD
      }
    }
    ```

# 用户行为日志分析
## 日志格式
[top](#catalog)
- 使用 `_` 分割数据
- 包含4种行为
    1. 搜索
    2. 点击
    3. 下单
    4. 支付
- 每列数据的含义
    1. 日期: 20xx-xx-xx
    2. 用户ID: xx
    3. sessionID
    4. 页面id:
    5. 动作时间: 20xx-xx-xx xx:xx:xx
    6. 搜索--关键字: `null` 表示不是搜索数据
    7. 点击-品类id: `-1` 表示数据不是点击数据
    8. 点击-商品id: `-1` 表示数据不是点击数据
    9. 下单一次订单中所有品类的ID集合
        - null表示不是下单
        - 内部有多个id，由 `,` 分割
    10. 下单一次订单中所有商品的ID集合
        - null表示不是下单
        - 内部有多个id，由 `,` 分割
    11. 支付一次支付中所有品类的ID集合
        - null表示不是下单
        - 内部有多个id，由 `,` 分割
    12. 支付一次支付中所有商品的ID集合
        - null表示不是下单
        - 内部有多个id，由 `,` 分割
    13. 城市id

## 需求1--分析热门品类前10
### 需求1--需求分析
[top](#catalog)
- 按照每个品类的点击、下单、支付的量来统计热门品类
- 排序的顺序
    1. 点击
    2. 下单
    3. 支付

### 基本实现--按项目读取再合并
[top](#catalog)
- 参考
    - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/service/HotCategoryAnalysisTop10ServiceBK01.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/service/HotCategoryAnalysisTop10ServiceBK01.scala)
- 代码内容
    ```scala
    override def analysis(): Array[(String, (Int, Int, Int))] = {
      // 读取数据
      val fileRDD: RDD[String] = dao.readFile("./input/demo/  user_visit_action.txt")

      // 1. 从文件中分别读取不同字段的数据
      // 1.1 对品类做点击量的排名 (category, clickCount)
      val clickCount = fileRDD.map(
        line => {
          val fields = line.split("_")
          (fields(6), 1)
        }
      ).filter(_._1 != "-1")
        .reduceByKey(_ + _)

      // 1.2 对品类做下单量的排名 (category, orderCount)
      // fields(8) 中是 一次订单中所有品类的ID集合，用 `,` 分割
      val orderCount = fileRDD.map(
        line => {
          val fields = line.split("_")
          fields(8)
        }
      ).filter(_ != "null")
        .flatMap(field => field.split(",").map((_, 1)))
        .reduceByKey(_ + _)

      // 1.3 对品类做支付量的排名 (category, payCount)
      val payCount = fileRDD.map(
        line => {
          val fields = line.split("_")
          fields(10)
        }
      ).filter(_ != "null")
        .flatMap(field => field.split(",").map((_, 1)))
        .reduceByKey(_ + _)

      // 2. 连接三个聚合结果，并转换结构
      val mapRDD = clickCount.join(orderCount)
        .join(payCount) // 连接后的数据结构 (品类 ((点击量, 下单量), 支付量))
        .mapValues {
          case ((click, order), pay) => {
            (click, order, pay) // 将数据结果转换为: (品类 (点击量,下单量,支付量))
          }
        }
      // 对转换后的数据进行降序排序
      // sortBt 会按照 (click, order, pay) 的顺序自动为三个值排序
      mapRDD.sortBy(_._2, false)
        .take(10)
      // 将排序结果取前10名
    }
    ```

### 优化1--添加缓存、去除join
[top](#catalog)
- 优化方式
    1. 在文件读取后，添加缓，防止重复读取
    2. 去除join
        - 将 `(k, v)` 类型的数据转换成 `(k, (v, 0, 0))` 的结构
        - 即直接将数据转换成结果的格式，简化计算
- 参考
    - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/service/HotCategoryAnalysisTop10ServiceBK02.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/service/HotCategoryAnalysisTop10ServiceBK02.scala)
- 代码内容
    ```scala
    override def analysis(): Array[(String, (Int, Int, Int))] = {
      // 读取数据
      val fileRDD: RDD[String] = dao.readFile("./input/demo/  user_visit_action.txt")

      // 优化1: 添加缓存，防止 三次 map 每次都直接读取文件
      fileRDD.cache()

      // 1. 从文件中分别读取不同字段的数据
      // 对品类做点击量的排名 (category, clickCount)
      val clickCount = fileRDD.map(
        line => {
          val fields = line.split("_")
          (fields(6), 1)
        }
      ).filter(_._1 != "-1")
        .reduceByKey(_ + _)

      // 对品类做下单量的排名 (category, orderCount)
      // fields(8) 中是 一次订单中所有品类的ID集合，用 `,` 分割
      val orderCount = fileRDD.map(
        line => {
          val fields = line.split("_")
          fields(8)
        }
      ).filter(_ != "null")
        .flatMap(field => field.split(",").map((_, 1)))
        .reduceByKey(_ + _)

      // 对品类做支付量的排名 (category, payCount)
      val payCount = fileRDD.map(
        line => {
          val fields = line.split("_")
          fields(10)
        }
      ).filter(_ != "null")
        .flatMap(field => field.split(",").map((_, 1)))
        .reduceByKey(_ + _)

      // 优化2: 替换 join
      // 将数据由 kv-转换成 k (v, 0, 0)，来简化计算
      val clickMap = clickCount.map {
        case (k, v) => (k, (v, 0, 0))
      }
      val orderMap = orderCount.map {
        case (k, v) => (k, (0, v, 0))
      }
      val payMap = payCount.map {
        case (k, v) => (k, (0, 0, v))
      }

      // 2. 合并三个 rdd，并聚合
      val mapRDD: RDD[(String, (Int, Int, Int))] = clickMap.union(orderMap)
        .union(payMap)
        .reduceByKey(
          (t1, t2) => {
            (t1._1 + t2._1, t1._2 + t2._2, t1._3 + t2._3)
          }
        )

      // 3. 对转换后的数据进行降序排序
      mapRDD.sortBy(_._2, false)
        .take(10)
      // 4. 将排序结果取前10名
    }
    ```

### 优化2--只读取一次文件
[top](#catalog)
- 优化方式
    - 只读取一次文件
    - 读取时，判断各个字段的状态，并整理成便于计算的格式
- 参考
    - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/service/HotCategoryAnalysisTop10ServicebK03.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/service/HotCategoryAnalysisTop10ServicebK03.scala)
- 代码内容
    ```scala
    override def analysis(): Array[(String, (Int, Int, Int))] = {
      // 读取数据
      val fileRDD: RDD[String] = dao.readFile("./input/demo/  user_visit_action.txt")

      // 优化3 [只读一次文件] 直接创建 (key, (1, 0, 0)) 格式的数据
      val mapRDD = fileRDD.flatMap(line=>{
        val fields = line.split("_")
        if (fields(6) != "-1"){
          List((fields(6), (1,0,0)))
        }else if (fields(8) != "null"){
          fields(8).split(",").map(
            w=> (w, (0,1,0))
          )

        }else if (fields(10) != "null") {
          fields(10).split(",").map(
            w=> (w, (0,0,1))
          )
        }else {
          Nil
        }
      }).reduceByKey(
        (t1, t2)=>{
          (t1._1+t2._1, t1._2+t2._2,t1._3+t2._3)
        }
      )

      // 对转换后的数据进行降序排序
      mapRDD.sortBy(_._2, false)
        .take(10)
      // 将排序结果取前10名
    }
    ```

### 优化3--累加器替代reduceByKey
[top](#catalog)
- 优化方式
    - 创建数据结构保存为各个项目的统计结果，便于计算
    - 使用累加器替代reduce，提升性能
- 创数据结构
    - 参考
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/bean/package.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/bean/package.scala)
    - 代码内容
        ```scala
        case class CategoryInfo(
          id: String,
          var clickCount: Int,    // 点击量
          var orderCount: Int,    // 下单量
          var payCount: Int)      // 支付量
        ```
- 创建累加器
    - 参考
        - [src\spark-learn\spark-core\src\main\scala\com\ljs\learn\sparkcore\inaction\helper\HotCategoryAccumulator.scala](src\spark-learn\spark-core\src\main\scala\com\ljs\learn\sparkcore\inaction\helper\HotCategoryAccumulator.scala)
    - 代码内容
        ```scala
        // 输入数据：(id, type)
        // 输出数据 Map(id --> CategoryInfo)
        class HotCategoryAccumulator extends AccumulatorV2[(String, String), mutable.Map[String, CategoryInfo]] {
          private val map: mutable.Map[String, CategoryInfo] = mutable.Map[String, CategoryInfo]()

          override def isZero: Boolean = {
            map.isEmpty
          }

          override def copy(): AccumulatorV2[(String, String), mutable.Map[String, CategoryInfo]] = {
            new HotCategoryAccumulator
          }

          override def reset(): Unit = {
            map.clear()
          }

          override def add(v: (String, String)): Unit = {
            val id = v._1
            val ctype = v._2
            val info = map.getOrElse(id, new CategoryInfo(id, 0, 0, 0))
            // 根据type，增加不同属性的值
            ctype match {
              case "click" => info.clickCount += 1
              case "order" => info.orderCount += 1
              case "pay" => info.payCount += 1
              case _ =>
            }
            map(id) = info
          }

          override def merge(other: AccumulatorV2[(String, String), mutable.Map[String, CategoryInfo]]): Unit = {
              // 将 other 的数据合并到当前对象的 map 中
              other.value.foreach{
                case (id, info)=>{
                  if (map.contains(id)){
                    val curInfo: CategoryInfo = map.get(id).get
                    curInfo.clickCount += info.clickCount
                    curInfo.orderCount += info.orderCount
                    curInfo.payCount += info.payCount
                  }else {
                    map(id) = info
                  }
                }
              }
          }

          override def value: mutable.Map[String, CategoryInfo] = map
        }
        ```

- 使用累加器替代reduceByKey
    - 参考
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/service/HotCategoryAnalysisTop10Service.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/service/HotCategoryAnalysisTop10Service.scala)
    - 代码内容
        ```scala
        override def analysis() = {
          // 读取数据
          val fileRDD: RDD[String] = dao.readFile("./input/demo/user_visit_action.txt")

          // 优化5: 用累加器替代 reduceByKey
          val acc = new HotCategoryAccumulator()
          EnvUtil.getEnv().register(acc)    // 注册自定义累加器

          fileRDD.foreach(line => {
            val fields: Array[String] = line.split("_")
            if (fields(6) != "-1") {
              acc.add((fields(6), "click"))     // 使用累加器求和
            } else if (fields(8) != "null") {
              fields(8).split(",").foreach(id => {
                acc.add((id, "order"))          // 使用累加器求和
              })
            } else if (fields(1) != "null") {
              fields(10).split(",").foreach(id => {
                acc.add((id, "pay"))            // 使用累加器求和
              })
            }
          })

          // 对转换后的数据进行降序排序
          // 从累加器中获取求和结果: acc.value。结果是一个 map，获取其所有的 value 进行排序
          val result: List[CategoryInfo] = acc.value.values.toList.sortWith(
            (left, right) => {
              if (left.clickCount > right.clickCount) {
                true
              } else if (left.clickCount < right.clickCount) {
                false
              } else {
                if (left.orderCount > right.orderCount) {
                  true
                } else if (left.orderCount < right.orderCount) {
                  false
                } else {
                  if (left.payCount > right.payCount) {
                    true
                  } else {
                    false
                  }
                }
              }
            }
          ).take(10)
          // 将排序结果取前10名

          result
        }
        ```

## 需求2--统计热门品类前10中每个品类下的活跃session前10
### 需求2--需求分析
[top](#catalog)
- 统计方式
    1. 统计热门品类前10 ，即需求1
    2. 过滤日志，只保留是热门品类前10的内容
    3. 按照 `品类--session_id` 进行分组求和
    4. 再对 `品类` 分组，并在组内，用 `session_id` 降序排序，获取 每组前10个 `session_id` 的数据

### 需求2--基本实现
[top](#catalog)
- controller 层，在需求1的基础上计算需求2
    - 参考
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/service/HotCategorySessionAnalysisTop10ServiceBK01.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/service/HotCategorySessionAnalysisTop10ServiceBK01.scala)
    - 代码内容
        ```scala
        class HotCategorySessionAnalysisTop10Controller extends TController{
          private val to10Service = new HotCategoryAnalysisTop10Service
          private val sessionService = new HotCategorySessionAnalysisTop10Service
          override def execute(): Unit = {
            // 1. 获取 需求1 的计算结果
            val top10Data: List[bean.CategoryInfo] = to10Service.analysis()
            // 2. 在 需求1 的基础上计算需求2
            sessionService.analysis(top10Data).foreach(println)
          }
        }
        ```
- 创建数据结构，在读取数据时，使用
    - 参考
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/bean/package.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/bean/package.scala)
    - 代码内容
        ```scala
        //用户访问动作表
        case class UserVisitAction(
          date: String,//用户点击行为的日期
          user_id: Long,//用户的ID
          session_id: String,//Session的ID
          page_id: Long,//某个页面的ID
          action_time: String,//动作的时间点
          search_keyword: String,//用户搜索的关键词
          click_category_id: Long,//某一个商品品类的ID
          click_product_id: Long,//某一个商品的ID
          order_category_ids: String,//一次订单中所有品类的ID集合
          order_product_ids: String,//一次订单中所有商品的ID集合
          pay_category_ids: String,//一次支付中所有品类的ID集合
          pay_product_ids: String,//一次支付中所有商品的ID集合
          city_id: Long //城市 id
        )
        ```
- dao层，读取数据时，整理数据结构
    - 参考
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/dao/HotCategorySessionAnalysisTop10Dao.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/dao/HotCategorySessionAnalysisTop10Dao.scala)
    - 代码内容
        ```scala
        class HotCategorySessionAnalysisTop10Dao extends TDao{
          def getUserVisitAction(path:String)={
            val fileRDD: RDD[String] = readFile(path)
            fileRDD.map(
              line=>{
                val fields: Array[String] = line.split("_")
                UserVisitAction(
                  fields(0),
                  fields(1).toLong,
                  fields(2),
                  fields(3).toLong,
                  fields(4),
                  fields(5),
                  fields(6).toLong,
                  fields(7).toLong,
                  fields(8),
                  fields(9),
                  fields(10),
                  fields(11),
                  fields(12).toLong,
                )
              }
            )
          }
        }
        ```

- service层
    - 参考
        - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/service/HotCategorySessionAnalysisTop10ServiceBK01.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/service/HotCategorySessionAnalysisTop10ServiceBK01.scala)
    - 代码内容
        ```scala
        override def analysis(data: Any): Array[(String, List[(String, Int)])] = {
          val top10Data = data.asInstanceOf[List[bean.CategoryInfo]]

          val top10DataMap: List[Long]= top10Data.map(data => {
            data.id.toLong
          })

          // 1. 过滤
          // 获取用户行为的数据，品类id必须在top10数据中
          // 对数据进行过滤，需要是 top10Data 中的数据
          val userData: RDD[bean.UserVisitAction] = dao.getUserVisitAction("./input/demo/user_visit_action.txt")
          val filterData = userData.filter(action => {
            if (top10DataMap.contains(action.click_category_id)) {
              true
            } else {
              false
            }
          })

          // 2. 【每个品类】用户【session】的【点击】统计
          // 目标结构(品类_session,1)
          val sessionData: RDD[(String, Int)] = filterData.map(action => {
            (action.click_category_id + "_" + action.session_id, 1)
          }).reduceByKey(_ + _)

          // 3. 对统计后结果做结构转换
          // (品类, (session,SUM))
          val result: Array[(String, List[(String, Int)])] = sessionData.map {
            case (key, count) => {
              val field = key.split("_")
              (field(0), (field(1), count))
            }
          }.groupByKey() // 对品类进行分组
            .mapValues(iter => {
              // 4. 将分组后的数据按照点击量进行排序，并取前10
              iter.toList.sortWith(
                (left, right) => {
                  if (left._2 > right._2) {
                    true
                  } else {
                    false
                  }
                }
              ).take(10)
            }).collect()

          result
        }
        ```

### 优化--使用广播变量
[top](#catalog)
- 优化方式
    - 使用广播变量需求1的结果
- 参考
    - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/service/HotCategorySessionAnalysisTop10Service.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/service/HotCategorySessionAnalysisTop10Service.scala)
- 代码内容
    ```scala
    override def analysis(data: Any): Array[(String, List[(String, Int)])] = {
      val top10Data = data.asInstanceOf[List[bean.CategoryInfo]]

      // 优化1：top10DataMap 需要在闭包中使用，需要转换为广播变量
      val top10DataList: List[Long]= top10Data.map(data => {
        data.id.toLong
      })

      val top10DataBC: Broadcast[List[Long]] = EnvUtil.getEnv().broadcast(top10DataList)

      // 获取用户行为的数据，品类id必须在top10数据中
      // 对数据进行过滤，需要是 top10Data 中的数据
      val userData: RDD[bean.UserVisitAction] = dao.getUserVisitAction("./input/demo/user_visit_action.txt")
      val filterData = userData.filter(action => {
        if (top10DataBC.value.contains(action.click_category_id)) { // 使用广播变量
          true
        } else {
          false
        }
      })

      // 【每个品类】用户【session】的【点击】统计
      // 目标结构(品类_session,1)
      val sessionData: RDD[(String, Int)] = filterData.map(action => {
        (action.click_category_id + "_" + action.session_id, 1)
      }).reduceByKey(_ + _)

      // 对统计后结果做结构转换
      // (品类, (session,SUM))
      val result: Array[(String, List[(String, Int)])] = sessionData.map {
        case (key, count) => {
          val field = key.split("_")
          (field(0), (field(1), count))
        }
      }.groupByKey() // 对品类进行分组
        .mapValues(iter => {
          // 将分组后的数据按照点击量进行排序，并取前10
          iter.toList.sortWith(
            (left, right) => {
              if (left._2 > right._2) {
                true
              } else {
                false
              }
            }
          ).take(10)
        }).collect()

      result
    }
    ```

## 需求3--计算单跳转换率
### 需求3--需求分析
[top](#catalog)
- 如何计算
    1. 统计 page_a-->其他page 的数量，作为分子
    2. 统计 page_a 出现的总数量，作为分母
    3. 计算结果

### 需求3--基本实现
[top](#catalog)
- 参考
    - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/service/PageFlowServiceBK01.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/service/PageFlowServiceBK01.scala)
- 代码内容
    ```scala
    override def analysis() = {
      // 读取数据
      val fileRDD: RDD[bean.UserVisitAction] = dao.getUserVisitAction("./input/demo/user_visit_action.txt")
      fileRDD.cache()
      // 1. 计算分母
      // 将数据整理成 kv 结构: (page_id, 数量)
      val denominator: Map[Long, Int] = fileRDD.map(d => (d.page_id, 1))
        // 分组求和
        .reduceByKey(_ + _)
        .collect()
        .toMap

      // 2. 计算分子
      // 2.1 将数据按照 session_id 分组: (session_id, [ (动作时间，page_id) ,...])
      val numerator: RDD[((Long, Long), Int)] = fileRDD.map(d => (d.session_id, (d.action_time, d.page_id)))
        .groupByKey()
        // 处理每个session_id 分组内的数据
        .mapValues(
          iter => {
            // 2.2 按照 action_time 升序排序
            val pids: List[Long] = iter.toList.sortBy(_._1).map(_._2)

            // 2.3 将数据组合成 px-py 的格式: ((px, py), 1)
            // 按照顺序做出跳转流程
            // 如: 1, 2, 3, 4, 5, 6, 7
            // 跳转顺序为:
            // (1,2), (2,3), (3,4), (4,5), (5,6), (6,7)
            val flows: List[((Long, Long), Int)] = pids.zip(pids.tail).map((_, 1))
            flows
          }
        ).flatMap(_._2) // 去除 session_id
        // 聚合每个 px-py 的数据
        .reduceByKey(_ + _)

      // 3. 计算结果: (px-py) / px
      val result = numerator.map {
        case ((left, right), count) => {
          (s"${left}_${right}", count.toDouble / denominator.get(left).get)
        }
      }
      result
    }
    ```

### 优化--只保留有意义的页面ID
[top](#catalog)
- 参考
    - [src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/service/PageFlowService.scala](src/spark-learn/spark-core/src/main/scala/com/ljs/learn/sparkcore/inaction/service/PageFlowService.scala)

- 代码内容
    ```scala
    override def analysis() = {
      // 读取数据
      val fileRDD: RDD[bean.UserVisitAction] = dao.getUserVisitAction("./input/demo/user_visit_action.txt")
      fileRDD.cache()

      // 有效的页面和跳转流程
      val pageIds: List[Int] = List(1,2,3,4,5,6,7)
      val effectivePageIds: List[Int] = pageIds.init
      val effectiveFlows: List[(Int, Int)] = pageIds.zip(pageIds.tail)

      // 1. 计算分母
      // 【优化1：过滤，只保留有效的页面】
      val denominator: Map[Long, Int] = fileRDD.filter(d=>effectivePageIds.contains(d.page_id))
        // 将数据整理成 kv 结构
        .map(d => (d.page_id, 1))
        // 分组求和
        .reduceByKey(_ + _)
        .collect()
        .toMap

      // 2. 计算分子
      // 将数据按照 session_id 分组
      val numerator: RDD[((Long, Long), Int)] = fileRDD.map(d => (d.session_id, (d.action_time, d.page_id)))
        .groupByKey()
        // 处理每个session_id 分组内的数据
        .mapValues(
          iter => {
            // 按照 action_time 升序排序
            val pids: List[Long] = iter.toList.sortBy(_._1).map(_._2)

            // 将数据组合成 px-py 的格式: ((px, py), 1)
            pids.zip(pids.tail)
              // 【优化2: 过滤，只保留有效的跳转】
              .filter(d=>effectiveFlows.contains(d))
              .map((_, 1))
          }
        ).flatMap(_._2)
        // 聚合每个 px-py 的数据
        .reduceByKey(_ + _)

      // 3. 计算结果: (px-py) / px
      val result = numerator.map {
        case ((left, right), count) => {
          (s"${left}_${right}", count.toDouble / denominator.get(left).get)
        }
      }
      result
    }
    ```


[top](#catalog)