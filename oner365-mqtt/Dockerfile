FROM jdk:1.8

VOLUME /root/oner365-mqtt
RUN mkdir -p /root/oner365-mqtt
WORKDIR /root/oner365-mqtt
EXPOSE 8723

ENV TZ=Asia/Shanghai
ENV PATH $PATH

ARG JAR_FILE
ARG OUT_DIRECTORY

COPY ${OUT_DIRECTORY}/lib /root/oner365-mqtt/lib/
COPY ${OUT_DIRECTORY}/resources /root/oner365-mqtt/resources/
COPY ${JAR_FILE} /root/oner365-mqtt/oner365-mqtt-2.1.0.jar
ENTRYPOINT ["java","-jar","oner365-mqtt-2.1.0.jar"]

# 本地执行
# mvn clean package -Dmaven.test.skip=true docker:build
# 服务器执行
# docker run -d --name oner365-mqtt -p 8723:8723 --privileged=true --restart=always oner365-mqtt
