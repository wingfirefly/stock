FROM openjdk:11

MAINTAINER Lcy <1031353743@qq.com>cd

COPY ./build/libs/stock-service-0.0.1.jar ./stock-service.jar

#RUN gradle build -x test

ENTRYPOINT ["java","-jar","stock-service.jar"]

EXPOSE 8998