FROM jdk:1.8

VOLUME /root/oner365-neo4j
RUN mkdir -p /root/oner365-neo4j
WORKDIR /root/oner365-neo4j
EXPOSE 8719

ENV TZ=Asia/Shanghai
ENV PATH $PATH

ARG JAR_FILE
ARG OUT_DIRECTORY

COPY ${OUT_DIRECTORY}/lib /root/oner365-neo4j/lib/
COPY ${OUT_DIRECTORY}/resources /root/oner365-neo4j/resources/
COPY ${JAR_FILE} /root/oner365-neo4j/oner365-neo4j-2.1.0.jar
ENTRYPOINT ["java","-jar","oner365-neo4j-2.1.0.jar"]

# 本地执行
# mvn clean package -Dmaven.test.skip=true docker:build
# 服务器执行
# docker run -d --name oner365-neo4j -p 8719:8719 --privileged=true --restart=always oner365-neo4j
