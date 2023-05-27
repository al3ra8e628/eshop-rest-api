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



