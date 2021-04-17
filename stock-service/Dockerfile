FROM gradle:jdk11

MAINTAINER Lcy <1031353743@qq.com>

WORKDIR /usr/src/app

COPY . .

RUN gradle build -x test

RUN gradle copyJar

COPY /stock-service-0.0.1.jar /stock-service.jar

ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","stock-service.jar"]

EXPOSE 8998