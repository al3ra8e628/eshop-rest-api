# History:

Internet

- connecting two or more of applications with each other.

- Files.

- RMI (Remote Method Invocation) {
  method in the first system (sync users information).
  the second system will invoke the exposed method from the first system.

    - can only be done between multiple systems with same programming language.
      }

- SOAP (Simple Object Access Protocol) (wsdl) built in xml format.
    - server , generate service based on the wsdl, regardless the programming language
    - client , generate client based on the wsdl, regardless the programming language

  C# -> server.

  app1 python , build client based on the wsdl,
  app2 java , build client based on the wsdl,
  app3 php , build client based on the wsdl

- Sockets (realtime communication)

- HTTP + REST APIs
    - json structure

- GRPc proto buffer file

# Hypertext Transfer Protocol (HTTP) protocol.

http client autorety (error code = 504).

# HTTP methods.

POST: add new resource to the service.
response: the created resource.
happy case reponse status code (CREATED) 201

PUT: to update an already exists resource details.
resource id should be passed as path variable.
response: the UPDATE resource.
happy case reponse status code (OK) 200
if the resource is not exist return 404 NOT_FOUND.

GET: to fetch one or more resources information from the server.
- no request body
- get calls should not change any state in the system or the db, only for data fetching.
- for listing, if no records the response should be empty list.
- for get by id, if the resource is not exist return 404 NOT_FOUND.

DELETE: to delete a specific resource from the server.

- no request body
- delete calls should return the deleted resource details.
- happy case reponse status code (OK) 200
- if the resource is not exist return 404 NOT_FOUND.

OPTIONS: used by the browsers fetch some browser related details from the server.

# Open API Specification.

the structure of the rest api url

- http:localhost:8080/api/v1.0/laksdjlakj/asdasa/asd android
- http:localhost:8080/api/v1.1/laksdjlakj/asdasa/asd react
- request body,
- response body,
- response status OKAY 200 , created 201, not found 404 , not authorized, not authenticated, time out, 500 internal
  server error, bad request 400.

1- query parameters (?key = value, & key = value)  => only for filter the outputs...
/// $ book_name != "ahmad$ali$$$$$$$$$"

2- request body. used as input for the system.
{
bookName: "ahmad$ali$$$$$$$$$"
}

# Swagger Documentation.
can be found in:
http://localhost:8080/swagger-ui/index.html
http://localhost:8080/api-docs

customize swagger documentation:
endpoint:
@Operation(summary = "Find student by id, also returns a link to retrieve all students with rel - all-students")

request body:
@Schema(description = "All details eshop item. ")
@Schema(description = "item price value", example = "USD 100.00", type = "string")
@Schema(name = "Name should have atleast 2 characters")
@Size(min = 2, message = "Name should have atleast 2 characters")


- Spring Object Mapper.
- java Jackson serialize and deserialize objects..
- hateoas links... add link to get the self, get main document.



- File Uploading.
- file downloading base64.
- file downloading octet stream.
