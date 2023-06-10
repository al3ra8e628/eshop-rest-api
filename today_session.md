Session topics:


2- integrate eshop application with oracledb:
   - introduction to ORM (Object Relational Mapping). done
   - introduction to JPA hibernate. done
   - add required dependencies for spring to connect with oracle db via JPA. done
   - apply pagination and sorting.
   
   - listing and filter with advanced hibernate filters
    
   - build entities for item and item_documents. 
   - build jpa repository for item and item documents.


3- working with spring profiles.






DB Table 


items table
id | name | category | isInStock  | rating | unit | price create_date_time


item_pictures table
reference | item_id | meta_data | content | content_type


public class Item {
private Long id;
private String name;
private ItemCategory category;
private Boolean isInStock;
private Integer rating;
private ItemUnit unit;
private Money price;
private LocalDateTime creationDateTime;
private List<Document> pictures;
}


Library to handle the mapping between the database table and the programming language object.

strategy design pattern:

i will implement one java class ItemEntity
no native sql queries will be written.. 


hibernate dialect.. 


oracle sysdate + 1 day.
postgres date_now



dialect 


manual manage 
item persist, 
but the item pictures 
















to be checked
//        @Spec(path = "creationDateTime", params = "creationDateTime", spec = Like.class),
dynamic assertion is not null..













add cart....
itemId, 

is item in stock.



named query jpql..
spring data method named query.






eshop: items table
id

eshop: documents
file_id, resource_id, resource_type, content_type, meta_data


"item":{

"documents": [
   
   ]
}


list 100 item -> 100 http:

http:













eshop documents: files
file_id, resource_id, resource_type, bytes [] blob, content_type, meta_data_  



create item::
create item repository:

1- save item in local db.
2- invoke a rest call for the documents service to persist the documents.

Notes: 
1- the tow save operation shall be done in one transactional block...
    - hibernate will roll back automatically any db change on any failure..    

2- 








Eshop Documents Service:

Document Controller:
   1- create document   
   2- get document 







Eshop App:
    1- Interface to communicate Documents service.
    before persisting the service shall invoke the document service to persist the item pictures...
    when client request to download picture, the download request shall be redirected to document service. 




    Rest call
    - Rest Tempalte multipart POST / GET / DELETE  
    - Feign CLient 
    - WebClient https




cascade type
master/slave
item/pictures


delete item,  



ALL, PERSIST, MERGE, REMOVE, REFRESH, DETACH;








Eshop Documents Service library 


best micro 


concerns:
    1- performance we have here two http calls instead of one.
    2- i had to handle the communication between eshop and eshop documents.
    3- i have to communicate with document with specific details that might change in future.
    4- first i build modular app then sperate to multi mic


                                  athontication
                                                                    athorization
client ->->->->->->->->->->->->-> API Gateway   ->->->->->->->->->  targeted service  
mob app                                                               might need lookups from another micro service              
web                                                                   might need to apply a change on a nother micro service
another service

create item 

post 
single 








unit testing
integration testing 
contract testing, 
end to end testing
performance testing

beta











modes 





















