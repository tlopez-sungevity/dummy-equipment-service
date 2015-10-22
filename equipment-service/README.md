Running equipment-service in a Docker Container
===============================================

docker run -d -p 9000:9000 -e "EQUIPMENT_DB_URL=$EQUIPMENT_DB_URL" -e "EQUIPMENT_DB_USER=$EQUIPMENT_DB_USER" -e "EQUIPMENT_DB_PASSWORD=$EQUIPMENT_DB_PASSWORD" sungevity-docker-dockerv2-local.artifactoryonline.com/equipment-service

Shell Into a Running Container
------------------------------
docker exec -it <container-id> bash 