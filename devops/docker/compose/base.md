<span id="catalog"></span>

### 目录
- [为什么需要Docker_Compose](#为什么需要Docker_Compose)
- [安装Compose](#安装Compose)
- [Compose的配置文件](#Compose的配置文件)
- [容器操作](#容器操作)
- [](#)
- [](#)
- [](#)


# 为什么需要Docker_Compose
[top](#catalog)
- 多容器应用的问题
    - 要通过 Dockerfile 构建 image 或者从 Dockerhub 拉取 image
    - 要手动管理容器的创建、停止与删除
    - 没有一个批处理/批管理的方式

- Docker Compose 是什么
    - Docker Compose 相当于docker的一个批处理工具
    - 可以用过yml文件定义多容器的docker应用
    - 创建好yml文件之后，就可以通过一条命令来创建或管理所需的容器

# 安装Compose
[top](#catalog)
- mac 和 window 系统在安装docker时，会自动安装 docker Compose，Linux系统需要手动安装 docker Compose
- ?????

# Compose的配置文件
[top](#catalog)
- 默认的配置文件：`docker-compose.yml`
- 配置文件中的三大概念
    1. Services
    2. Networks
    3. Volumes

- Services
    - 什么是Service
        - 一个service代表一个container
        - 这个container的来源
            - 由 dockerhub 的image创建的
            - 由本地的 dockerfile 制作出来的image创建的
    - Service的启动
        - 启动方式类似 `docker run`
        - 启动时，可以指定 network、volume

# 容器操作
[top](#catalog)
- 启动
    - docker-compose -f [*.yml] up
    - docker-compose  up 
        - 默认使用当前路径下的：docker-compose.xml
    - docker-compose  up -d 守护进程运行
- docker-compose images
- docker-compose ps
- docker-compose stop
- docker-compose start
- docker-compose down 停止+删除
- docker-compose exec serviceID
