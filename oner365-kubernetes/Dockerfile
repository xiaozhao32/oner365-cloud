FROM jdk:1.8

VOLUME /root/oner365-kubernetes
RUN mkdir -p /root/oner365-kubernetes
WORKDIR /root/oner365-kubernetes
EXPOSE 8728

ENV TZ=Asia/Shanghai
ENV PATH $PATH

ARG JAR_FILE
ARG OUT_DIRECTORY

COPY ${OUT_DIRECTORY}/lib /root/oner365-kubernetes/lib/
COPY ${OUT_DIRECTORY}/resources /root/oner365-kubernetes/resources/
COPY ${JAR_FILE} /root/oner365-kubernetes/oner365-kubernetes-2.1.0.jar
ENTRYPOINT ["java","-jar","oner365-kubernetes-2.1.0.jar"]

# 本地执行
# mvn clean package -Dmaven.test.skip=true docker:build
# 服务器执行
# docker run -d --name oner365-kubernetes -p 8728:8728 --privileged=true --restart=always oner365-kubernetes
