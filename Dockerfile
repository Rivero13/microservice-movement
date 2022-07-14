FROM openjdk:11
VOLUME /tmp
ADD ./target/microservice-movement-0.0.1-SNAPSHOT.jar microservice-movement.jar
ENTRYPOINT ["java", "-jar", "/microservice-movement.jar"]