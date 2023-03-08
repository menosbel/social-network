FROM node:16.4.0-alpine as builder

WORKDIR /app

COPY package.json .

COPY yarn.lock .

COPY shared shared

COPY client/package.json client/

#COPY server/package.json server/

RUN yarn install

COPY . .

RUN yarn workspace @social-network/web-client build

FROM node:16.4.0-alpine

COPY package.json .

COPY yarn.lock .

COPY server/package.json server/

RUN yarn install --production

COPY shared/base_tsconfig.json shared/base_tsconfig.json

COPY server server

COPY --from=builder /app/client/dist server/public

CMD yarn workspace @social-network/web-server start