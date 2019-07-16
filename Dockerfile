FROM registry.cn-shenzhen.aliyuncs.com/cuishiwen/geek-java8-v1:latest

MAINTAINER yellowtail

# 更新时间
ENV REFRESHED_AT 2019-07-16

# 拷贝代码
RUN mkdir /mm
WORKDIR /mm/

COPY skmr-web/build/libs/skmr-web-1.0.jar .

ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom"

ENV LC_ALL C.UTF-8

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "skmr-web-1.0.jar", "--spring.profiles.active=prod"]