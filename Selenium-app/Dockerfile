FROM khangtd/crawler

RUN rm -rf /opt/app/

COPY ./youtube-crawler/ /opt/app/
COPY ./Procfile /opt/app/
COPY ./DOKKU_SCALE /opt/app/

WORKDIR /opt/app/

RUN cd /opt/app/ && mvn package

# RUN usermod -aG sudo chrome

# USER chrome

# ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/app/target/youtube-crawler-0.0.1-SNAPSHOT.jar"]

