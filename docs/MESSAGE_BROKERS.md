# Event Driven Development: (broadcasting)

- where the application state can be updated by listening for state update events from outside the service.

# Example: 
  - EShop Item Management Service
  - EShop Stock Service

## problem statement:
while having a specific item in the actual shop stock is a mandatory condition for applying any operation on a specific item inside the item management service, this information is persisted and managed in another separate service which is the eshop stock service.

## available solutions:

- moving is in stock state into the item management service.
  - hard to apply when stock management service is a big service. 
  - hard to apply when this information is only important for item management service, 
     (have to be centralized between all the services). 

- broadcast an event from the stock management service about an item state change whenever the item stock state is changed, and listen for this event from all other services that is interested in this information and keep it updated on the item details.
  - the business of the stock management is a blackbox in item management service.
  - this information is getting updated always for all the microservices that is listening for this event.



# Apply the broadcasting solution:

1. add support for rabbit MQ in the docker compose to be the message broker for the eshop events:
    ```yaml
      rabbitmq:
        image: rabbitmq:management
        container_name: rabbitmq
        environment:
          - RABBITMQ_DEFAULT_USER=guest
          - RABBITMQ_DEFAULT_PASS=guest
        ports:
          - "5672:5672"
          - "15672:15672"
    ```

### Consumer Services Changes:
in the item management service or any service that need to listen for the published events we need to apply couple of changes:
1. update service pom.xml by adding this property to the properties section:
      ```xml
      <spring-cloud.version>Edgware.SR1</spring-cloud.version>
      ```
2. add spring cloud dependencies to the dependency Management:
     ```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>2022.0.4</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
     ```
3. add spring cloud rabbit mq starter dependency:
     ```xml
     <dependency>
         <groupId>org.springframework.cloud</groupId>
         <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
     </dependency>
     ```
4. add rabbit connection and stock queue connection configurations:
     ```properties
     spring.rabbitmq.host=localhost
     spring.rabbitmq.port=5672
     spring.rabbitmq.username=guest
     spring.rabbitmq.password=guest
     spring.cloud.stream.bindings.stockUpdate-in-0.group=item-management
     ```
5. implement the event listener java class:
     ```java
      @Slf4j
      @Configuration
      public class StockServiceEventsConsumer {
            @Bean
            public Consumer<Message<String>> stockUpdate() {
                return message -> {
                String stockUpdateEvent = message.getPayload();
                LOGGER.info("event consumed {}", stockUpdateEvent);
                };
            }
      }
      ```


### Event Producer Service (Stock Management):
1. update service pom.xml by adding this property to the properties section:
      ```xml
      <spring-cloud.version>Edgware.SR1</spring-cloud.version>
      ```
2. add spring cloud dependencies to the dependency Management:
     ```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>2022.0.4</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
     ```
3. add spring cloud rabbit mq starter dependency:
     ```xml
     <dependency>
         <groupId>org.springframework.cloud</groupId>
         <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
     </dependency>
     ```
4. add rabbit connection and stock queue connection configurations:
     ```properties
     spring.rabbitmq.host=localhost
     spring.rabbitmq.port=5672
     spring.rabbitmq.username=guest
     spring.rabbitmq.password=guest
     ```
5. implement the event listener java class:
     ```java
        @Component
        public class EventPublisher {
               
               private final RabbitTemplate template;
               private final ObjectMapper objectMapper;
               private final String stockEventsExchangeName;
               
               public EventPublisher(RabbitTemplate template,
                                     ObjectMapper objectMapper,
                                     @Value("${stock.update.events.exchangeName:stockUpdate-in-0}")
                                     String stockEventsExchangeName) {
                   this.template = template;
                   this.objectMapper = objectMapper;
                   this.stockEventsExchangeName = stockEventsExchangeName;
               }
               
               public void publishStockUpdateEvent(String itemReference,
                                                   String stockStatus) throws JsonProcessingException {
                   HashMap<Object, Object> message = new HashMap<>();
               
                   message.put("itemReference", itemReference);
                   message.put("stockStatus", stockStatus);
               
                   template.convertAndSend(stockEventsExchangeName, "#", objectMapper.writeValueAsString(message));
               }
        }
    ```



#### Note : You can try the Event Publishing from the Rabbit UI directly without having a ready service up for the stock Management.