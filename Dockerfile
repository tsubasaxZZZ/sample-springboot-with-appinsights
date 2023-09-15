FROM mcr.microsoft.com/openjdk/jdk:17-mariner AS build
COPY . .
RUN pwd && ls -l
RUN cd demo && ./mvnw package -DskipTests

FROM mcr.microsoft.com/openjdk/jdk:17-distroless
ARG JAR_FILE=demo/target/*.jar
# COPY from the build context to the container filesystem
COPY --from=build ${JAR_FILE} app.jar
COPY --from=build "demo/applicationinsights-agent-3.4.16.jar" .
ENTRYPOINT ["java","-javaagent:applicationinsights-agent-3.4.16.jar","-jar","/app.jar"]
