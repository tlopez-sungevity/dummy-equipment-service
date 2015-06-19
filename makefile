all : play-app docker
	
play-app : 
	cd equipment; activator dist

docker :
	docker build -t ahullsungevity/equipment .
