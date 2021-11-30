##
## Stage 1 - Build using Maven
##
FROM maven:3.8.4-openjdk-17 as BUILD

WORKDIR /project
# Dependencies caching
COPY pom.xml /project/pom.xml
RUN mvn org.apache.maven.plugins:maven-dependency-plugin:3.1.1:go-offline

# Copy entire project and build it
COPY . /project/
RUN mvn clean install

##
## Stage 2 - Create runnable image
##
FROM maven:3.8.4-openjdk-17

COPY --from=BUILD /project/target/route-planner.jar /opt/uamk/route-planner.jar

WORKDIR /opt/uamk/

ENTRYPOINT ["java", "-jar", "route-planner.jar"]

EXPOSE 8080
