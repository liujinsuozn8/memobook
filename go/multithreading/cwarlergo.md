* 基本数据结构： chapter6\webcrawler\module\data.go
	* 统一接口： Data
	* 请求： Request
		* 构造：NewRequest
	* 响应： Response
		* 构造：NewResponse
	* 条目的类型： Item
* 异常： chapter6\webcrawler\errors\errors.go
	* 异常接口： CrawlerError
	* 接口实现： myCrawlerError
		* 构造： NewCrawlerError
* 组件ID中的序列号生成器
	* chapter6\webcrawler\module\sn.go
	* SNGenertor
* 三大组件的接口设计
	* 抽象类： 
		* chapter6\webcrawler\module\base.go
		* Module
			* 定义组件的基本行为
	* 下载器: 
		* 下载器接口： Downloader
			* chapter6\webcrawler\module\base.go
			* 嵌入Module
			* 两个功能：发送请求和接受相应
				* 方法： Download
	* 分析器： 根据给定的规则分析响应
		* 分析器接口：Analyzer
			* chapter6\webcrawler\module\base.go
			* 嵌入Module
		* 方法： RespParsers() []ParseResponse
			* 会返回当前分析器使用的响应解析函数的列表。
			* ParseResponse，一个函数类型
				* type ParseResponse func(httpResp *http.Response, respDepth uint32) ([]Data, []error)
				* 使使用者可以自定义响应的分析过程
	* 条目处理管道
		* 提供条目处理环境，并控制整体的处理流程，具体步骤有使用者提供
		* 实现单一处理步骤的程序称为条目处理器
		* 接口:Pipeline
			* chapter6\webcrawler\module\base.go
			* 嵌入Module
	* 调度器
		* 功能
			* 通过调度器来启动和停止爬取流程
			* 提供获取实时状态和摘要信息的方法
		* 调度器接口： Scheduler
			* chapter6\webcrawler\scheduler\scheduler.go
		* 方法:Init(requestArgs RequestArgs, dataArgs DataArgs, moduleArgs ModuleArgs) (err error)
			* 初始化调度器
			* 抽象类：
				* chapter6\webcrawler\scheduler\args.go
				* Args
			* RequestArgs 请求相关的参数
				* chapter6\webcrawler\scheduler\args.go
			* DataArgs 数据相关的参数
				* chapter6\webcrawler\scheduler\args.go
				* 四种缓冲器：请求缓冲、响应缓冲、条目缓冲、错误缓冲，分别用来传输对应类型的数据
					* 每种缓冲两个字段：容量、最大值
				* 包含与数据缓冲池相关的字段
					* **一个缓冲池会包含若干个缓冲器**
				* 缓存池和缓冲器都是：并发安全的、队列式的数据传输功能
					* 缓冲池是可伸缩的
			* ModuleArgs 组件相关的参数
				* chapter6\webcrawler\scheduler\args.go
				* 最重要的部分
				* 可以提供3种组件的实例列表
		* 方法:Start(firstHTTPReq *http.Request) (err error)
			* 初始化成功后，就可以使用Start方法启动调用器
			* 该方法值接受首次请求
			* 启动操作可能失败
				* 对于启动，失败原因可能是：有无效的参数、调度器当时的状态不能启动
		* 方法:Stop() (err error)
			* 停止调度器的运行
			* 停止操作可能失败
				* 唯一的失败原因： 状态不对应
				* 停止的方式：向调度器内部和各个组件异步发出停止信号，所以即使有问题也不会体现在Stop的返回值上
		* 方法:Status() Status
			* 用于获取调度器当时的状态
			* chapter6\webcrawler\scheduler\status.go
				* type Status uint8
				* 表示状态的常量
				* 状态转换规则
					* 当调度器处于：正在初始化、正在启动、正在停止状态时，不能有外部触发状态的变化
						* 即此时的调度器不能被初始化、启动、停止
					* 处于未初始化状态时，调度器不能被启动或停止
					* 处于已启动状态时，调度器不能被初始化或启动
					* 仅当调度器处于已启动状态时，才能被停止
		* 方法:ErrorChan() <-chan error
			* 获取错误通道
		* 方法:Idle() bool
			* 判断调度器当前是否是空闲的，
			* 空闲的判断标准
				* 调度器使用的所有组件都正处于空闲
				* 4个缓冲中已经没有任何数据
		* 方法:Summary() SchedSummary
			* 获取描述调度器当时的内部状态的摘要
	
	* 缓冲池和缓冲器
		* 缓冲池和缓冲器本身都是为了扩展通道类型的功能
		* 缓冲器是缓冲池的底层支持，缓冲池是缓冲器的再封装
		* 缓冲池利用它持有的缓冲器实现数据存取的功能，并且可以更具情况自动增减它持有的缓冲器的数量
		* 缓冲池：Pool
			* chapter6\webcrawler\toolkit\buffer\pool.go
			* 当缓冲池已满时，调用Put会阻塞；当缓冲池为空时，调用Get会阻塞，遵守通道类型的行为模式
			* 核心功能动态伸缩
		* 缓冲器
			* 当缓冲池已满时，调用Put会返回nil；当缓冲池为空时，调用Get会返回nil，这样可以使缓冲器的实现足够简单
			* 通过缓冲器来避免通道类型的两个会引发运行时panic的操作：
				* 关闭一个已关闭的通道
				* 向已关闭的通道发送值
			* 通道本身无法获取关闭状态，缓冲器可以通过Closed()方法来获取该状态
	* 多重读取器
		*

* 组件摘要结构类型
	* chapter6\webcrawler\module\base.go
	* SummaryStruct
	* 用于获取组件的结构化摘要信息
* 组件评分计算抽象方法：
	* chapter6\webcrawler\module\score.go
	* type CalculateScore func(counts Counts) uint64
		* Counts 表示组件相关计数的字段
* 组件的注册：
	* 爬虫程序真正启动之前，应该先向组件注册器注册足够的组件实例
	* 组件注册器可以注册、注销、获取某类组件的实例
		* chapter6\webcrawler\module\registrar.go
		* Registrar
