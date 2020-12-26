
if [ -z $1 ];then
  echo "\033[31mnot java class \033[0m"
  exit 1
fi

cd `dirname $0`/..
pwd
mvn clean assembly:assembly -Dmaven.test.skip=true
#mvn assembly:assembly
docker cp target/hbase-weibo-1.0-SNAPSHOT-jar-with-dependencies.jar nn01:/
docker exec nn01 /bin/bash -c "java -cp /hbase-weibo-1.0-SNAPSHOT-jar-with-dependencies.jar $1"

