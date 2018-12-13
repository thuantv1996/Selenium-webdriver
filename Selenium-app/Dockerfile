FROM ubuntu:16.04

RUN  apt-get update && apt-get install wget curl maven unzip default-jre default-jdk -y

RUN apt-get update && apt-get install -y libappindicator1 fonts-liberation

RUN apt-get update && wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb && dpkg gdebi google-chrome*.deb
ENV JAVA_HOME /usr/lib/jvm/default-java/

RUN echo $JAVA_HOME

RUN wget https://chromedriver.storage.googleapis.com/2.45/chromedriver_linux64.zip -O /opt/chromedriver.zip && unzip /opt/chromedriver.zip

ENV SELENIUM_WEB_DRIVER /opt/chromedriver

COPY ./youtube-crawler/ /opt/app/

WORKDIR /opt/app/

RUN cd /opt/app/ && mvn package

# ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/app/target/youtube-crawler-0.0.1-SNAPSHOT.jar"]