FROM jdk:1.8

VOLUME /root/oner365-swagger
RUN mkdir -p /root/oner365-swagger
WORKDIR /root/oner365-swagger
EXPOSE 8714

ENV TZ=Asia/Shanghai
ENV PATH $PATH

ARG JAR_FILE
ARG OUT_DIRECTORY

COPY ${OUT_DIRECTORY}/lib /root/oner365-swagger/lib/
COPY ${OUT_DIRECTORY}/resources /root/oner365-swagger/resources/
COPY ${JAR_FILE} /root/oner365-swagger/oner365-swagger-2.1.0.jar
ENTRYPOINT ["java","-jar","oner365-swagger-2.1.0.jar"]

# 本地执行
# mvn clean package -Dmaven.test.skip=true docker:build
# 服务器执行
# docker run -d --name oner365-swagger -p 8714:8714 --privileged=true --restart=always oner365-swagger
