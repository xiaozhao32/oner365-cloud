FROM jdk:1.8

VOLUME /root/oner365-datasource
RUN mkdir -p /root/oner365-datasource
WORKDIR /root/oner365-datasource
EXPOSE 8716

ENV TZ=Asia/Shanghai
ENV PATH $PATH

ARG JAR_FILE
ARG OUT_DIRECTORY

COPY ${OUT_DIRECTORY}/lib /root/oner365-datasource/lib/
COPY ${OUT_DIRECTORY}/resources /root/oner365-datasource/resources/
COPY ${JAR_FILE} /root/oner365-datasource/oner365-datasource-2.1.0.jar
ENTRYPOINT ["java","-jar","oner365-datasource-2.1.0.jar"]

# 本地执行
# mvn clean package -Dmaven.test.skip=true docker:build
# 服务器执行
# docker run -d --name oner365-datasource -p 8716:8716 --privileged=true --restart=always oner365-datasource
