<span id=catalog></span>
- [概述](#概述)
- [数据链路层](#数据链路层)

# 概述
[top](#catalog)
* 三网
	* 电信网络
	* 计算机网络
	* 有限电视网
* 计算机网络= 若干节点(node) + 链路(连接节点)
	* 节点包括：计算机、集线器、交换机、路由器
* 网络连接许多计算机(互连网);互联网通过路由器连接网络，即网络的网络
* 与网络相连的计算机通常称为**主机**
* **internet 和 Internet**
	* internet：互连网，通用名词，泛指由多个计算机网络互连而成的计算机网络
		* 网络之间的**通信协议可以任意选择**，不一定要使用TCP/IP协议
	* Internet：互联网，专用名词，全球最大的、开放的、由众多网络互连而成的特定互连网
		* **采用TCP/IP协议族作为通信协议**
		* 前身是ARPANET
* 互联网基础结构的三个阶段
	1. 分组交换网络**ARPANET**
		* 最初只是一个单个的分组交换网，不是互联网
		* 连接在ARPANET的主机都直接与就近的节点交换及连接
		* 一个单独的网络无法满足所有的通信问题
		* 1983年TCP/IP成为ARPANET的标准协议，使所有使用TCP/IP协议的计算机都能利用互联网通信
		* 1990年关闭
	2. 三级结构的互联网
		* 即美国国家科学基金网：NSFNET
		* 分为：主干网、地区网、校园/企业网
	3. 多层次ISP结构互联网
		* NSFNET逐渐被若干商用互联网主干网替代，政府不再负责互联网的运营
		* ISP：Internet Server Provider 互联网服务提供商
			* ISP拥有IP地址（从互联网管理机构申请）、通信线路、路由器等连网设备 可以到很多的IP地址
				* 通过向ISP缴费，可获得IP的使用权
			* ISP可以向端系统提供服务，也可以拥有一些端系统
		* ISP的层次
			* 主干ISP
				* 由几个专门公司创建并维持，服务面积最大（一般覆盖国家），拥有高速主干网
			* 地区ISP
				* 较小的ISP，可以连接一个或多个主干ISP，位于等级中的第二层，**数据率低**
			* 本地ISP
				* 给用户提供直接的服务（末端用户）
				* 可以连地区ISP，也可以连主干ISP
				* 本地ISP可以是提供互联网服务的公司，也可以是拥有网络的企业
			* 互联网交换点IXP
				* 用来降低分组转发任务的延迟，降低分组转发的费用，使互联网上的数据流量分布更合理
				* 允许两个网络直接相连并交换分组
					* 例如两个地区ISP可以跳过主干ISP，通过IXP来转发分组
				* IXP结构复杂，典型的IXP由一个/多个网络交换机组成，ISP再连接到这些网络交换机的端口上
					* 网络交换机工作在**数据链路层**，使用局域网互联
* 标准化工作
	* ISCO 互联网协会 Internet Society
		* IAB 互联网体系结构委员会
			* IETF 互联网工程部
			* IRTF 互联网研究部
	* RFC文档（Request For Comments 请求评论）
		* 互联网标准都是以RFC的形式在互联网上发表的
		* 并不是所有的RFC都是互联网标准
	* 制定标准的三个阶段
		1. 互联网草案
		2. 建议标准
		3. 互联网标准
* 计算机间通信：主机A的进程X与主机B上的进程Y进行通信
	* 通信的对象：应用层中的应用进程
	* 两种通信方式
		* C/S方式：客户-服务器
			* 客户、服务器即是**通信对象**，客户请求服务，服务器提供服务，**两者本质上都是计算机进程**
				* 客户程序
					* 主动向服务器发起通信(请求)
					* 必须知道服务器程序的地址
					* 对硬件、系统的要求较低
				* 服务器程序
					* **能同时处理多个远程/本地客户**的请求
					* 需要良好的硬件、系统
		
		* P2P方式：对等方式
			* 指两台主机通信时不区分客户和服务器，只要都**运行了对等连接软件（P2P软件）**，就可以进行对等连接通信
			* 本质上仍然是CS方式，但是每台主机既是客户又是服务器
			* **可支持大量对等用户同时工作**
* 电路交换的基本概念
	* 使用交换机来替代多部电话两两相连
	* 电路交换过程:
		* 建立连接：拨号请求连接，接听方摘机
		* 通话：创建一条**专用物理通路**（通信资源，该资源不会被其他用户占用）
		* 释放连接：一方挂机，将通路释放并将资源归还给交换机
	* 电路交换的特点：在通话时间内，通话的两个用户始终占用端到端的通信资源
	* 交换：按某种方式动态的分配传输路线的资源
	* 使用电路交换来传送计算机数据时，线路的传输效率很低
		* 因为计算机数据时突发式的出现在传输线路上的真正用来传输数据的时间很少
		* 已被占用的通信线路资源大部分时间都是空闲的（等待输入，正在运行程序）
* 分组交换(转发)
	* 采用**存储转发技术**
	* 报文
		* 需要发送的整块数据成为报文
		* 发送之前把报文划分成更小的**等长数据段**
	* 分组/包(也称为 数据报、IP数据报)
		* 报文的数据段+首部/包头 = 分组/包
			* 首部包含： 目的地址、源地址等重要的**控制信息**
		* 每个分组在网络中**独立选择传输路经**
		* 分组是网络传输中的数据单元
	* 转发过程：
		1. 路由器接收到一个分组，暂时存储在存储器中（**暂存一个短分组，而不是整个长报文**）
		2. 检查分组的首部，并查找转发表，按照首部的地址找到合适的接口转发出去，把分组交给下一个路由器
		3. 重复此过程直到目标主机
		4. 如果数据量过大，可以多路传送
	* 为了能在整个网络拓扑发生变化时及时更新转发表，各路由器间必须经常交换路由信息
	* 分组转发的优点
		* 传输高效：分组传输的过程中动态分配传输宽带，通信链路是逐段占用的（没有建立连接和释放连接的开销）,适合计算机数据突发式出现的场景
		* 灵活性：每个**分组独立选择**最合适的转发路由
		* 迅速：以分组为传送单位，不建立连接就能向其他主机发送分组
		* 可靠：有保证可靠性的网络协议；分布式多路由的交换网，使网络有很好的生存性
	* 其他问题
		* 分组在各路由存储转发时需要排队，会造成一定的延迟
		* 各分组的包头带来了额外的开销的开销
		* 整个分组交换网需要专门的管理和控制机制
* 互联网的组成
	1. 边缘部分
		* 用户直接使用的部分，由**所有连接在互联网上的主机组成**，可以进行资源共享
		* 这些主机称为**端系统**
			* 端系统可以是电脑、手机，也可以是某个ISP，甚至是网络摄像头
			* 主机通常以低速链路接入核心部分
	2. 核心部分
		* 由大量网络和路由器组成，为边缘部分提供服务（各主机间的连通性和交换）
		* 核心最重要的功能：分组转发（需要依靠路由器）
		* 路由器
			* 一种专用计算机
			* 连接大量的异构网络
			* 路由器间一般通过**高速链路**连接
			* 用来执行**分组交换**（packet switching），即**转发收到的分组**
			* 为了能在整个网络拓扑发生变化时及时更新转发表，各路由器间必须经常交换路由信息
* 距离非常近的多个中央处理器称为**多处理机系统**，不称为计算机网络
* 网络的类别
	* 按作用范围分类
		* 广域网(WAN)
		* 城域网(MAN)
		* 局域网(LAN)
		* 个人区域网(PAN)
	* 按使用者分类
		* 公用网
		* 专用网
	* 接入互联网的网络：**接入网**
		* 不属于互联网的任何组成部分
		* 是`用户<--->第一个路由器`之间的一种网络
* 网络的性能指标
	1. 速率（数据率）: 单位bit/s （bps），往往指额定速率huo标称速率，并非网络上的实际速率
	2. 带宽： 
		* 在频域上：指信号具有的频带宽度，即信号所包含的各种不同频率成分所占据的频率范围，单位：Hz
		* 在时域上：表示单位时间内网络中的某信道所能通过的最高数据率（速率）
		* 带宽越宽，能传输的最高数据率越高
	3. 吞吐量
		* 单位时间内通过某个网络(信道、接口)的实际数据量
		* 受网络的带宽、网络的额定速率的限制
		* 1Gbit/s的网络，该数值时该网络吞吐量的绝对上限值，实际吞吐可能只有100Mbit/s，甚至更低
		* 可以用每秒传送的字节或帧数来表示
	4. 时延
		* 数据从网络的一端到另一端所需的时间
		* 几种不同的时延
			* 发送时延(传输时延)：主机/路由器发送数据帧所需要的时间，
				* 计算方法：发送时延 = 数据帧长度(bit)/发送速率(bit/s)
				* 单位:k=10^3, M=10^6, G=10^9
			* 传播时延：电磁波在信道中传播一定距离需要花费的时间
				* 计算方法：传播时延 = 信道长度(m)/电磁波在信道上的传播速率(m/s)
				* 传播速率的单位m/s：每秒传播多少公里
			* 处理延时：路由器/主机在接收到分组时需要花费一些时间进行处理
				* 处理包括:分组首部的分析，数据的提取，数据检验，查找路由邓
			* 排队时延：
				* 输入队列中等待
				* 输出队列中等待
				* 网络通信量很大时会发生队列溢出，使分组丢失，相当于排队时延**无穷大**
		* 总时延 = 发送时延 + 传播时延 + 处理延时 + 排队时延
		* 网络中通信量过大时，路由的处理时延和排队时延会大大增加
	5. 时延带宽积
		* 时延带宽积 = 传播时延 * 带宽
			* 结果表示：当第一个发送的比特到达接收端时，已经发送了时延带宽积个比特，这些比特正在链路上移动
		* 以比特为单位的链路长度：表示从发送端发出，但尚未到达接收端的比特
		* 对于一条正在传输数据的链路，只有管道中充满比特时，链路才得到充分的利用
	6. 往返时间RTT(Round-Trip Time)
		* 数据传输时双向交互的时间：A-->B发送数据，B接收，B-->A发送确认信息（RTT）
		* 往返时间包含：各中间结点的处理时延、排队时延、转发数据时的发送时延
		* 有效数据率 = 数据长度 / (发送时间 +　ＲＴＴ)
	7. 利用率
		* 两种利用率
			* 信道利用率：某信道有百分之几的时间是被利用的
			* 网络利用率：全网络的信道利用率的加权平均
		* 并非利用率越高越好：某信道利用率增大时，引起的时延也会增加（各节点的排队时延）
		* 利用率公式： D = D_0 / (1-U)
			* D 表示网络时延
			* D_0 表示网络空闲时的时延
			* U 表示网络的利用率
			* **当网络的利用率达到容量的1/2时，时延加倍；网络利用接近1时，网络的时延趋于无穷大（信道/网络利用率过高会产生非常大的时延）**
			* 为了避免高利用率，一些较大主干网的ISP通常控制信道利用率不超过50%，如果超过了就准备扩容，**增加线路的带宽**
* 网络体系结构
	* 定义：计算机网络的各层及其协议的集合，即计算机网络及其构件能完成的功能的精确定义
	* 主机间通信需要做的工作
		1. 激活通路：发起通信的计算机**激活**通信通路，保证数据能在这条通路上正确发送和接收
		2. 如何识别：高速网络如何识别接收端
		3. 检查连接：检查接收端是否开机，网络连接是否正常
		4. 做好接收准备：接收端的文件管理程序是否已做好接收、存储文件的准备工作
		5. 兼容问题：若干两主机的文件格式不兼容，则一台计算机需要完成格式转换功能
		6. 异常处理：如果数据传输过程中出现异常，需要有可靠的措施保证接收端能接收到正确的文件
	* 开放系统互连基本参考模型 OSI/RM (或OSI) (Open System Interconnection Reference Model)
		* OSI/RM本身是一个抽象概念
		* 使各种计算机在世界范围内互联成网的标准框架
		* 在一个系统中不是所有的部分都与互连有关，OSI/RM把与互连无关的部分除外，仅考虑与互联有关的部分
		* 1983年形成了OSI/RM的正式文件，即ISO 7498国际标准---**七层协议的体系结构**
		* TCP/IP早已抢占了市场且未使用OSI/RM标准，所以**OSI失败了**
	* (网络)协议与层次划分
		* 网络协议
			* 规定了所交换的**数据的格式**以及有关的**同步问题**---为网络中的数据交换而建立的规则/标准/约定
			* 水平性：协议是控制两个对等实体或多个实体进行通信的规则的集合
			* 下层的协议对上层是透明的
			* 协议必须事先考虑所有不利条件--即各种异常的解决方案
			* 网络协议的三要素
				1. 语法:**数据**与**控制信息**的**结构或格式**
				2. 语义：控制信息要完成的动作与如何响应(发送端与接收端所要完成的操作)
				3. 同步：事件实现顺序的详细说明
		* 层次化的优点
			* 各层之间是独立的
			* 灵活性高
			* 结构可分割
			* 有利于促进标准化
		* 层次化的缺点
			* 有些功能会在不同层中重复出现，造成额外的开销
		
* 5层协议的体系结构
	* TCP/IP的体系结构
		* 应用层(各种应用层协议：TELNET，FTP，SMTP等)
		* 运输层(TCP 或 UDP)
		* 网际层IP
		* 网络接口层(该层没有什么实际的内容)
	* 各对等层间使用的数据单位可能不相同，但都可以统称为**协议数据单元PDU** (Protocol Data Unit)
	* 5层协议(OSI七层和TCP/IP的折中)
		* 5.应用层
			* 定义了: **应用进程间**通信和交互的规则
			* 交互的数据单元称为:**报文** (首部会添加控制信息)
			* 任务：通过应用进程间的交互来完成特定网络应用
			* 不同的网络应用需要有不同的应用层协议
				* 协议很多:域名系统DNS， 支持万维网应用的HTTP协议，支持电子邮件的SMTP协议 等等
		* 4.运输层 TCP
			* 任务：在两台主机的**进程间**进行通信时，提供**通用的数据传输服务**
				* 通用性：不针对某种特定网络应用
				* 复用功能：多个**应用层进程**可同时使用运输层的服务
				* 分用功能：运输层将接收到的信息分别交付给**应用层的相应进程**
			* 运输层的两种协议
				* 传输控制协议 TCP (Transmission Control Protocol)
					* 提供面向连接的、可靠的数据传输服务。
					* 数据传输的单位也是**报文** (首部会再添加控制信息)
				* 用户数据报协议 UDP (User Datagram Protocol)
					* 提供无连接的、尽最大努力的数据传输服务
					* 数据传输单位：用户数据报？？？？？
		* 3.网络层 IP (和网际层，IP层 是同义词)
			* 数据单位
				* 处理数据单位：报文或用户数据报
				* 发送数据单位：分组/包/IP数据报
			* 任务：负责为**不同主机**提供通信服务
				* 发送数据（**数据怎么发**）：将运输层产生的报文或用户数据报**封装成分组/包**进行传送(由于使用IP协议，分组也称为IP数据报)
				* 选择路由（**数据怎么传**）：选择合适的路由，使分组能通过网络中的路由器找到目标主机
			* 使用的协议：
				* 无连接的**网际协议IP(Internet Protocol)**
				* 多种路由选择协议
		* 2.数据链路层
			* 任务：将网络层的IP数据报组装成**桢**，在两个相邻节点间的链路上传送
			* 每一帧 = 首部控制信息 + 数据 + 尾部控制信息
				* 控制信息包括： 同步信息、地址信息、差错控制
				* 控制信息的作用
					* 通过控制信息可知：每个帧中**数据的起始和结束位置**，使数据链路层可以从帧中提取数据，上交网络层
					* 检测帧中有无异常
						* 如果有异常，可以丢弃帧，减少网络资源的浪费
						* 如果要改错，则需要依靠可靠的传输协议来纠正出现的错误，这将使数据链路层的协议更复杂
		* 1.物理层
			* 数据单位：比特
			* 任务：
				* 传送比特流
				* 发1接1，发0接0，需要考虑1和0所使用的电压
				* 接收方如何识别 0 和 1
				* 确定连接电缆的插头应该有多少引脚、各引脚如何连接
				
		* 0.物理层之下的第0层--物理媒体
			* 传递信息所利用的物理媒体，如：双绞线、同轴电缆、光缆、无线信道等
			
* 协议、服务、服务访问点
	* 协议是**水平的**：负责对等实体间的通信
	* 服务是**垂直的**
		* 服务是由下向上通过层间接口提供的 --- 只有那些被高层实体使用的功能才能成为服务
		* 上层使用下层提供的服务时必须与下层交换一些命令，这些命令在OSI称为**服务原语**
	* 服务访问点SAP (Server Access Point)
		* 指同一系统中相邻两层的实体进行交互(交换信息)的地方
		* 一个抽象概念，相当于一个**逻辑接口**
		* OSI把层间的数据交换单位称为：**服务数据单元SDU** (Server Data Unit)
			* 多个SDU可以合成一个PDU，一个SDU也可以划分成多个PDU
			
* TCP/IP的体系结构
	* ？？？？？？？？？？？？？？？？？

# 数据链路层
[top](#catalog)
* 数据链路层使用的两种信道：
	* 点对点信道：这种信道使用一对一的点对点通信方式
	* 广播信道：使用一对多的广播通信方式
		* 广播信道上连接的主机很多，因此必须使用专用的共享信道协议来协调这些主机的数据发送
* 使用点对点信道的数据链路层
	* 简化的三层模型 ![]() ??????
	* 链路和数据链路
		* 链路：从一个节点到相邻节点的一段物理线路(有线或无线)
		* 数据链路：实现通信协议的硬件和软件 + 链路
			* 常用**网络适配器(既有硬件，又有软件)**来实现这些协议。一般的网络适配器都包括了**数据链路层和物理层**
	* 帧：数据链路层的**协议数据单元**
	* 在数据链路层传输时，不需要考虑物理层如何实现比特数据的传输细节，可以简单假设是在两个数据链路层之间水平传输的
	* 通信的步骤
		1. 结点A的数据链路层把网络层交下来的IP数据报**添加首部和尾部封装成帧**
		2. 结点A把封装好的帧发送给结点B的数据链路层
		3. 若B的数据链路层接收哦到的帧无异常，则从帧中提取IP数据报上交B的网络层；否则丢弃这个帧
* 三个基本问题：各种数据链路层协议的三个共同问题
	1. 封装成帧： 
		* 首部+IP数据包+尾部 的长度 = 帧长 ![]()??????
		* 首部、尾部的作用：帧定界--确定帧的界限
		* 帧的数据部分长度上限--最大传送单元MTU(Maximum Transfer Unit)
			* 不同的协议有不同的规定
			* 当数据是可打印的ASCII码组成的文本文件时，帧定界可以使用特殊的**帧定界符**
				* SOH表示帧的首部开始，EOF表示帧的结束； 编码分别是SOH=01,EOT=04
			* 在数据部分，任何的**8比特**组合不允许和帧定界符相同，否则会出现帧定界错误
	2. 透明传输： 无论传输什么样的数据，接收端都能无差错的解析
		* 当帧是由ASCII组成的，数据部分不会出现帧定界符，即从键盘输入任何字符都可以放在帧中传输，这样的传输就是**透明传输**
		* 当帧不是是由ASCII组成的，如果数据中的某个字节的二进制代码恰好和帧定界符相同，将会错误的确定边界，剩余的数据会被丢弃(因为找不到SOH)，这种情况不是透明传输
		* 解决方法
			* 字节填充/字符填充
				* 发送端：发送前在SOH/EOT前面插入一个转义字符ESC(1B= 0001 1011 16进制)
					* 如果转义字符也出现在数据中，则在转义字符前添加转义字符
				* 接收端：数据送到网络层之前删除ESC
					* 如果在数据中接受到两个连续的转义字符时，删除前面的一个
				* 实例![]()????????
	3. 差错检测
		* 比特差错：物理层传输时 1变0、0变1
		* 除了比特差错外还有一些其他的差错，但这里只讨论比特差错
		* 误码率BER(Bit Error Rate)：**在一段时间内，传输错误的比特所占的比率**
			* 误码率与信噪比有很大关系，如果提高信噪比，就可以减小误码率
		* 循环冗余校验CRC (Cyclic Redundancy Check)
			* 数据链路层广泛使用的差错检测技术
			* 原理
				* 
				发送端：数据-->分组，每组k比特-->
						数据M=101 001 ， k=6， 在M的后面添加检测用的n位冗余码
				n为冗余码的计算方法：
					模2--左移，在后面加n个数，得到k+n位数
					接收时，除以双方事先商量好的长度为n+1为的除数P，得到商Q、余数R(n位，比P少一位)
					
					
***************	
