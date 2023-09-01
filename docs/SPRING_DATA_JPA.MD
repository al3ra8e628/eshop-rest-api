to conect with oracle db:

CREATE USER eshop_app IDENTIFIED BY eshop_app;
GRANT ALL PRIVILEGES TO eshop_app;

CREATE USER eshop_docs_app IDENTIFIED BY eshop_docs_app;
GRANT ALL PRIVILEGES TO eshop_docs_app;



##Transactional blocks..
- spring is opening a transaction on first operation to the db.
- use @Transactional to open the transaction early.
- db commit is executed when the thread finish execution by default.
- db commit is executed when the @Transactional method finish execution when @Transactional is used.
- db rollback is executed when an exception is thrown during the execution.
- to apply specific changes to the db immediately during working within a different transaction.
    - use @Transactional(propagation = Propagation.REQUIRES_NEW)

- handle transactional blocks through multiple microservices you have multiple options:
  1- use thread join to handle the invoking...
  2- late the microservices call to the last execution part,
  Note: the db commit might fail by the end but by applying the approach you might get lower risk.
  3- use the inbox/outbox pattern to handle any failure management,
  and suppose the other services calls are success by applying the required validations
  that might fail on the other service.
