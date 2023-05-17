FROM openjdk:17-jdk-slim
ADD ./build/libs/*.jar News.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "News.jar"]