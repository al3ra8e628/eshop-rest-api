- to enable spring job we should add following annotation to the spring APP class: @EnableScheduling
















producer send message  ----> Rabbit.  

case rabbit is up.  happy 
case rabbit is down.  worst case... retry...



outbox 
ID  |  message_type        |  message_payload |  status   |  retries | failure reason
1      STOCK_ITEM_UPDATED    "{ID: 1231}"        FAILED      5

COMPONENT: 

JOBs , Schedulers 

every 5 seconds -> send outbox events... 

Spring Schedulers,,, 

rabbit

-------------------------

rabbit

Listener "{ID: 1231}" -> eshop..

inbox
ID  |  message_type        |  message_payload |  status   |  retries  | failure reason
1      STOCK_ITEM_UPDATED    "{ID: 1231}"        FAILED      5


JOBs , Schedulers
every 5 seconds -> send outbox events...


"{ID: 1231}" parse by object mapper.
"{ID: 1231}" id should be matching exiting item.
"{ID: 1231}" technical or business validations.
"{ID: 1231}" to update other service or update db records with complex queries that might fail.

inbox 


access to the message broker...




