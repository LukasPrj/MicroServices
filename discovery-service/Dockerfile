FROM openjdk:8-jre-alpine 

COPY ./build/libs/discovery-server-0.0.1-SNAPSHOT.jar /usr/src/application/

WORKDIR /usr/src/application

EXPOSE 9091

CMD java $JAVA_OPTIONS -jar discovery-server-0.0.1-SNAPSHOT.jar

