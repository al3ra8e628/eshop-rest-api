
# api gateway


```text
practices:
1- create the api gateway service.
2- create the routes.
3- create the docker file and build docker image.
4- add the api gateway service to docker-compose.
5- use web filters. 

6- compare calling the items management service with and without api.
7- make the item management service not accessible from outside the docker compose env.
8- apply an example of creating custom gateway predicate.
```


```properties
spring.cloud.gateway.default-filters[0].name=StripPrefix

spring.cloud.gateway.routes[0].id=items-management
spring.cloud.gateway.routes[0].uri=http://localhost:8081/
spring.cloud.gateway.routes[0].predicates[0]=Path=/items-api/**
```




Java EE // dispatcher servlet // main servlet.

web.xml


to be executed before the servlet execution

webFilters



{
FirstFilter  
SecondFilter
ThirdFilter
onGet
}


onGet(request , response) {


}




onPost (Request request, Response response)



