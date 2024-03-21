FROM jdk:1.8

VOLUME /root/oner365-kafka
RUN mkdir -p /root/oner365-kafka
WORKDIR /root/oner365-kafka
EXPOSE 8710

ENV TZ=Asia/Shanghai
ENV PATH $PATH

ARG JAR_FILE
ARG OUT_DIRECTORY

COPY ${OUT_DIRECTORY}/lib /root/oner365-kafka/lib/
COPY ${OUT_DIRECTORY}/resources /root/oner365-kafka/resources/
COPY ${JAR_FILE} /root/oner365-kafka/oner365-kafka-2.1.0.jar
ENTRYPOINT ["java","-jar","oner365-kafka-2.1.0.jar"]

# 本地执行
# mvn clean package -Dmaven.test.skip=true docker:build
# 服务器执行
# docker run -d --name oner365-kafka -p 8710:8710 --privileged=true --restart=always oner365-kafka
