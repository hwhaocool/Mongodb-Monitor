FROM openjdk:8u232-jdk-stretch

MAINTAINER yellowtail

# 更新时间
ENV REFRESHED_AT 2019-12-26

# 拷贝代码
RUN mkdir /mm
WORKDIR /mm/

ARG envType=dev
ENV envType ${envType}

ENV LC_ALL C.UTF-8

EXPOSE 8080

COPY mm-web/target/mm-web-1.0.jar .

CMD ["java", "-jar", "-Duser.timezone=GMT+8", "-Djava.security.egd=file:/dev/./urandom", "mm-web-1.0.jar", "--spring.profiles.active=${envType}"]