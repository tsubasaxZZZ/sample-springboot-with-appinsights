FROM mcr.microsoft.com/openjdk/jdk:17-distroless
ARG JAR_FILE=demo/target/*.jar
COPY ${JAR_FILE} app.jar
COPY "demo/applicationinsights-agent-3.4.16.jar" .
ENTRYPOINT ["java","-javaagent:applicationinsights-agent-3.4.16.jar","-jar","/app.jar"]
