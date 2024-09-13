# Brokerage Firm
Brokerage firm backend application developed for case study

## Requirements
Java 17 and Maven version 3.3.2

## Installation
```bash
mvn clean package
```

## Running the application
```bash
mvn spring-boot:run
```

## Endpoints

Project includes swagger dependencies which can be accessible from this url: 
[Swagger UI](http://localhost:8080/swagger-ui/index.html)

Also Postman collection created to test every endpoint:
[Postman Collection](https://www.postman.com/aerospace-technologist-66625246/workspace/brokerage-firm-apis-public/collection/14312751-e4b69827-0536-4ee8-8f79-598cd7918c26?action=share&creator=14312751)

## Security
Every endpoint requires basic http authentication.
Default username and password set as USER and 123456 in application.properties
Change in case of need

## Controllers
### AssetController:
**_NOTE:_** createAsset endpoint not included in the case study but implemented for test purposes.
#### Endpoints:
- listAssets Path:/asset/list HTTP Method: GET Params:
    - @RequestParam String customerId
  
- createAsset Path:/asset HTTP Method: POST Params:
    - @RequestParam String customerId
    - @RequestParam String assetName
    - @RequestParam BigDecimal size

### MoneyController: 
Controller that includes methods for only money asset for customer which is TRY as default
#### Endpoints:
- listAssets Path:/money/deposit HTTP Method: POST Params:
  - @RequestParam String customerId
  - @RequestParam BigDecimal size

- createAsset Path:/money/deposit HTTP Method: POST Params:
  - @RequestParam String customerId
  - @RequestParam BigDecimal size
  - @RequestParam String iban  **_NOTE:_** iban parameter not actively used only validated with regex

### OrderController:
Controller for create delete and list operations for order.
#### Endpoints:
- createOrder Path:/order HTTP Method: POST Params:
  - @RequestBody CreateOrderRequest createOrderRequest

- deleteOrder Path:/order HTTP Method: DELETE Params:
  - @RequestParam String orderId

- listOrders Path:/order HTTP Method: GET Params:
  - @RequestParam String customerId

### CustomerController:
Controller for creating customer. Login for customer not implemented.
This controller mostly used for creating a customer to use id in order and asset endpoints
#### Endpoints:
- signup Path:/customer/signup HTTP Method: POST, Params:
  - @RequestParam String username
  - @RequestParam String password