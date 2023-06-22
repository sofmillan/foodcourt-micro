FROM openjdk:11
EXPOSE 8080
COPY /build/libs/foodcourt-micro-0.0.1-SNAPSHOT.jar foodcourt.jar
ENTRYPOINT ["java","-jar","foodcourt.jar"]
