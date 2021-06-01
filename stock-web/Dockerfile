FROM node:lts-alpine3.13

WORKDIR /usr/src/stock-web

COPY . .

RUN npm install

ENTRYPOINT ["npm", "start"]

EXPOSE 3000
