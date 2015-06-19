FROM ahullsungevity/openjdk8:latest
MAINTAINER Andy Hull <ahull@sungevity.com>
COPY equipment/target/universal/equipment-1.0-SNAPSHOT.zip /opt/equipment.zip
WORKDIR /opt
RUN apt-get install unzip; unzip equipment.zip; rm equipment.zip
WORKDIR /opt/equipment_1.0-SNAPSHOT
ENV EQUIPMENT_DB_URL EQUIPMENT_DB_USER EQUIPMENT_DB_PASSWORD
EXPOSE 9000
CMD bin/production-estimator
