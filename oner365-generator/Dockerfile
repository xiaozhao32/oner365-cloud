FROM jdk:1.8

VOLUME /root/oner365-generator
RUN mkdir -p /root/oner365-generator
WORKDIR /root/oner365-generator
EXPOSE 8711

ENV TZ=Asia/Shanghai
ENV PATH $PATH

ARG JAR_FILE
ARG OUT_DIRECTORY

COPY ${OUT_DIRECTORY}/lib /root/oner365-generator/lib/
COPY ${OUT_DIRECTORY}/resources /root/oner365-generator/resources/
COPY ${JAR_FILE} /root/oner365-generator/oner365-generator-2.1.0.jar
ENTRYPOINT ["java","-jar","oner365-generator-2.1.0.jar"]

# 本地执行
# mvn clean package -Dmaven.test.skip=true docker:build
# 服务器执行
# docker run -d --name oner365-generator -p 8711:8711 --privileged=true --restart=always oner365-generator
