FROM sungevity-docker-dockerv2-local.artifactoryonline.com/openjdk7:latest
MAINTAINER Software Platform Engineering <software-platform-dev@sungevity.com>
ENV component equipment-service
ENV version 0.2.1
COPY target/universal/${component}-${version}.zip /opt/${component}.zip
WORKDIR /opt
RUN apt-get install unzip; unzip ${component}.zip; rm ${component}.zip
WORKDIR /opt/${component}-${version}
EXPOSE 9000
CMD bin/${component} > /var/log/${component}.log
