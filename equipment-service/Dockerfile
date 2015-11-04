FROM sungevity-docker-dockerv2-local.artifactoryonline.com/openjdk8:latest
MAINTAINER Andy Hull <ahull@sungevity.com>
ENV version 0.1.0
COPY target/universal/equipment-service-${version}.zip /opt/equipment-service.zip
WORKDIR /opt
RUN apt-get install unzip; unzip equipment-service.zip; rm equipment-service.zip
WORKDIR /opt/equipment-service-${version}
EXPOSE 9000
CMD bin/equipment-service > /var/log/equipment-service.log
