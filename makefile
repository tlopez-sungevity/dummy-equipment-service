all : dockerize
	
play-app : 
	cd equipment-service; activator dist

dockerize : play-app
	docker build -t sungevity-docker-dockerv2-local.artifactoryonline.com/equipment-service .

publish : dockerize
	docker push sungevity-docker-dockerv2-local.artifactoryonline.com/equipment-service
