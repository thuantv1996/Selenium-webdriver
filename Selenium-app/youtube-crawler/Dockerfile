FROM openjdk:7u181-jdk-jessie
RUN apt-get install maven default-jre default-jdk -y
RUN echo $JAVA_HOME
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]