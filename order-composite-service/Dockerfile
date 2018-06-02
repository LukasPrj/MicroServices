FROM openjdk:8-jre-alpine 
COPY ./build/libs/order-composite-service-0.0.1-SNAPSHOT.jar /usr/src/application/
WORKDIR /usr/src/application
EXPOSE 8000
CMD java $JAVA_OPTIONS -jar order-composite-service-0.0.1-SNAPSHOT.jar
