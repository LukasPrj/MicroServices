FROM openjdk:8-jre-alpine 
COPY ./build/libs/discovery-server-0.0.1-SNAPSHOT.jar /usr/src/eureka/
WORKDIR /usr/src/eureka
EXPOSE 9091 
CMD ["java", "-jar", "discovery-server-0.0.1-SNAPSHOT.jar"]
