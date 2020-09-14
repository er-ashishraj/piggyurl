# Alpine Linux with OpenJDK JRE
FROM openjdk:11

# Copy war file
COPY bootstrap.jar /bootstrap.war

# run the app
CMD ["/usr/bin/java", "-jar", "/bootstrap.war"]