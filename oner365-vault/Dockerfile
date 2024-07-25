FROM jdk:1.8

VOLUME /root/oner365-vault
RUN mkdir -p /root/oner365-vault
WORKDIR /root/oner365-vault
EXPOSE 8722

ENV TZ=Asia/Shanghai
ENV PATH $PATH

ARG JAR_FILE
ARG OUT_DIRECTORY

COPY ${OUT_DIRECTORY}/lib /root/oner365-vault/lib/
COPY ${OUT_DIRECTORY}/resources /root/oner365-vault/resources/
COPY ${JAR_FILE} /root/oner365-vault/oner365-vault-2.1.0.jar
ENTRYPOINT ["java","-jar","oner365-vault-2.1.0.jar"]

# 本地执行
# mvn clean package -Dmaven.test.skip=true docker:build
# 服务器执行
# docker run -d --name oner365-vault -p 8722:8722 --privileged=true --restart=always oner365-vault
