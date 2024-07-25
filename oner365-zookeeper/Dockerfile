FROM jdk:1.8

VOLUME /root/oner365-zookeeper
RUN mkdir -p /root/oner365-zookeeper
WORKDIR /root/oner365-zookeeper
EXPOSE 8725

ENV TZ=Asia/Shanghai
ENV PATH $PATH

ARG JAR_FILE
ARG OUT_DIRECTORY

COPY ${OUT_DIRECTORY}/lib /root/oner365-zookeeper/lib/
COPY ${OUT_DIRECTORY}/resources /root/oner365-zookeeper/resources/
COPY ${JAR_FILE} /root/oner365-zookeeper/oner365-zookeeper-2.1.0.jar
ENTRYPOINT ["java","-jar","oner365-zookeeper-2.1.0.jar"]

# 本地执行
# mvn clean package -Dmaven.test.skip=true docker:build
# 服务器执行
# docker run -d --name oner365-zookeeper -p 8725:8725 --privileged=true --restart=always oner365-zookeeper
