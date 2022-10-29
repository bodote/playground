FROM ibm-semeru-runtimes:open-17-jre

WORKDIR /home/microservice
COPY app.jar /home/microservice/app.jar
EXPOSE 8080

ENV TZ=Europe/Berlin
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone && dpkg-reconfigure -f noninteractive tzdata

RUN locale-gen de_DE.UTF-8
ENV LANG=de_DE.UTF-8 LANGUAGE=de_DE.UTF-8 LC_ALL=de_DE.UTF-8

ENTRYPOINT ["java", "-jar", "-Dlog4j2.formatMsgNoLookups=true", "app.jar"]

