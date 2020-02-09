内容整理自：https://www.bilibili.com/video/av21004567

<span id="catalog"></span>

### 目录
- [项目开发中存在的问题以及如何使用maven解决](#项目开发中存在的问题以及如何使用maven解决)
- [安装Maven核心程序](#安装Maven核心程序)
- [Maven简介](#Maven简介)
- [Maven命令](#Maven命令)
- [Maven的核心概念---仓库](#Maven的核心概念---仓库)
- [Maven的核心概念---目录结构](#Maven的核心概念---目录结构)
    - [为什么要遵守约定的目录结构](#为什么要遵守约定的目录结构)
    - [Maven目录结构](#Maven目录结构)
    - [目录结构示例工程---Hello](#目录结构示例工程---Hello)
- [Maven的核心概念---pom文件](#Maven的核心概念---pom文件)
- [Maven的核心概念---坐标](#Maven的核心概念---坐标)
- [Maven的核心概念---依赖](#Maven的核心概念---依赖)
    - [为什么需要依赖以及如何配置](#为什么需要依赖以及如何配置)
    - [依赖的范围](#依赖的范围)
    - [依赖示例工程---HelloFriend](#依赖示例工程---HelloFriend)
- [Maven的核心概念---生命周期](#Maven的核心概念---生命周期)
    - [Maven生命周期](#Maven生命周期)
    - [与生命周期相关的插件和目标](#与生命周期相关的插件和目标)
- [其他问题](#其他问题)
- [](#)
- [](#)
- [](#)
- [](#)
- [](#)

# 项目开发中存在的问题以及如何使用maven解决
[top](#catalog)

|问题|使用maven|
|-|-|
|一个项目就是一个工程<br>    如果一个项目过大，就不适合使用package来划分模块，最好是一个模块一个工程|使用Maven就可以将一个项目拆分成多个工程|
|项目中需要的jar包必须手动拷贝到WEB-INF/lib目录下<br>    同样的jar包重复出现在不同的工程目录中，浪费内存，让工程变的臃肿不好管理|使用Maven，将jar包保存在仓库中，有需要的工程引用这个文件接口，并不需要直接赋值jar包|
|jar包需要提前准备好<br>    不同的官网提供的jar包下载形式是不同的，有些技术的官网就是通过Maven或SVN等专门的工具来提供下载的<br>    如果以非正规的方式下载的jar包，那么其中的内容很可能也是不规范的|使用Maven可以一种规范的方式下载jar包，所以知名框架或第三方工具的jar包已经按照统一的规范存放在了Maven的中央仓库中。以规范的方式下载的jar包，内容也是可靠的|
|一个jar包依赖的其他jar包也需要手动加载到项目中|Maven会自动导入被依赖的jar包|

# 安装Maven核心程序
[top](#catalog)
1. 检查JAVA_HOME环境变量
2. 下载并解压Maven核心程序的压缩包，需要解压到一个纯英文的目录下，如：apache-maven-3.6.2-bin.tar
3. 配置Maven的环境变量：MAVEN_HOME或M2_HOME
    - export MAVEN_HOME=/Users/liujinsuo/javaJar/apache-maven-3.6.2
      export PATH=$PATH:$MAVEN_HOME/bin
4. 运行`mvn -v`查看Maven的版本

# Maven简介
[top](#catalog)
- Maven是服务于Java平台的自动化构建工具
    - 自动构建工具的发展：Make->Ant->Maven->Gradle

- 什么是构建
    - 构建的概念：以Java源文件、框架配置文件、JSP、HTML、图片等资源来生成一个可以运行的项目的过程
    - 构建的过程：编译-->部署-->搭建
        1. 编译--对于纯Java代码
            - java源文件(.java)-->编译-->Class字节码文件-->交给JVM执行
            - 一个BS项目最终运行的并不是动态Web工程本身，而是这个动态web工程**编译的结果**
                - 动态web工程-->编译、部署-->编译结果
                - 开发web工程时，需要运行时环境，其本质也是一组jar包的引用，但是并没有把jar包本身复制到工程中，所以不是目录
        2. 部署--对于Web工程
            - 什么是部署：将包含Java程序的Web工程编译的结果拿到服务器上的指定目录     
        3. 搭建--对于实际项目
            - 在实际项目中整合第三方框架，Web工程中除了Java程序和JSP页面、图片等静态资源之外，还包括第三方框架的jar包以及各种各样的配置文件。**所有这些资源必须按照正确的目录结果部署到服务器上，项目才可以运行**

- Maven构建过程中的各个环节
    1. 清理：将以前编译得到的旧的class字节码文件删除，为下一次编译做准备
    2. 编译：将java源程序编译成class字节码文件
    3. 测试：自动测试，自动调用junit程序
    4. 报告：测试程序执行的结果
    5. 打包：动态web工程打war包，Java工程打jar包
    6. 安装：Maven的特定概念--将打包得到的文件复制到仓库中的指定位置
    7. 部署：将动态Web工程生成的war包复制到Servlet容器的指定目录下，使其可以运行

- Maven的核心概念
    1. 目录结构
    2. pom文件
    3. 坐标
    4. 依赖
    5. 仓库
    6. 生命周期/插件/目标
    7. 继承
    8. 聚合
    

# Maven命令
[top](#catalog)
- <lable style="color:red">执行与构建过程相关的Maven命令，必须进入pom.xml所在的目录</label>
    - 与构建构成相关的Maven命令：编译、测试、打包等等
- `mvn clean`， 清理 （删除编译结果目录）
- `mvn compile`， 编译
- `mvn test-compile`， 编译测试程序
- `mvn test`， 执行测试
- `mvn package`， 打包
- `mvn install`， 将工程打包并放入仓库
- `mvn site`，生成项目报告、站点、发布站点

# Maven的核心概念---仓库
[top](#catalog)
- 仓库的分类
    - 本地仓库：当前电脑上部署的仓库目录，为当前电脑上所有的Maven工程服务
    - 远程仓库：
        - 私服：架设在当前局域网环境下，为当局域网内的所有Maven工程服务
            - 依赖获取的步骤
                1. 需要依赖X时，直接访问私服
                2. 如果私服上不存在依赖X，则私服会自动的到外网下载并保存在私服上
                3. 当其他用户需要依赖X时，直接从私服上获取
            - 使用场景：大型开发时，只允许部分机器连接外网，其他机器全部使用私服
            - ![repository_nexus.png](./imgs/base/repository_nexus.png)
        - 中央仓库：架设在Internet上，为世界上所有Maven工程服务
        - 中央仓库的镜像：为了分担中央仓库的流浪，提升用户访问速度
        
- 仓库中保存的内容:本质都是Maven工程
    1. Maven自身所需要的插件 
    2. 第三方框架或工具的jar包
    3. 自己开发的Maven工程

- 修改Maven仓库位置--解决联网问题
    - Maven的**核心程序中仅仅定义了抽象的生命周期**，但是具体的工作必须有特定的插件来完成，而插件本身并不包含在Maven的核心程序中
    - 当Maven命令需要用到某些插件时，Maven核心程序会首先到本地仓库中查找，如果本地找不到需要的插件，那么它会自动链接外网到中央仓库下载，如果无法链接外网，则构建失败
    - 本地仓库的默认位置：`~/.m2/repository`
    - 修改默认本地仓库的位置可以让Maven核心程序到自定义的目录下查找插件
        - 两种级别修改
            - 修改全局配置文件：`maven解压目录/conf/settings.xml`
            - 修改自定义配置文件：`~/.m2/settings.xml`，或`~/.m2/自定义配置文件名.xml`
        - 修改方式
            - 在配置文件中查找标签：`localRepository`
            - 修改`<localRepository>/path/to/local/repo</localRepository>`
            - 将标签体内容修改为自定义Maven仓库目录



# Maven的核心概念---目录结构
## 为什么要遵守约定的目录结构
[top](#catalog)
- maven 要负责项目的自动化构建，以编译为例，Maven想要自动进行编译，那么它必须知道java源文件，保存在哪里，构建的结果放在哪里

- 自定义的内如果想让框架或工具知道，有两种办法
    - 以配置的方式明确告诉框架
    - 遵守框架内部的约定

- 约定 > 配置 > 编码

## Maven目录结构
[top](#catalog)
- maven的目录结构分为3类
    - 配置文件：pom.xml
    - 源码目录：src
    - 构建结构目录：target
    
- maven的目录结构说明

    ```
    根目录：工程名
    ├── pom.xml                     ：Maven工程的核心配置文件
    ├── src                         ：源码
    │   ├── main                    ：主程序
    │   │   ├── java                ：java源程序
    │   │   └── resources           ：框架或其他工具的配置文件
    │   └── test                    ：测试程序
    │       ├── java                ：测试源程序
    │       └── resources           ：框架或其他工具的配置文件
    └── target                      ：执行 mvn指令 后生成的工程构建结果
      ├── 工程名-版本号.jar           ：执行 mvn package 之后生成的jar
      ├── classes                   ：执行 mvn compile/test/test-compile/package 指令之后，生成的主程序编译结果
      ├── generated-sources         ：?????
      ├── generated-test-sources    ：?????
      ├── maven-archiver            ：?????
      ├── maven-status              ：?????
      ├── surefire-reports          ：执行`mvn test/package`指令之后，生成的测试结果报告
      └── test-classes              ：执行 mvn test/test-compile/package 指令之后，生成的测试程序编译结果
    ```
            
            
## 目录结构示例工程---Hello
[top](#catalog)
- 示例工程地址：Hello，[java/maven/sample/Hello](java/maven/sample/Hello)
- 示例的目录结构

    ```
    Hello
    ├── pom.xml
    ├── src
    │   ├── main
    │   │   ├── java
    │   │   │   └── com
    │   │   │       └── ljs
    │   │   │           └── mavenlearn
    │   │   │               └── Hello.java
    │   │   └── resources
    │   └── test
    │       ├── java
    │       │   └── com
    │       │       └── ljs
    │       │           └── mavenlearn
    │       │               └── HelloTest.java
    │       └── resources
    ```
  
- pom.xml配置：[/java/maven/sample/Hello/pom.xml](/java/maven/sample/Hello/pom.xml)
    ```xml
    <?xml version="1.0" ?>
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        <modelVersion>4.0.0</modelVersion>
    
        <groupId>com.ljs.mavenlearn</groupId>
        <artifactId>Hello</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    
        <properties>
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
            <maven.compiler.source>1.8</maven.compiler.source>
            <maven.compiler.target>1.8</maven.compiler.target>
        </properties>
    
    
        <name>Hello</name>
          
        <dependencies>
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>4.12</version>
                <scope>test</scope>
            </dependency>
        </dependencies>
    </project>
    ```
  
- 代码
    - 主程序源代码，Hello.java：[/java/maven/sample/Hello/src/main/java/com/ljs/mavenlearn/Hello.java](/java/maven/sample/Hello/src/main/java/com/ljs/mavenlearn/Hello.java)
        ```java
        public class Hello {
        	public String sayHello(String name){
        		return "Hello "+name+"!";
        	}
        }
        ```
    
    - 测试源代码，HelloTest.java：[/java/maven/sample/Hello/src/test/java/com/ljs/mavenlearn/HelloTest.java](/java/maven/sample/Hello/src/test/java/com/ljs/mavenlearn/HelloTest.java)
        ```java
        import org.junit.Test;
        import static org.junit.Assert.assertEquals;
        public class HelloTest {
            @Test
            public void testHello(){
                Hello hello = new Hello();
                String results = hello.sayHello("testUser");
                assertEquals("Hello testUser!",results);	
            }
        }
        ```

- 执行maven命令之后，生成的target目录
    - mvn clean 清理
    - mvn compile 编译
        ```
        Hello
        └── target
            ├── classes
            │   └── com
            │       └── ljs
            │           └── mavenlearn
            │               └── Hello.class
            ├── generated-sources
            │   └── annotations
            └── maven-status
                └── maven-compiler-plugin
                    └── compile
                        └── default-compile
                            ├── createdFiles.lst
                            └── inputFiles.lst
        ```
    - mvn test-compile 编译测试程序
        ```
        Hello
        └── target
          ├── classes
          │   └── com
          │       └── ljs
          │           └── mavenlearn
          │               └── Hello.class
          ├── generated-sources
          │   └── annotations
          ├── generated-test-sources
          │   └── test-annotations
          ├── maven-status
          │   └── maven-compiler-plugin
          │       ├── compile
          │       │   └── default-compile
          │       │       ├── createdFiles.lst
          │       │       └── inputFiles.lst
          │       └── testCompile
          │           └── default-testCompile
          │               ├── createdFiles.lst
          │               └── inputFiles.lst
          └── test-classes
              └── com
                  └── ljs
                      └── mavenlearn
                          └── HelloTest.class
        ```
    - mvn test 执行测试
    - mvn package 打包
        ```
        Hello
        └── target
          ├── Hello-0.0.1-SNAPSHOT.jar
          ├── classes
          │   └── com
          │       └── ljs
          │           └── mavenlearn
          │               └── Hello.class
          ├── generated-sources
          │   └── annotations
          ├── generated-test-sources
          │   └── test-annotations
          ├── maven-archiver
          │   └── pom.properties
          ├── maven-status
          │   └── maven-compiler-plugin
          │       ├── compile
          │       │   └── default-compile
          │       │       ├── createdFiles.lst
          │       │       └── inputFiles.lst
          │       └── testCompile
          │           └── default-testCompile
          │               ├── createdFiles.lst
          │               └── inputFiles.lst
          ├── surefire-reports
          │   ├── TEST-com.ljs.mavenlearn.HelloTest.xml
          │   └── com.ljs.mavenlearn.HelloTest.txt
          └── test-classes
              └── com
                  └── ljs
                      └── mavenlearn
                          └── HelloTest.class
        ```

            
# Maven的核心概念---pom文件
[top](#catalog)
- 含义：Project Object Model项目对象模型
- pom.xml是Maven工程的核心配置文件，与构建过程相关的一切设置都在这个文件中配置 （相当于web.xml对于web工程）
- 配置内容




# Maven的核心概念---坐标
[top](#catalog)
- Maven中使用三个向量在仓库中唯一定位一个Maven工程
    |向量|含义|配置内容|
    |-|-|-|
    |groupid|公司或组织域名倒序 + 项目名|`<groupId>com.ljs.learn</groupId>`|
    |artifactid|模块名|`<artifactId>mylearn</artifactId>`|
    |version|版本|`<version>1.0-SNAPSHOT</version>`|

- `SNAPSHOT`和`RELEASE`版本
    - `SNAPSHOT`表示迭代开发中的版本
    - `RELEASE`表示一个稳定的版本
    
- Meven坐标与仓库中路径的对应关系
    - `spring-core`的坐标配置
        ```xml
        <groupId>org.springframework</groupId>
        <artifactId>spring-core</artifactId>
        <version>5.2.0.RELEASE</version>
        ```
    - `spring-core`在仓库中的路径
        - `仓库根目录/org/springframework/spring-core/5.2.0.RELEASE/spring-core-5.2.0.RELEASE.jar`
    - 对应关系：`仓库根目录/groupId/artifactId/version/artifactId-version.jar`
        - groupId中的所有`.`改成`/`

# Maven的核心概念---依赖
## 为什么需要依赖以及如何配置
[top](#catalog)
- 当 A.jar 包用到了 B.jar 包中的某些类时，A 就对 B 产生了依赖，所以需要使用某种方式来描述依赖
- 配置方法
    ```xml
    <dependency>
        <groupId>com.ljs.mavenlearn</groupId>
        <artifactId>Hello</artifactId>
        <version>0.0.1-SNAPSHOT</version>   
        <scope>compile</scope>                <!--依赖的范围-->
    </dependency>
    ```
- Maven解析依赖信息时，会到本地仓库中查找被依赖的jar包
- 对于自定义的Maven工程，如果想再被依赖时被Meven找到，需要使用安装命令：`mvn install`，将工程打包并放入仓库

## 依赖的范围
[top](#catalog)
- 依赖范围：`<dependency><scope>`
    - 6种依赖范围
        |名称|范围|
        |-|-|
        |compile|**默认scope**，主程序和测试范围|
        |test|测试范围|
        |provided|编译时，不会将这个依赖相关的包引入|
        |runtime|编译不需要依赖项，但是用于运行时|
        |system|和 provided 类似，这里只需要指定jar 的路径，编译时会引用这个 jar 包信息。|
        |import|编译时，依赖其他项目的 <dependencyManagement>|

- 常用的依赖范围有三种：compile、test、provided
    - compile和test
        - 依赖范围对main主程序和测试程序test的可见性
            - ![dependency_scope.png](./imgs/base/dependency_scope.png)
        
        - compile
            |有效范围|是否有效|
            |-|-|
            |对主程序是否有效|有效|
            |对测试程序是否有效|有效|
            |是否参与打包|参与|
    
        - test
            |有效范围|是否有效|
            |-|-|
            |对主程序是否有效|无效|
            |对测试程序是否有效|有效|
            |是否参与打包|不参与|
    
    - provided
        - 有些依赖只是在开发阶段和测试阶段使用，在运行是服务器会提供对应的实现，所以可以使用provided。如果使用compile，可能会造成依赖冲突。如servlet-api
            - ![provided_scope.png](./imgs/base/provided_scope.png)  
        - 有效范围
        
            |有效范围|是否有效|
            |-|-|
            |对主程序是否有效|有效|
            |对测试程序是否有效|有效|
            |是否参与打包|不参与|
            |是否参与部署|不参与|


## 依赖示例工程---HelloFriend
[top](#catalog)
- 示例工程地址：HelloFriend，[/java/maven/sample/HelloFriend](/java/maven/sample/HelloFriend)
- **在主程序中需要使用：[目录结构示例工程---Hello](#目录结构示例工程---Hello) 中的类，所以需要添加依赖**
- 目录结构

    ```
    HelloFriend
    ├── pom.xml
    └── src
        ├── main
        │   ├── java
        │   │   └── com
        │   │       └── ljs
        │   │           └── mavenlearn
        │   │               └── HelloFriend.java
        │   └── resources
        └── test
            ├── java
            │   └── com
            │       └── ljs
            │           └── mavenlearn
            │               └── HelloFriendTest.java
            └── resources
    ```

- pom.xml：[/java/maven/sample/HelloFriend/pom.xml](/java/maven/sample/HelloFriend/pom.xml)
    
    ```xml
    <?xml version="1.0" ?>
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        <modelVersion>4.0.0</modelVersion>
    
        <groupId>com.ljs.mavenlearn</groupId>
        <artifactId>HelloFriend</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    
        <properties>
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
            <maven.compiler.source>1.8</maven.compiler.source>
            <maven.compiler.target>1.8</maven.compiler.target>
        </properties>
    
        <name>HelloFriend</name>
          
        <dependencies>
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>4.12</version>
                <scope>test</scope>
            </dependency>
    
            <!-- 添加自定义maven工程的依赖 -->
            <dependency>
                <groupId>com.ljs.mavenlearn</groupId>
                <artifactId>Hello</artifactId>
                <version>0.0.1-SNAPSHOT</version>
                <scope>compile</scope>
            </dependency>
        </dependencies>
    </project>
    ```

- 代码
    - 主程序代码，HelloFriend.java : [/java/maven/sample/HelloFriend/src/main/java/com/ljs/mavenlearn/HelloFriend.java](/java/maven/sample/HelloFriend/src/main/java/com/ljs/mavenlearn/HelloFriend.java)
        ```java
        package com.ljs.mavenlearn;        
        import com.ljs.mavenlearn.Hello;
      
        // 测试依赖范围，但是并没有使用
        // 由于test范围的依赖对于main程序不可见，所以无法编译
        // import org.junit.Test;
        
        public class HelloFriend {
            public String sayHelloToFriend(String name){
                Hello hello = new Hello(); // 调用其他Maven工程中的类com.ljs.mavenlearn.Hello
                String str = hello.sayHello(name)+" I am "+this.getMyName();
                System.out.println(str);
                return str;
            }
            public String getMyName(){
                return "John";
            }
        }
        ```
    - 测试程序代码，HelloFriendTest.java : [/java/maven/sample/HelloFriend/src/test/java/com/ljs/mavenlearn/HelloFriendTest.java](/java/maven/sample/HelloFriend/src/test/java/com/ljs/mavenlearn/HelloFriendTest.java)
        ```java
        package com.ljs.mavenlearn;
        
        import org.junit.Test;
        import static org.junit.Assert.assertEquals;
      
        // 测试依赖范围，但是并没有使用
        import com.ljs.mavenlearn.Hello;
        
        public class HelloFriendTest {
            @Test
            public void testHelloFriend(){
                HelloFriend helloFriend = new HelloFriend();
                String results = helloFriend.sayHelloToFriend("Tom");
                assertEquals("Hello Tom! I am John",results);	
            }
        }
        ```
- 编译过程
    1. 在`HelloFriend`的根目录下，执行`mvn compile`，没有在仓库中找到`com.ljs.mavenlearn/Hello`，所以产生了编译异常
        - ![HelloFriend_mvn_cmd_01.png](./imgs/base/HelloFriend_mvn_cmd_01.png)
    2. 在`Hello`的根目录下，执行`mvn install`
    3. 在`HelloFriend`的根目录下，重新执行`mvn compile`，编译成功
    
- 依赖范围测试
    1. junit的测试是test范围，无法在main程序中使用
        - 在HelloFriend.java中添加引用：`import org.junit.Test;，执行编译时发生异常，执行compile阶段时，找不到jar包
            - ![HelloFriend_mvn_cmd_02.png](./imgs/base/HelloFriend_mvn_cmd_02.png)

# Maven的核心概念---生命周期
## Maven生命周期
[top](#catalog)
- Maven的核心程序中定义了抽象的生命周期，生命周期中各个阶段的具体任务是由插件来完成的
- Maven核心程序执行生命周期各阶段的规则：**无论现在要执行生命周期的哪一个阶段，都是从这个生命周期最初的位置开始执行**
- Maven有三套相对独立的生命周期，分别是：
    1. `Clean Lifecycle` 在进行真正的构建之前进行一些清理工作
    2. `Default Lifecycle`构建的核心部分，编译、测试、打包、安装、部署
    3. `Site Lifecycle`生成项目报告、站点、发布站点

- `Clean Lifecycle`
    - 生命周期
        1. pre-cleap，执行一些需要在clean之前完成的工作
        2. clean，移除所有上一次构建生成的文件
        3. post-clean，执行一些需要在clean之后立刻完成的工作

- `Site Lifecycle`
    - 生命周期
        1. pre-site 执行一些需要在生成站点文档之前完成的工作
        2. site 生成项目的站点文档
        3. post-site 执行一些需要在生成站点文档之后完成的工作，并且为部署做准备
        4. site-deploy 将生成的站点文档部署到特定的服务器上

    - 经常用到的是`site`阶段和`site-deploy`阶段，用以生成和发布 Maven 站点，耗费的时间比较长
   
- `Default Lifecycle`
    - 23个生命阶段
        
        ||阶段|含义|描述|
        |-|-|-|-|
        |1|validate|校验|校验项目是否正确并且所有必要的信息可以完成项目的构建过程|
        |2|initialize|初始化|初始化构建状态，比如设置属性值|
        |3|generate-sources|生成源代码|生成包含在编译阶段中的任何源代码|
        |4|process-sources|处理源代码|处理源代码，比如说，过滤任意值|
        |5|generate-resources|生成资源文件|生成将会包含在项目包中的资源文件|
        |6|<label style="color:red">process-resources</label>|处理资源文件|复制和处理资源到目标目录，为打包阶段最好准备|
        |7|<label style="color:red">compile</label>|编译|编译项目的源代码|
        |8|process-classes|处理类文件|处理编译生成的文件，比如说对Java class文件做字节码改善优化|
        |9|generate-test-sources|生成测试源代码|生成包含在编译阶段中的任何测试源代码|
        |10|process-test-sources|处理测试源代码|处理测试源代码，比如说，过滤任意值|
        |11|generate-test-resources|生成测试资源文件|为测试创建资源文件|
        |12|<label style="color:red">process-test-resources</label>|处理测试资源文件|复制和处理测试资源到目标目录|
        |13|<label style="color:red">test-compile</label>|编译测试源码|编译测试源代码到测试目标目录|
        |14|process-test-classes|处理测试类文件|处理测试源码编译生成的文件|
        |15|<label style="color:red">test</label>|测试|使用合适的单元测试框架运行测试（Juint是其中之一）|
        |16|prepare-package|准备打包|在实际打包之前，执行任何的必要的操作为打包做准备|
        |17|<label style="color:red">package</label>|打包|将编译后的代码打包成可分发格式的文件，比如JAR、WAR或者EAR文件|
        |18|pre-integration-test|集成测试前|在执行集成测试前进行必要的动作。比如说，搭建需要的环境|
        |19|integration-test|集成测试|处理和部署项目到可以运行集成测试环境中|
        |20|post-integration-test|集成测试后|在执行集成测试完成后进行必要的动作。比如说，清理集成测试环境|
        |21|verify|验证|运行任意的检查来验证项目包有效且达到质量标准|
        |22|<label style="color:red">install</label>|安装|安装项目包到本地仓库，这样项目包可以用作其他本地项目的依赖|
        |23|<label style="color:red">deploy</label>|部署|将最终的项目包复制到远程仓库中与其他开发者和项目共享|
    

## 与生命周期相关的插件和目标
[top](#catalog)
- Maven 的核心仅仅定义了抽象的生命周期，具体的任务都是交由插件完成的
- 每个插件都能实现多个功能，每个功能就是一个插件目标
- Maven 的生命周期与插件的某个目标(功能)是相对应的
- ?????

# 其他问题
[top](#catalog)
- 解决自动创建的maven工程提供的JDK版本过低的问题
    1. 进行全局配置
        - 在`maven解压目录/conf/settings.xml`中的`<profiles>`下添加配置
        - <label style="color: red">在idea中无效</label>
        ```xml
        <profile>
            <id>jdk-1.8</id>
            <activation>
                <activeByDefault>true</activeByDefault>
                <jdk>1.8</jdk>
            </activation>
            <properties>
                <maven.compiler.source>1.8</maven.compiler.source>
                <maven.compiler.target>1.8</maven.compiler.target>
                <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
            </properties>
        </profile>
        ```
    2. 配置某个工程的pom.xml
        ```xml
        <properties>
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
            <maven.compiler.source>1.8</maven.compiler.source>
            <maven.compiler.target>1.8</maven.compiler.target>
        </properties>
        ```
    
    7. 继承
    8. 聚合