FROM jdk:1.8

VOLUME /root/oner365-clickhouse
RUN mkdir -p /root/oner365-clickhouse
WORKDIR /root/oner365-clickhouse
EXPOSE 8731

ENV TZ=Asia/Shanghai
ENV PATH $PATH

ARG JAR_FILE
ARG OUT_DIRECTORY

COPY ${OUT_DIRECTORY}/lib /root/oner365-clickhouse/lib/
COPY ${OUT_DIRECTORY}/resources /root/oner365-clickhouse/resources/
COPY ${JAR_FILE} /root/oner365-clickhouse/oner365-clickhouse-2.1.0.jar
ENTRYPOINT ["java","-jar","oner365-clickhouse-2.1.0.jar"]

# 本地执行
# mvn clean package -Dmaven.test.skip=true docker:build
# 服务器执行
# docker run -d --name oner365-clickhouse -p 8731:8731 --privileged=true --restart=always oner365-clickhouse
