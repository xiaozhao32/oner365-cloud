FROM jdk:1.8

VOLUME /root/oner365-system
RUN mkdir -p /root/oner365-system
WORKDIR /root/oner365-system
EXPOSE 8707

ENV TZ=Asia/Shanghai
ENV PATH $PATH

ARG JAR_FILE
ARG OUT_DIRECTORY

COPY ${OUT_DIRECTORY}/lib /root/oner365-system/lib/
COPY ${OUT_DIRECTORY}/resources /root/oner365-system/resources/
COPY ${JAR_FILE} /root/oner365-system/oner365-system-2.1.0.jar
ENTRYPOINT ["java","-jar","oner365-system-2.1.0.jar"]

# 本地执行
# mvn clean package -Dmaven.test.skip=true docker:build
# 服务器执行
# docker run -d --name oner365-system -p 8707:8707 --privileged=true --restart=always oner365-system
