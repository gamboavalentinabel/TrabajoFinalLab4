FROM node:21

WORKDIR /myAppVUE

RUN npm install -g http-server

COPY package*.json .
RUN npm install

COPY . .
RUN  npm run build

EXPOSE 5173
CMD [ "http-server", "-p 5173", "dist" ]