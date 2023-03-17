item resource
```json
{
  "name": "item name",
  "itemCategory": [
    "IT",
    "CLOTHS",
    "GROCERY"
  ],
  "inStock": [
    true,
    false
  ],
  "rating": [
    1...5
  ],
  "unit": [
    "k",
    "size"
  ],
  "price": "USD 50.00",
  "itemPictures": [
    "main pic",
    "secondary 1",
    "secondary 2"
  ]
}
```

EShop:
Administration Portal:
Items management:
- as an admin user i should be able to create a new item.
- as an admin user i should be able to update item details.
- as an admin user i should be able to delete item.
- as an admin user i should be able to list items with filters and sort functionality.
- as an admin i should be able to see all pending orders.


Customers Portal:
Cart Management:
- as a customer user i should be able to add an item to my cart.
- as a customer user i should be able to delete item from my cart.
- as a customer user i should be able to list my cart items.
- as a customer user i should be able to submit purchase request (add address details) to my cart items.
- mark purchase request as derived.


EShopDeliveryService:
CaptionsPortal:
- as a caption i should be able to see the pending orders that i can pick and deliver to customer.
- as a caption i should be pick item to be delivered by me, and the purchase process status on customer portal should be updated to in delivery process.


Identity Management:
- the system should provided api to log in to the system, with JWT tokens.
- jwt token should be the single source of truth for the user roles and userDetails.
- jwt token should be validated (exp, issuer....) in a abstraction way so that it can be moved to another service (gateway service).
- rest apis should be validated against user authorized roles.



