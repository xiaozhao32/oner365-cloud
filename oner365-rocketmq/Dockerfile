FROM jdk:1.8

VOLUME /root/oner365-rocketmq
RUN mkdir -p /root/oner365-rocketmq
WORKDIR /root/oner365-rocketmq
EXPOSE 8713

ENV TZ=Asia/Shanghai
ENV PATH $PATH

ARG JAR_FILE
ARG OUT_DIRECTORY

COPY ${OUT_DIRECTORY}/lib /root/oner365-rocketmq/lib/
COPY ${OUT_DIRECTORY}/resources /root/oner365-rocketmq/resources/
COPY ${JAR_FILE} /root/oner365-rocketmq/oner365-rocketmq-2.1.0.jar
ENTRYPOINT ["java","-jar","oner365-rocketmq-2.1.0.jar"]

# 本地执行
# mvn clean package -Dmaven.test.skip=true docker:build
# 服务器执行
# docker run -d --name oner365-rocketmq -p 8713:8713 --privileged=true --restart=always oner365-rocketmq
