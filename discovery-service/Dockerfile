FROM openjdk:8-jre-alpine 

COPY ./build/libs/discovery-service.jar /usr/src/application/

WORKDIR /usr/src/application

EXPOSE 9091

CMD java $JAVA_OPTIONS -jar discovery-service.jar

