FROM openjdk

WORKDIR /app

COPY target/play-with-docker-0.0.1-SNAPSHOT.jar /app/play-with-docker.jar

ENTRYPOINT ["java", "-jar", "play-with-docker.jar"] 