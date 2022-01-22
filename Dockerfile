FROM alpine:3.15
RUN apk update && \
    apk add openjdk11 && \
    mkdir /application/
WORKDIR /application/
COPY build/libs/ .
COPY .env .
CMD ["java", "-jar", "com.kastro.authentication-service-0.0.1-all.jar"]