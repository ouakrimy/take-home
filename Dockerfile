FROM openjdk:21-jdk

COPY target/takehome-0.0.1-SNAPSHOT.jar takehome-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","takehome-0.0.1-SNAPSHOT.jar"]