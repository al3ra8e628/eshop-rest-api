


VM



install binary.



resource 
1 vm (OS, memory, cpu)
1 vm (OS, memory, cpu)
1 vm (OS, memory, cpu)

2 vm (OS, memory, cpu)
3 vm (OS, memory, cpu)
4 vm (OS, memory, cpu)

1.5 RAM
7.5 RAM 

basic distribution
jar 



orchestration platforms
loadbalancing
horizontal && vertical scaling
continuous delivery



linux

java
python
c#
php

windows 

java
python
c#
php


java -jar 
pip .py
exec



starting point basic distribution os   
copy your binary into the os
specify the execution command


templating for all application types executables..


orchestration platforms: docker


Dockerfile 

- what is docker file.
- what is docker image.
- what is docker container.
- what is docker-compose file.
- docker CLI most used commands.



Dockerfile

build ---> docker image

class / objects

horizontal scaling
item-managemnt_1 is a container of item-managemnt-image
item-managemnt_2 is a container of item-managemnt-image

simplest OT.



automate running two docker containers of the postgres image 
1- item-management-db
2- eshop-documents-db
3- by using docker-compose
4- build dockerFile for items management
5- build dockerFile for eshop documents
6- run the whole services with docker compose
-----------------------
manage the transactional state between item management persistence and documents persistence 
-----------------------
7- add gateway service to route http calls to internal micro services
8- apply authentication on the gateway so that only permitted user can access the micro services
9- apply the authorization on each micro service
10- add more functions that will allow us to practice different technologies like message brokers




direct    
             sync call
entity1 <----------------> entity2

              async call
entity2 ------------------------>  group(e1, e2, e3, e4)












to manage:
1- scalability (H, V).
2- availability (auto restart on failure).
3- to automate the process.
4- to secrets.
5- to manage the state.

docker-compose.yaml 

docker-compose up










1- practice docker compose..
2- @Transactional
3- gateway netflix zull gateway
4- identity provider (spring boot service, login/registration (JWT), manage users) ///  two options 
5- integrate the zull gateway with the identity provider to manage the authentication
6- implement the authorization for item-management && eshop documents
7- message brokers...








item table , item_images table

thread 1
phase one () { 
processing
db operation (read(big objects, read with update), write......)  -> open a new transaction...  
processing
}
phase two () { 
processing
db operation (read(big objects, read with update), write......)  -> reuse the opened transaction
processing
}
response
processing opening a new thread with db changes
COMMIT

on in case the above commit proceeded new thread 


ibox/outbox pattern...


outbox
save the document to another service 

ID | out handler   | content  | status     | retries 
1  | save document | payload  | processed  | 0

job 

ROLLBACK revert changes till the last commit.


item is not 

item loading.......

item inserting flow

phase created
phase pictures saved
phase ready to use 




new thread -->
     join multiple threads









processing takes much 



http -> prcessing takes 15mins


at mint one i need to commit something.



















