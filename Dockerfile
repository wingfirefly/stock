FROM bosspen1/javanode

RUN mkdir -p /data/deploy/ 

COPY . /data/deploy/

RUN chmod +x /data/deploy/bin/*.sh

# WORKDIR /data/deploy/bin
# CMD /data/deploy/bin/stock-service.sh
# CMD /data/deploy/bin/stock-web.sh
