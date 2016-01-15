Building
========

Requires JDK 8.

You will need to have docker installed and running for your environment (including docker-machine on OSX).

Run "make" from the repo root directory to create a docker container for the microservice.

Run "make publish" to publish the docker container to the registry.

Configuration
=============
This dockerized microservice is configured using the following environment variables:

| Environment Variable  | Description                  |
|-----------------------|------------------------------|
| EQUIPMENT_DB_URL      | &lt;jdbc-style-URL-to-DB&gt; |
| EQUIPMENT_DB_USER     | &lt;user&gt;                 |
| EQUIPMENT_DB_PASSWORD | &lt;password&gt;             |

Running in a Docker Container
=============================

docker run -d -p 9000:9000 -e "EQUIPMENT_DB_URL=$EQUIPMENT_DB_URL" -e "EQUIPMENT_DB_USER=$EQUIPMENT_DB_USER" -e "EQUIPMENT_DB_PASSWORD=$EQUIPMENT_DB_PASSWORD" sungevity-docker-dockerv2-local.artifactoryonline.com/equipment-service

Shell Into a Running Container
------------------------------
docker exec -it <container-id> bash 
