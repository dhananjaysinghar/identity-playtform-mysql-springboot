FROM bellsoft/liberica-openjdk-alpine:17
VOLUME /tmp
WORKDIR /app
COPY target/Identity-platform-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "Identity-platform-0.0.1-SNAPSHOT.jar"]

#docker build -f Dockerfile -t test-app .
