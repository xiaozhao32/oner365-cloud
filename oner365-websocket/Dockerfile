FROM jdk:1.8

VOLUME /root/oner365-websocket
RUN mkdir -p /root/oner365-websocket
WORKDIR /root/oner365-websocket
EXPOSE 8703

ENV TZ=Asia/Shanghai
ENV PATH $PATH

ARG JAR_FILE
ARG OUT_DIRECTORY

COPY ${OUT_DIRECTORY}/lib /root/oner365-websocket/lib/
COPY ${OUT_DIRECTORY}/resources /root/oner365-websocket/resources/
COPY ${JAR_FILE} /root/oner365-websocket/oner365-websocket-2.1.0.jar
ENTRYPOINT ["java","-jar","oner365-websocket-2.1.0.jar"]

# 本地执行
# mvn clean package -Dmaven.test.skip=true docker:build
# 服务器执行
# docker run -d --name oner365-websocket -p 8703:8703 --privileged=true --restart=always oner365-websocket
