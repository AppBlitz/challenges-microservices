FROM node:25-alpine

WORKDIR /app

COPY package.json ./

RUN npm install -g pnpm@10.32

RUN pnpm install

COPY . .

CMD ["pnpm","start"]
