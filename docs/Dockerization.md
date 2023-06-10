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