- 构建集群
    ```
    docker network create --driver bridge --subnet 172.21.0.0/16 zkbr

    docker run -it --name=zk01 -p=2181:2181 -v=/Users/liujinsuo/bigdata/00_data-docker/zookeeper/local/zoo.cfg:/zoo.cfg  -h=zk01 --network=zkbr --ip=172.21.100.101 --add-host=zk02:172.21.100.102 --add-host=zk03:172.21.100.103 zklocal

    docker run -it --name=zk02 -v=/Users/liujinsuo/bigdata/00_data-docker/zookeeper/local/zoo.cfg:/zoo.cfg  -h=zk02 --network=zkbr --ip=172.21.100.102 --add-host=zk01:172.21.100.101 --add-host=zk03:172.21.100.103 zklocal

    docker run -it --name=zk03 -v=/Users/liujinsuo/bigdata/00_data-docker/zookeeper/local/zoo.cfg:/zoo.cfg  -h=zk03 --network=zkbr --ip=172.21.100.103 --add-host=zk01:172.21.100.101 --add-host=zk02:172.21.100.102 zklocal 

    echo 1 > /zkdata/myid
    echo 2 > /zkdata/myid
    echo 3 > /zkdata/myid
    ```
