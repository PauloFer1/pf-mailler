FROM openjdk:8-jdk-alpine

ENV sender_email=paulofernandes.tarambola@gmail.com
ENV sender_password=snoof00000
ENV spring_datasource_password=58f4976ddde066f
ENV spring_datasource_username=be550795dbbf32
ENV realemail_api_key=PYJCH6A512N0GLQ3D77TRB45M63K8ZX489S0IWEO2V1F9U

VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
