FROM openjdk:8-jre-alpine 
COPY ./build/libs/edge-service.jar /usr/src/application/
WORKDIR /usr/src/application
EXPOSE 8000
CMD java $JAVA_OPTIONS -jar edge-service.jar 
