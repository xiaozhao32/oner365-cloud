FROM jdk:1.8

VOLUME /root/oner365-cassandra
RUN mkdir -p /root/oner365-cassandra
WORKDIR /root/oner365-cassandra
EXPOSE 8720

ENV TZ=Asia/Shanghai
ENV PATH $PATH

ARG JAR_FILE
ARG OUT_DIRECTORY

COPY ${OUT_DIRECTORY}/lib /root/oner365-cassandra/lib/
COPY ${OUT_DIRECTORY}/resources /root/oner365-cassandra/resources/
COPY ${JAR_FILE} /root/oner365-cassandra/oner365-cassandra-2.1.0.jar
ENTRYPOINT ["java","-jar","oner365-cassandra-2.1.0.jar"]

# 本地执行
# mvn clean package -Dmaven.test.skip=true docker:build
# 服务器执行
# docker run -d --name oner365-cassandra -p 8720:8720 --privileged=true --restart=always oner365-cassandra
