## 使用idea+maven+tomcat创建web工程
- 选择maven工程 
    - ![01_choose_maven.png](./imgs/createProject/01_choose_maven.png)
- 创建工程
    - ![02_01_newProject.png](./imgs/createProject/02_01_newProject.png)
    - ![02_02.png](./imgs/createProject/02_02.png)
    - ![02_03_name.png](./imgs/createProject/02_03_name.png)

- 修改maven工程配置
    - `servlet`配置
        ```xml
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.0</version>
            <scope>provided</scope>
        </dependency>
        ```
    - ![03_01_maven_pom.png](./imgs/createProject/03_01_maven_pom.png)

- 完善工程结构：
    - 添加路径：`main/java`， `main/resources`
        - ![03_02_java_structure.png](./imgs/createProject/03_02_java_structure.png)
    - 增加idea的目录设定
        - ![03_03_idea_structure_setting.png](./imgs/createProject/03_03_idea_structure_setting.png)

- 确认：facets
    - ![04_01_facets.png](./imgs/createProject/04_01_facets.png)

- 确认：artifacts
    - ![05_01_artifacts.png](./imgs/createProject/05_01_artifacts.png)

- **配置tomcat**：`Run--> Edit Configurations...`
    - 添加tomcat
        - ![06_01_add_tomcat.png](./imgs/createProject/06_01_add_tomcat.png)
    - 修改servet配置：tomcat名字，url等
        - ![06_02_tomcat_server.png](./imgs/createProject/06_02_tomcat_server.png)
    - 修改deployment，添加`artifacts`
        - ![06_03_tomcat_artifacts.png](./imgs/createProject/06_03_01_tomcat_artifacts.png)
        - ![06_03_02_tomcat_artifacts.png](./imgs/createProject/06_03_02_tomcat_artifacts.png)
        - ![06_03_03_tomcat_artifacts.png](./imgs/createProject/06_03_03_tomcat_artifacts.png)
    - 保存后重新进入`Run--> Edit Configurations...`，
        - 设定**debug模式**可以**动态编译**：`On frame dactivation: Update classes and resources`
        - ![06_04_01_reload.png](./imgs/createProject/06_04_01_reload.png)
- 启动tomcat
    - ![07_01_start_tomcat.png](./imgs/createProject/07_01_start_tomcat.png)
    - ![07_02_page_start.png](./imgs/createProject/07_02_page_start.png)

