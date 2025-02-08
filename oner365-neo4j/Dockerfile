FROM jdk:1.8

VOLUME /root/oner365-neo4j
RUN mkdir -p /root/oner365-neo4j
WORKDIR /root/oner365-neo4j
EXPOSE 8719

ENV TZ=Asia/Shanghai
ENV PATH $PATH
ENV SPRING_PROFILES_ACTIVE=dev
ENV SPRING_CLOUD_NACOS_SERVER_ADDR=oner365-nacos:8848

ARG JAR_FILE
ARG OUT_DIRECTORY

COPY ${OUT_DIRECTORY}/lib /root/oner365-neo4j/lib/
COPY ${OUT_DIRECTORY}/resources /root/oner365-neo4j/resources/
COPY ${JAR_FILE} /root/oner365-neo4j/oner365-neo4j-2.1.0.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "-Dspring.cloud.nacos.server-addr=${SPRING_CLOUD_NACOS_SERVER_ADDR}", \
	"-jar", "/root/oner365-neo4j/oner365-neo4j-2.1.0.jar"]

# 本地执行
# mvn clean package -Dmaven.test.skip=true docker:build
# 服务器执行
# docker run -d --name oner365-neo4j -p 8719:8719 --privileged=true --restart=always oner365-neo4j
