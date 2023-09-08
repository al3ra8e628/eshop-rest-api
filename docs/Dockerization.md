1- install docker desktop.
https://docs.docker.com/desktop/install/windows-install/

2- learn about docker hub, pull images, build images, run image as a container.
- what is docker file.
- what is docker image.
- what is docker container.
- what is Dockerization file.
- what is docker-compose file.
- docker CLI most used commands.


Docker CLI:

1- docker images 
to list all local docker images (pulled or created)
2- docker rmi image_id
to delete docker image by id
3- docker ps
to list all active containers
4- docker rm -f container_id
to remove active container by id
5- docker exec -it da0fdd2d1862 bash
to open a shell session inside the container.
6- docker logs container_id 
to print the container logs
6- docker logs -f container_id
to print the container logs with following to print any new execution logs.
7- to build docker image from docker Dockerfile
   docker build -t image-name:image-tag .

8- docker image push:
    - create account on docker hub
    - login to your account at docker hub.
    - create a tag for local image to the image on the registry -> 
      docker tag eshop-api:v1.0.0 albayati/eshop-api:v1.0.0
    - execute the docker push command
      albayati/eshop-api:v1.0.0

docker-compose:
1- docker-compose up 
pull, build and run docker compose services
2- docker-compose up --scale items-management=2
to run service with more than one instance, NOTE: ports should not be specified for this service.


docker push 


docker build -t items-management:v1.0.0 .




3- pull and run postgres db by docker.
```shell
https://www.baeldung.com/ops/postgresql-docker-setup

docker pull postgres

docker run -itd -e POSTGRES_USER=eshop_documents -e POSTGRES_PASSWORD=eshop_documents -p 5432:5432 --name eshop_docs_db postgres
```

4- connect to the postgres running db by DBeaver and documents service.

5- build a local docker image for documents service.

6- apply same for eshop app.

7- run all services by docker compose.




