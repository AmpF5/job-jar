FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

RUN --mount=type=cache,target=/root/.m2 ./mvnw -q -B -DskipTests dependency:go-offline

COPY src/ src/
RUN --mount=type=cache,target=/root/.m2 ./mvnw -q -B -DskipTests package

RUN JAR=$(ls target/*-SNAPSHOT.jar 2>/dev/null || ls target/*.jar | head -n1) \
 && mkdir -p /out && cp "$JAR" /out/app.jar

FROM eclipse-temurin:17-jre AS runtime
WORKDIR /app
COPY --from=build /out/app.jar /app/app.jar

USER 10001
EXPOSE 8080
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75.0"
ENTRYPOINT ["java","-jar","/app/app.jar"]