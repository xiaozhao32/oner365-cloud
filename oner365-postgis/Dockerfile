FROM jdk:1.8

VOLUME /root/oner365-postgis
RUN mkdir -p /root/oner365-postgis
WORKDIR /root/oner365-postgis
EXPOSE 8729

ENV TZ=Asia/Shanghai
ENV PATH $PATH

ARG JAR_FILE
ARG OUT_DIRECTORY

COPY ${OUT_DIRECTORY}/lib /root/oner365-postgis/lib/
COPY ${OUT_DIRECTORY}/resources /root/oner365-postgis/resources/
COPY ${JAR_FILE} /root/oner365-postgis/oner365-postgis-2.1.0.jar
ENTRYPOINT ["java","-jar","oner365-postgis-2.1.0.jar"]

# 本地执行
# mvn clean package -Dmaven.test.skip=true docker:build
# 服务器执行
# docker run -d --name oner365-postgis -p 8729:8729 --privileged=true --restart=always oner365-postgis
