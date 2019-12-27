FROM openjdk:8u232-jdk-stretch

# 更新源为 163，在这里不install，会出错，有需要自行到容器install
RUN mv /etc/apt/sources.list /etc/apt/sources.list.bak && echo "deb http://mirrors.163.com/debian/ jessie main non-free contrib" >/etc/apt/sources.list && echo "deb http://mirrors.163.com/debian/ jessie-proposed-updates main non-free contrib" >>/etc/apt/sources.list && echo "deb-src http://mirrors.163.com/debian/ jessie main non-free contrib" >>/etc/apt/sources.list && echo "deb-src http://mirrors.163.com/debian/ jessie-proposed-updates main non-free contrib" >>/etc/apt/sources.list

MAINTAINER yellowtail

# 更新时间
ENV REFRESHED_AT 2019-12-26

# 拷贝代码
RUN mkdir /mm
WORKDIR /mm/

ENV LC_ALL C.UTF-8

EXPOSE 8080

COPY mm-web/target/mm-web-1.0.jar .

CMD ["java", "-jar", "-Duser.timezone=GMT+8", "-Djava.security.egd=file:/dev/./urandom", "mm-web-1.0.jar"]