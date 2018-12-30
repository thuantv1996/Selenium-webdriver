FROM khangtd/crawler

ENV JAVA_HOME /usr/lib/jvm/default-java/

RUN echo $JAVA_HOME

RUN cd /opt/ && wget https://chromedriver.storage.googleapis.com/2.45/chromedriver_linux64.zip && unzip /opt/chromedriver_linux64.zip

ENV SELENIUM_WEB_DRIVER /opt/chromedriver

COPY ./youtube-crawler/ /opt/app/

WORKDIR /opt/app/

RUN cd /opt/app/ && mvn package

# RUN usermod -aG sudo chrome

# USER chrome

# ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/app/target/youtube-crawler-0.0.1-SNAPSHOT.jar"]

