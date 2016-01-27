Building
========

Requires JDK 7.

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

The simplest way is to run the container is to define an env file containing the values for the environment variables:

```bash
docker run -d -p 9000:9000 --env-file=~/env/<your-env-file> sungevity-docker-dockerv2-local.artifactoryonline.com/equipment-service
```

Shell Into a Running Container
------------------------------

Useful for debugging.

```bash
docker exec -it <container-id> bash 
```

HELP! My Container Doesn't Start
================================

Try removing the -d flag to get some insight. When set, this flag runs the container in the background. Removing the flag will run the container in the same shell session.