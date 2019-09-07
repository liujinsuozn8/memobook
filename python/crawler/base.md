<span id="catalog"></span>
- [爬虫基础](#爬虫基础)
- [Robots协议](#robots协议)
- [基本库的使用](#基本库的使用)

# 爬虫基础
[top](#catalog)
* 请求
	* 请求方法(request method)
		* 常用请求方法：
			* GET
				* 请求页面，并返回页面内容
				* 请求参数包含在URL中
				* 请求参数的数据量最多只有1024B
			* POST
				* 大多用于提交表单或上传文件
				* 请求参数不会包含在URL中，数据通过表单形式传输，会包含在请求体中
				* 请求参数的数据量没有限制
	* 请求的地址(request URL)
		* 请求的网址，即URL
	* 请求头(request headers)
		* 用来说明**服务器要使用的附加信息**，重要的信息包括：**Cookie、Referer、User-Agent等**
		* 写爬虫时，大部分情况下都需要设定请求头
		* 常用头信息
			* Accept：请求报头域，用于**指定客户端可以接受哪些类型的信息**
			* Accept-Language：**指定客户端可接受的语言类型**
			* Accept-Encoding：**指定客户端可以接受的内容编码**
			* Host:用于指定请求资源的主机IP和端口号
				* 内容为请求URL的原始服务器或网关的位置
			* Cookies：网站为了辨别用汇进西功能会话跟踪而存储在用户本地的数据
				* 主要功能是为了维持当前访问的会话
				* cookies中有信息标识了我们所对应的服务器的会话，每次浏览器在请求该站点的页面时，都会在请求头中加上Cookies并将其发送给服务器
				* 服务器通过cookies识别用户，并查询用户的状态，并返回相应的网页内容
			* Referer：标识这个请求是从哪个页面发过来的，服务器可以拿到这一信息并作相应的处理，如作来源统计、防盗链处理等
			* User-Agent：一个特殊的字符串头，可以**使服务器识别客户使用的操作系统及版本、浏览器及版本等信息**
				* **作爬虫时加上UA，可以伪装为浏览器；如果不加，很可能会被识别为爬虫**
			* Content-Type：互联网类型/MIME类型
				* 在HTTP协议消息头中，用来表示具体请求中的媒体类型信息
					* text/html 代表HTML格式
					* image/gif 代表GIF图片
					* application/json 代表JSON类型
					* 参照表：http://tool.oschina.net/commons
				*　ＰＯＳＴ提交数据方式的关系
				
				|Ｃｏｎｔｅｎｔ－Ｔｙｐｅ|提交数据的方式|
				|-|-|
				|ａｐｐｌｉｃａｔｉｏｎ/ｘ－ｗｗｗ|表单数据|
				|ｍｕｌｔｉｐａｒｔ/ｆｏｒｍ|表单文件上传|
				|application/json|序列化ＪＳＯＮ数据|
				|ｔｅｘｔ/ｘｍｌ|ＸＭＬ数据|
	* 请求体(request body)
		* 对于POST请求，包含的是请求的表单数据
		* 对于GET请求，是空
* 响应
	* 由服务端返回客户端
	* 响应状态码(Response Status Code)
		* 表示服务器的响应状态
		* 常用状态：
			* 200 服务器正常响应
			* 404 页面未找到
			* 500 服务器内部发生错误
	* 响应头(Response Headers)
		* 包含了服务器对请求的应答信息
		* 常用头信息
			* Date:响应产生的时间
			* LastModified:指定资源的最后修改时间
			* Server:包含服务器的信息,如名称版本等
			* Content-Encoding:指定响应内容的编码
			* Content-Type:返回的媒体类型,与请求头中的相同
			* Set-Cookie:设置Cookies,告知浏览器需要将此内容放在Cookies中,**在下次请求时,携带Cookies请求**
			* Expires:指定响应的过期时间,可以使代理服务器或浏览器将加载的内容更新到缓存中。再次访问时，直接从缓存中加载，降低服务器负载，缩短加载时间
	* 响应体(Response Body)
		* 包含响应的正文数据
			* 请求网页时，响应体就是网页的HTML代码
			× 请求图片时，响应体就是图片的二进制数据
		* **爬虫主要解析的内容就是请求体**
		
* DOM (Document Object Model) 文档对象模型
	* 定义了访问HTML和XML文档的标准
	* 不依赖于平台和语言的接口，允许程序和脚本动态的访问和更新文档的内容、结构、样式
	* DOM标准被分为三个不同的部分
		* 核心DOM：针对任何结构化文档的标准模型
		* XML DOM:针对DOM文档的标准模型
		* HTML DOM：针对HTML文档的标准模型
	× HTML DOM 标准
		* 整个文档是一个文档节点
		* 每个HTML元素是元素节点
		* HTML元素内的文本是文本节点
		* 每个HTML属性是属性节点
		* 注释是注释节点
		* HTML DOM将HTML文档视作树结构，这种结构称为**节点树**
			* 树中的节点彼此拥有层级关系
		* 数中的节点都可以通过JS访问，所有节点元素均可以被修改、创建、删除
		* 节点
	
* 网页基础
	* 网页的组成
		* HTML(骨架)：超文本标记语言
		* CSS(皮肤)：层叠样式表，唯一的网页页面排版样式标准
			* 一般会统一定制规则，并写入.css文件中
			× 在HTML中，需要用link标签引入.css文件
		* JS(肌肉)
			* 在HTML中通过script标签引入：<script src="jquery..."></script>
	* 网页的结构
		```
		<!DOCTYPE html>  <!--定义文档类型-->
		<html>
		<head> <!--网页头定义，包含一些页面的配置和引用-->
		<meta charset="UTF-8"> <!--指定网页的编码为UTF-8-->
		<title> This is a Demo</title> <!--定义网页的标题，会显示在网页的选项卡中，不会显示在正文中-->
		</head>
		<body> <!--网页体定义-->
		<div id="container"> 
		<div class="wrapper"> <!--wrapper 一个常用属性-->
		<h2 class="title">Hello World</h2>
		<p class="text">hello,this is a p</p>
		</div>
		</div>
		</body>
		</html>
		```
	* 节点树及节点间的关系
		* HTML中，所有标签的内容都是节点，这些节点构成了一个**HTML DOM树**
* 爬虫的基本原理
	* **爬虫就是获取网页并提取和保存信息的自动化程序**
		* 获取网页
			* 获取网页的源代码: 向网站的服务器发送一个请求，返回的**响应体就是网页源代码**
			* 主要问题：构造一个请求并发送给服务器
		* 提取信息
			* 可以使用正则表达式提取
		* 保存数据
	* 能抓取怎样的数据
		× 只要是基于HTTP或HTTPS协议，有各自对应的URL的数据，爬虫就可以抓取
		* 最常抓取的是：HTML源代码
		* 有些网页返回的是JSON字符串
		* 二进制数据：图片、视频、音频
		* 还有各种扩展名的文件，如css文件、js、配置文件
	* js渲染的页面
		* 有些页面实际上是js渲染出来的，HTML源代码只是一个框架
		* 如果使用urllib 或 requests等库请求这类页面时，只能得到HTML代码，不会继续加载JS文件，会导致看不到浏览器的真正内容
			* 这种情况可以：分析后台Ajax接口， 使用Selenium、Splash这样的库来模拟JS渲染
* 会话和Cookies
	* 静态网页
		* 网页内容是HTML代码编写的，文字、图片等内容均通过写好的HTNL代码来指定
		* 优点：加载速度快，编写简单
		* 缺点：可维护性差，不能根据URL变化的现实内容
	* 动态网页
	* 无状态HTTP
		* 指HTTP协议**对事务处理是没有记忆力的，即服务器不知道客户端是什么状态**
			* 服务器只负责根据请求返回对应的响应，不会记录状态变化
			* 如果要继续处理信息，前面的信息必须重传，这回导致只要重复传递前面的请求，才能获取后续响应，但这样太浪费资源
		*　通过会话和Cookies来保持HTTP连接的状态，来避免请求的重传	
	*　会话
		*　**会话在服务端**，用会话对象来存储特定用户会话所需的属性及配置信息
		*　用户在应用程序的页面之间跳转时，存储在会话对象中的变量将不会丢失，
		*　当用户情趣页面时，如果该用户还没有会话，则服务器将自动创建一个会话对象
		*　会话过期或被放弃后，服务器将终止该会话
		* 关闭浏览器不会导致会话被删除
	*　Cookies
		* **Cookies保存在客户端，即浏览器**		
		*　会话维持（利用Cookies保持状态）
			* 客户端**第一次**请求服务器 -->服务器返回响应：响应头{Set-Cookie(用来标记用户)}--> 客户端浏览器保存Cookies
			* 再次请求该网站--> Cookies{会话ID信息} 放入请求头 --> 服务器 --> 服务器检查Cookies --> 获取会话 --> 通过会话辨认用户状态
			* 如果Cookies无效或会话已过期，将不能访问对应状态的页面，**此时将会收到错误的响应或跳转到初始页面**
		* 属性结构
			* Name:Cookie名称，一旦创建则不可更改
			* Value：Cookie的值，如果值为Unicode字符，需要字符编码；如果值为二进制数据，则需要使用BASE64编码
			* Domain：可以访问该Cookie的域名，只有以指定值结尾的域名才能访问Cookie
			* Max Age： Cookie的失效时间，单位为秒。
				* 可以和Expires一起使用，通过它可以计算出有效时间
				* 如果为正数，则Max Age秒之后失效
				* 如果为负数，则关闭浏览器时失效，浏览器也不会以任何形式保存该Cookie
			* Path：Cookie的使用路径
				* 如果值是：`/path/`，则只有/path/的页面可以访问该Cookies
				* 如果值是：`/`，则本域名下的所有页面都可以访问该Cookie
			* Size：Cookie的大小
			* HttpOnly：若属性为True，则只有HTTP头中会带有此Cookie的信息，并且不能通过`document.cookie`来访问此Cookie
			* Secure:该Cookie是否仅被使用安全协议传输，
				* 安全协议包括：HTTPS、SSL等，在网络上传输数据之前先将数据加密
				× 默认false
		* 会话Cookie： 把Cookie放在浏览器内存里，**浏览器在关闭之后该Cookie失效**
		* 持久Cookie： 将Cookie保存到客户端的硬盘中，下次还可以继续使用，用于长久保持用户登录状态
			
		*　**对于需要登录才能执行后续操作的页面，可以将登录成功后获取的Cookies放在请求头里面直接请求，而不必重新模拟登录**
	* 会话和Cookies相互配合，一个处于客户端，一个处于服务端，相互配合，实现各种页面状态的控制
* 代理的基本原理(proxy server)
	* 封IP：服务器会检测某个IP在单位时间内的请求次数，如果请求次数超过了某个阀值，就会直接拒绝服务
	* 防止IP被封：伪装自己的IP，让服务器识别不出是由本级发起的请求
	* 可以使用代理来伪装IP
	* 基本原理
		* 功能：代理网络用户去取得网络信息
			* 正常的请求流程：本机<-->服务器
			* 使用代理的流程：本机<-->代理服务器<-->服务器
		* 作用
			* 可以访问本级IP不能访问的站点
			* 提高访问速度：通常代理会设置较大的硬盘缓存去，当有外界信息通过时会保存到缓存区。当其他用户再访问相同的信息是，则直接由缓冲区中信息返回给用户
			*　隐藏真实IP
	* 代理分类
		* 根据协议区分
			* FTP代理服务器：主要用于访问FTP服务器，可以上传、下载、缓存，端口一般为：21、2121
			* HTTP代理服务器：主要用于访问网页，用内容过滤、缓存功能，端口一般为：80、8080、3128
			* SSL/TLS代理：主要用于加密网站，有SSL、TLS加密功能(最高支持128位加密强度)，端口一般为:443
			* RTSP代理：主要用于访问Real流媒体服务器，有缓存功能，端口一般为：554
			* Telnet代理：主要用于telnet远程控制，端口一般为23
			* POP3/SMTP代理：主要用于POP3/SMTP方式手法邮件，有缓存功能，端口一般为：110/25
			* SOCKS代理：单纯传递数据包，不关系具体协议和用法，所以速度快，有缓存功能，端口一般为：1080
				* SOCKS4：支持TCP
				* SOCKS5：支持TCP和UDP，支持各种身份验证机制、服务器端域名解析
		* 匿名程度区分
			*　高度匿名代理：直接转发数据包，在服务端看来是一个普通客户端，而记录的IP是代理服务器的IP
			*　普通匿名代理：会在数据包上做改动，然后再转发。服务端可能会发现这是个代理服务器，甚至追查到客户端的真实IP
				*　代理服务器会加入的HTTP头有：HTTP_VIA和HTTP_X_FORWARDED_FOR
			* 透明代理：改动数据包，并告诉服务器真实IP。这种代理只是用缓存技术提高浏览速度，体高安全性
			* 间谍代理：指组织或个人创建的用于记录用户传输的数据，然后进行研究、、监控等目的的代理服务器
	* 常见代理设置
		* 免费/付费代理
		* ADSL拨号：拨一次号换一次IP，稳定性高

# Robots协议
[top](#catalog)
* 爬虫协议/机器人协议，全名：网络爬虫排除标准(Robots Exclusion Protocol)
* 用来告诉爬虫和搜索引擎，哪些页面可以抓取，哪些页面不可以抓取
* 通常是一个robots.txt文本文件，一般放在网络根目录下
	* 一个搜索爬虫访问一个站点是，会首先检查这个站点根目录下是否存在robots.txt文件。
		* 如果存在，爬虫会根据文件中定义的爬取范围来爬取
		* 如果不存在，爬虫会访问所有可访问的页面
* robots.txt文件
	* 样例
		```python
		User-agent: * 
		Disallow: / 
		Allow: /public/
		```
	* User-agent：有效的爬虫名称
	* Disallow：不允许被爬取的页面。如果为空，则所有页面都可以爬取
	* Allow：可以抓取的页面
* 常见的搜索爬虫的名称及其对应网站

|爬虫名称|名称|网站|
|-|-|-|
|BaiduSpider|百度|www.baidu.com|
|Googlebot|谷歌|www.google.com|
|360Spider|360搜索|www.so.com|
|YodaoBot|有道|www.youdao.com|
|ia_archiver|Alexa|www.alexa.cn|
|Scooter|altavista|www.altavista.com|

# 基本库的使用
[top](#catalog)
* urllib
	* request： 最基本的HTTP请求模块，可以模拟发送请求
		* urlopen(url):返回一个HTTPResponse类型的对象
			```python
			url = 'http://httpbin.org/post'
			data = bytes(up.urlencode({'word':'hello'}), encoding='utf8')
			response = ur.urlopen(url, data=data)
			print(response.read().decode('utf-8'))
			```
			* 参数
				* data： 使用data后，请求方式变为POST。参数需要转化成字节流
					* 参数字典的转化：urllib.parse.urlencode({key:value})
				* timeout:超时时间，不指定则使用全局默认时间，支持：HTTP、HTTPS、FTP请求
			* HTTPResponse包含的方法
				* read()
				* readinto()
				* getheader(name)
				* getheaders()
				* fileno()
			* HTTPResponse包含的属性
				* msg
				* version
				* status
				* reason
				* debuglevel
				* closed
			
		* Request(url, data=None, headers={}, origin_req_host=None, unverifiable=False, method=None)： 
			```python
			# Request传入多个参数
			url = 'http://httpbin.org/post'
			headers = {
				'User-Agent':'Mozilla/4.0 (compatible; MSIE 5.5; Windows NT)',
				'Host':'httpbin.org'
			}
			dict = {'name':'Germey'}
			data = bytes(up.urlencode(dict), encoding='utf8')
			req = ur.Request(url, data=data, headers=headers, method='POST')
			response = ur.urlopen(req)
			print(response.read().decode('utf-8'))
			```
			* 通过构造数据结构，将请求独立成一个对象，可以更灵活的配置参数
			* data：必须是bytes字符流,如果是参数字典需要使用`urllib.parse.urlencode({key:value})`转化
			* headers：请求头，一个字典。
				* 构造请求时可以直接构造，也可以通过**调用请求实例的add_header()方法添加**
				* User-Agent的默认值是：`Python-urllib`,可以通过该参数来伪装浏览器
			* origin_req_host:请求方的host名称或者IP地址
			* unverifiable:表示该请求是否是无法验证的，默认`False`，指用户没有足够权限来选择接收这个请求的结果
				* 如果没有自动抓取图像的权限，应该使用True
			* method:请求方法，GET、POST等
		* Handler：完成如Cookie、代理设置等高级操作
			* BaseHandler：所有其他Handler的父类
				* 提供了基本的方法：default_open(),protocol_request()
			* HTTPDefaultErrorHandler:用于处理HTTp响应错误，错误都会抛出HTTPError类型的异常
			* HTTPRedirectHandler:用于处理重定向
			* HTTPCookieProcess:用于处理Cookies
			* ProxyHandler:用于设置代理，默认代理为空
			* HTTPPasswordMgr:用于管理密码，它维护了用户名和密码表
			* HTTPBasicAuthHandler:用于管理认证，如果一个链接打开时需要认证，可以使用该类解决
			* 其他参考：htts://docs.python.org/3/library/urllib.request.html#urllib.request.BaseHandler
		* OpenerDirector:可以进行更深层的配置，实现更高级的功能
			* Cookie
				* 输出cookie
					```python
					import http.cookiejar
					cookie = http.cookiejar.CookieJar()
					handler = ur.HTTPCookieProcessor(cookie)
					opener = ur.build_opener(handler)
					response = opener.open('http://www.baidu.com')
					for item in cookie:
						print(f'{item.name} = {item.value}')
					```
				* 保存cookie到文件
					```python
					import http.cookiejar
					filename = 'cookie.txt'
					# MozillaCookieJar是CookieJar的子类，用来处理和文件相关的操作
					#可以读取、保存cookie，可以将Cookies保存成Mozilla型浏览器的Cookies格式。
					cookie = http.cookiejar.MozillaCookieJar(filename)
					# 同样可以读取和保存Cookies，但是会保存成libwww-perl(LWP)格式的Cookies文件
					#cookie = http.cookiejar.LWPCookieJar(filename)
					handler = ur.HTTPCookieProcessor(cookie)
					opener = ur.build_opener(handler)
					response = opener.open('http://www.baidu.com')
					cookie.save(ignore_discard=True, ignore_expires=True)
					```
				* 读取cookie
					```python
					import http.cookiejar
					filename = 'cookie.txt'
					cookie = http.cookiejar.LWPCookieJar()
					cookie.load(filename, ignore_discard=True, ignore_expires=True)
					handler = ur.HTTPCookieProcessor(cookie)
					opener = ur.build_opener(handler)
					response = opener.open('http://www.baidu.com')
					print(response.read().decode('utf-8'))
					```
	* error：异常处理
		* URLError: error模块的基类，request模块的异常都可以通过这个类来捕获
			* e.reason:返回错误原因
				```python
				import urllib.error as ue
				try:
					response = ur.urlopen('https:www.baidu.cn')
				except ue.URLError as e:
					print(e.reason)
				```
		* HTTPError:URLError的子类，用来处理HTTP请求错误
			* 属性
				* code：返回HTTP状态码，如404表示网页不存在，500表示服务器内部错误
				* reason：返回错误原因
				* headers：返回请求头
				```python
				import urllib.error as ue
				#先捕获子类异常，再捕获父类异常
				try:
					response = ur.urlopen('https://www.baidu.c', timeout=1)
				except ue.HTTPError as e:
					print(e.reason, e.code, e.headers, sep='\n')
				except ue.URLError as e:
					print(e.reason)
				```
			* 返回的reason是对象
				```python
				import urllib.error as ue
				import socket
				try:
					response = ur.urlopen('https://www.baidu.c', timeout=1)
				except ue.URLError as e:
					print(type(e.reason)) #<class 'socket.timeout'>
					if isinstance(e.reason, socket.timeout):
						print('timeout')
				```
	* parse：工具模块，提供了许多URL处理方法(解析链接)，如：拆分、解析、合并等
		* 支持的URL协议：
			>file, ftp, gopher, hdl, http, https, imap, mailto, mms
			news, nntp, prospero, rsync, rtsp, rtspu, sftp, sip, sips
			snews, svn, svn+ssh, telnet, wais
		* urlparse(urlstring, scheme='', allow_fragments=True):实现url的识别和分段
			* 分段标准：scheme://netloc/path;params?query#fragment
				```python
				url = 'http://www.baidu.com/index.html;user?id=5#comment'
				result = up.urlparse(url)
				#ParseResult(scheme='http', netloc='www.baidu.com', 
				#	path='/index.html', params='user', query='id=5', 
				#	fragment='comment')

				# scheme协议，netloc域名，path访问路径，params参数，
				# query查询条件，fragment锚点(用于直接定位页面内部的下拉位置)
				```
			* scheme:默认协议，如果url中没有协议信息，会将该参数作为默认协议
			* allow_fragments:是否忽略`fragments`，如果设为False，会被忽略，将会被解析为：path、params、query的一部分
				```python
				url = 'www.baidu.com/index.html;user?id=5#comment'
				result = up.urlparse(url, allow_fragments=False)
				#ParseResult(scheme='', netloc='', path='www.baidu.com/index.html'
				#	, params='user', query='id=5#comment', fragment='')
				```
		* urlunparse():**接受6个参数，合成url**
			```python
			data =['http', 'www.baidu.com', 'index.html', 'user', 'a=6', 'comment']
			print(up.urlunparse(data))
			# http://www.baidu.com/index.html;user?a=6#comment
			```
		* urlsplit():与urlparse类似，只返回5个结果，其中params会合并带path中
			```python
			url = 'www.baidu.com/index.html;user?id=5#comment'
			result = up.urlsplit(url)
			#SplitResult(scheme='', netloc='', path='www.baidu.com/index.html;user', 
			#		query='id=5', fragment='comment')
			```
		* urlunsplit():**接受5个参数，合成url**
		* urljoin():提供一个base_url作为第一个参数，新链接作为第二个参数，自动分析base_url的scheme、netloc、path，对新链接的缺失部分进行补充
			* scheme、netloc、path在新链接中存在，则用新链接的；如果不存在，则用base_url中的
			* base_url中的params、query、fragment是无效的
		* urlencode():通过字典来构造GET请求的参数
			```python
			params = {'name':'xxx', 'age':22}
			ps = up.urlencode(params)
			#name=xxx&age=22
			```
		* parse_qs():将请求参数转换为字典
			```python
			ps = 'name=xxx&age=22'
			params = up.parse_qs(ps)
			#{'name': ['xxx'], 'age': ['22']}
			```
		* parse_qsl():将参数转换为元祖列表
			```python
			ps = 'name=xxx&age=22'
			params = up.parse_qsl(ps)
			#[('name', 'xxx'), ('age', '22')]
			```
		* quote():将文字转换为URL编码
			```python
			k='中文'
			q=up.quote(k)
			```
		* unquote():对url进行解码
	* robotparser：分析Robots协议，主要用来识别网站的robots.txt文件，判断网站是否可爬
		* set_url():设置robots.txt文件的位置
			* 两种设置方法：`RobotFileParser('....')`，或`rp=RobotFileParser();rp.set_url('...')`
			```python
			import urllib.robotparser as urobot
			rp = urobot.RobotFileParser()
			rp.set_url('http://www.jianshu.com/robots.txt')
			rp.read()
			print(rp.can_fetch('*','http://www.jianshu.com/p/b67554025d7d'))
			```
		* read():读取文件，并分析。该方法不返回任何内容。如果不调用，后续的判断结构都是False
		* parse():传入的是文件某些行的内容，方法会按照robot.txt文件的规则进行解析
		* can_fetch(User-agent, 抓取的URL):返回的是该URL是否可以抓取
		* mtime():返回上次抓取和分析robot.txt的时间。这对于长时间分析和抓取的搜索爬虫是 很有必要的，你可能需要定期检查来抓取最新的robots.txt。？？？？？？
		* modified():将当前时间设置为上次抓取和分析robot.txt的时间？？？？？

************************************
