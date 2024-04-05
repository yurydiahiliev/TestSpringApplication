FROM node:20.11.0 as build
WORKDIR /app
COPY frontend/app/package*.json ./
RUN npm install
COPY frontend/app .
RUN npm run build
EXPOSE 3000
CMD ["npm", "start"]

#FROM nginx:alpine
#COPY --from=build /app/build /usr/share/nginx/html
#COPY frontend/app/nginx/default.conf /etc/nginx/conf.d/default.conf
#EXPOSE 8083
#CMD ["nginx", "-g", "daemon off;"]