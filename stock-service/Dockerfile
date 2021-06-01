FROM gradle:jdk8

WORKDIR /usr/src/stock-service

RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

COPY . .

ENTRYPOINT ["gradle","bootRun"]

EXPOSE 8088
