FROM node:25-alpine

WORKDIR /app

COPY package.json ./

Run npm install -g npm
RUN npm install -g pnpm

RUN pnpm install 

COPY . .

CMD ["pnpm","start"]
