* 初次启动后会在用户家目录下创建一个IntelliJIdea的文件夹，里面自动生成默认的配置文件。
    * 如果将配置文件改坏了，直接删除IntelliJIdea这个文件夹，然后重启工具，会重新生成该文件夹
* project和module
    * 每一窗口只能显示一个project
    * project下以module来区分
    * module的删除方法
        1. project右键-->Open Module Settings-->选择Module-->Delete
        2. 操作1并没有真正从磁盘中删除，若需要从磁盘中删除，需要在project中选择module右键删除
* 常用设置
    * 启动设置：File-->Settings
    * 设置工具的主题
        * Appearance&Behavior-->Appearance-->theme
    * 修改当前主题的字体、字体大小、字体行间距
        * Editor-->Color Scheme-->Color Scheme Font
    * 使用Ctrl+鼠标滚轮来调整文字大小
        * Editor-->General-->Mouse-->**Change font size(Zoom) with Ctrl+Mouse Wheel**
    * 鼠标放在方法/类上显示Doc信息
        * Editor-->General-->Other-->**Show quick documentation on mouse move**
        * Delay参数设定显示的延迟时间
    * 自动导包
        * 手动导包快捷键：alt+enter
        * Editor-->General-->Auto Import-->Java-->
            * Insert inports on paste: All
            * 选中：
                * Add unambiguous inports on the fly
                * Optimize imports on the fly
    * 设置显示行号
        * Editor-->General-->Appearance-->Show line numbers
    * 方法间的分隔符
        * Editor-->General-->Appearance-->Show method separators
        * 设置后每个方法之间使用虚线进行分割
    * 忽略大小写的情况下进行提示
        * Editor-->General-->Code Completion-->
            * case sensitive completion: None
            * 或者取消：match case
    * 取消单行显示tags
        * Editor-->General-->Editor Tabs-->
            * 取消：Show tabs in one row
        * 取消后，当打开的文件特别多时，顶部显示tags的部分会自动换行，进行多行显示
    * 手动执行文字大小，样式，文字间隔的设置
        * Editor-->Font
    * 修改注释颜色
        * Editor-->Color Scheme-->Language Defaults
            * 选择Commnets，可以分别对单行、多行、说明注释进行调整
    * 设置超过指定的inport个数时，自动将import改为 import xxx.* （不常用）
        * Editor-->Code Style-->Java-->
            * 选择import
            * 单个包导入时的设定：Class count to use import with '*'，默认是5
            * 静态导入时(方法/类/变量等)的设定：Names count to use static import with '*'，默认是3
    * 修改类头的文档注释信息
        * Editor-->File and Code Templates-->
            * 选择Includes-->File Header，进行手动添加
    * 设置项目文件的编码
        * 文件的编码最好都设定成**utf-8**
        * 也可用通过工具右下角的选项进行当前文件的编码变换
        * Editor-->File Encodings-->
            * Global Encoding
            * Project Encoding 
            * Default encoding for properties files
            * transparent native-to-assii conversion，涉及到ascii码时也进行自动转换
    * 设置自动编译 
        * Intellij IDEA默认状态为不自动编译
        * Build,Execution,Deployment-->Compiler-->
            * Build project automatically 是否进行自动编译
            * Compile independent modules in parallel 是否并行的对多个模块编译
    * 省电模式
        * File-->Power Save Mode
        * 在省电模式下，将自动关闭代码检查和代码提示等功能。可以理解为这是一种阅读模式

* 快捷键keymap
    * 光标向下移动一行 = shift + enter
    * 万能解错/生成返回值变量=alt + enter
    * 在工作窗口进行移动alt + 左/右
    * 查看类继承关系=ctrl + h
    * 代码格式化=ctrl + alt + L
    * 代码折叠 = ctrl +/-
    * 增加对一段代码的某种处理= ctrl+alt+T
    * 查看类的树形结构= ctrl + alt + U
    * 查看方法的多层重写结构= ctrl + alt + h
    * 添加收藏=alt+shift+f
    * 切换代码文字的大小写=ctrl + shift + U
    * 创建get/set，构造器 = alt+ insert
    * 复制一行代码 = ctrl + D
    * 查看接口的实现类= ctrl + alt + B
    * 查找某个类：ctrl+alt+shift+n
* 模版
    * 配置一些常用代码字母缩写，输入简写时可以使用
    * 不可修改预定以设置：settings-->Editor-->General-->Postfix Completion
    * 可修改预定义设置：settings-->Editor-->Live Templates
        * 同时可在该处添加自定义设置
    * 常用模版
        * main方法=psvm
        * 控制台输出
            * sout
            * soutp 输出当前方法的形参 
            * soutm 输出当前的方法名
            * soutv 输出参数的值
            * xxx.sout xxx表示某个参数，加.sout后等同于直接输出该参数
        * for循环
            * fori 普通循环
            * iter 增强for循环 
            * itar 对某个参数进行计数
        * list for 集合遍历
            * xxx.for 增强for循环
            * xxx.fori 普通for循环
            * xxx.forr 倒叙for循环
        * 条件判断
            * ifn/xxx.null = if (xx==null)
            * inn/xxx.nn = if (xx!=null)
        * 变量类型
            * 私有静态=prsf
            * 公有静态=psf
            * 公有静态int型=psfi
            * 公有静态string型=psfs
    * 自定义模版
        * 

* java Mave 配置文件：`settings.xml`
    * 配置阿里镜像
        * 参考：`https://www.cnblogs.com/draculaqk/p/7613807.html`
        * 创建配置文件的路径`cd ~; cd .m2` 
        * 配置内容
            ```xml
            <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                                    https://maven.apache.org/xsd/settings-1.0.0.xsd">
            
                <mirrors>
                    <mirror> 
                        <id>alimaven</id> 
                        <name>aliyun maven</name> 
                        <url>http://maven.aliyun.com/nexus/content/groups/public/</url> 
                        <mirrorOf>central</mirrorOf>         
                    </mirror> 
                </mirrors>
            </settings>
            ```
* 从git拉取项目
    * File-->New-->Project from version control--->git
* 添加SDKS
    * File-->Project Structure
* 使从git上克隆的java项目可用
    * File-->Project Structure
        * Projcet：添加out目录，SDK
        * Modules：设置src路径
* 排除某个目录，不进行index
    * 右键 --> Mark Directory as --> Excluded
* 调整java的内存使用
    * idea.vmoptions