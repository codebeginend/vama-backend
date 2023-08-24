FROM maven:3.8.6-openjdk-11 AS build
VOLUME ~/.m2:/root/.m2
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean install -DskipTests=true
FROM adoptopenjdk/openjdk11
COPY --from=build /usr/src/app/target/*.jar /usr/app/vamaapi.jar
EXPOSE 8081
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","/usr/app/vamaapi.jar"]