FROM sungevity-docker-dockerv2-local.artifactoryonline.com/openjdk8:latest
MAINTAINER Andy Hull <ahull@sungevity.com>
ENV component equipment-service
ENV version 0.2.0
COPY target/universal/${component}-${version}.zip /opt/${component}.zip
WORKDIR /opt
RUN apt-get install unzip; unzip ${component}.zip; rm ${component}.zip
WORKDIR /opt/${component}-${version}
EXPOSE 9000
CMD bin/${component} > /var/log/${component}.log
