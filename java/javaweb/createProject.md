<span id="catalog"></span>

### 目录---创建web工程
- [使用空maven工程创建web工程](#使用空maven工程创建web工程)
- [maven+tomcat创建web工程--已废弃](#maven+tomcat创建web工程)

# 使用空maven工程创建web工程
[top](#catalog)
1. 创建空的 maven 工程
    - ![01](imgs/createProject/createByEmptyMaven/01.jpg)
2. 添加框架支持
    - 在 `Module` 右键选择 `Add FrameWork Support`
        - ![02_01](imgs/createProject/createByEmptyMaven/02_01.jpg)
    - 在 javaEE 下选择 `Web Application`
        - ![02_02](imgs/createProject/createByEmptyMaven/02_02.jpg)
    - 添加框架支持后，会自动添加 `web/` 目录
3. 完整的项目结构
    - ![03](imgs/createProject/createByEmptyMaven/03.jpg)
4. 添加启动配置
    - 选择 `Edit Configuration`
        - ![04_01](imgs/createProject/createByEmptyMaven/04_01.jpg)
    - 添加一个 `tomcat` 配置
        - ![04_02](imgs/createProject/createByEmptyMaven/04_02.jpg)
        - ![04_03](imgs/createProject/createByEmptyMaven/04_03.jpg)
    - 向 `tomcat` 中添加项目
        - ![04_04](imgs/createProject/createByEmptyMaven/04_04.jpg)
        - ![04_05](imgs/createProject/createByEmptyMaven/04_05.jpg)
5. 添加 `maven` 配置
    ```xml
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>4.0.0</version>
        <scope>provided</scope>
    </dependency>
    ```
6. 检查 `maven` 项目有没有被忽略。如果被忽略，会导包失败
    - 通过 `Setting` 打开
    - ![05](imgs/createProject/createByEmptyMaven/05.jpg)

# maven+tomcat创建web工程
[top](#catalog)
1. 选择maven工程 
    - ![01_choose_maven.png](imgs/createProject/maven_web/01_choose_maven.png)
2. 创建工程
    - ![02_01_newProject.png](imgs/createProject/maven_web/02_01_newProject.png)
    - ![02_02.png](imgs/createProject/maven_web/02_02.png)
    - ![02_03_name.png](imgs/createProject/maven_web/02_03_name.png)

3. 修改maven工程配置
    - `servlet`配置
        ```xml
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.0</version>
            <scope>provided</scope>
        </dependency>
        ```
    - ![03_01_maven_pom.png](imgs/createProject/maven_web/03_01_maven_pom.png)

4. 完善工程结构：
    - 添加路径：`main/java`， `main/resources`
        - ![03_02_java_structure.png](imgs/createProject/maven_web/03_02_java_structure.png)
    - 增加idea的目录设定
        - ![03_03_idea_structure_setting.png](imgs/createProject/maven_web/03_03_idea_structure_setting.png)

5. 确认：facets
    - ![04_01_facets.png](imgs/createProject/maven_web/04_01_facets.png)

6. 确认：artifacts
    - ![05_01_artifacts.png](imgs/createProject/maven_web/05_01_artifacts.png)

7. **配置tomcat**：`Run--> Edit Configurations...`
    - 添加tomcat
        - ![06_01_add_tomcat.png](imgs/createProject/maven_web/06_01_add_tomcat.png)
    - 修改servet配置：tomcat名字，url等
        - ![06_02_tomcat_server.png](imgs/createProject/maven_web/06_02_tomcat_server.png)
    - 修改deployment，添加`artifacts`
        - ![06_03_tomcat_artifacts.png](imgs/createProject/maven_web/06_03_01_tomcat_artifacts.png)
        - ![06_03_02_tomcat_artifacts.png](imgs/createProject/maven_web/06_03_02_tomcat_artifacts.png)
        - ![06_03_03_tomcat_artifacts.png](imgs/createProject/maven_web/06_03_03_tomcat_artifacts.png)
    - 保存后重新进入`Run--> Edit Configurations...`，
        - 设定**debug模式**可以**动态编译**：`On frame dactivation: Update classes and resources`
        - ![06_04_01_reload.png](imgs/createProject/maven_web/06_04_01_reload.png)
8. 启动tomcat
    - ![07_01_start_tomcat.png](imgs/createProject/maven_web/07_01_start_tomcat.png)
    - ![07_02_page_start.png](imgs/createProject/maven_web/07_02_page_start.png)

