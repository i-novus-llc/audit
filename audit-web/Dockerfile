FROM gcr.io/distroless/java:11

EXPOSE 8083

ARG JAR_FILE
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]
