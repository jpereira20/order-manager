# Order Manager

Este projeto é uma aplicação de gestão de encomendas construída com Java, Maven e PostgreSQL.

## Pré-requisitos

- Java JDK 8 ou superior  
- Maven  
- PostgreSQL  
- Servidor de aplicações (GlassFish, WildFly, etc.)  
- Postman (ou outra ferramenta para testar APIs)

## Configurar Ambiente

- Fazer o clone do projeto.
- Configure a base de dados PostgreSQL:
  - Criar a base de dados com o nome order_manager.
  - Definir as credenciais no ficheiro DAO.
- Executar mvn clean install.

##  Endpoints da API

###  UserController
- `POST /users` -> Criar utilizador

- `GET /users` -> Listar todos os utilizadores

- `GET /users/{id}` -> Procura por ID

- `GET /users/by-name/{name}` -> Procura pelo nome

- `GET /users/by-email/{email}` -> Procura pelo email

- `PUT /users/{id}` -> Atualiza utilizadores

- `DELETE /users/{id} ` -> Apagar utilizadores

---

###  ItemController
- `POST /items` -> Criar items

- `GET /items` -> Listar todos os items

- `GET /items/{id}` -> Procura por ID

- `GET /items/by-itemName/{itemName}` -> Procura pelo nome do item

- `PUT /items/{id}` -> Atualiza items

- `DELETE /items/{id} ` -> Apagar items

---

###  OrderController
- `POST /orders ` -> Criar pedido

- `GET /orders` -> Listar todos os pedidos

- `GET /orders/{id}` -> Procura por ID

- `GET /orders/by-user/{userId}` -> Procurar pedidos pelo ID do user

- `GET /orders/by-item/{itemId}` -> Procurar pedidos pelo ID do item

- `GET /orders/by-creationDate/{creationDate}` -> Procurar pedidos por data de criação (formato dd-MM-yyyy)

- `GET /orders/by-quantity/{quantity}` -> Procurar pedidos por quantidade

- `PUT /orders/{id}` -> Atualiza pedidos

- `DELETE /orders/{id}` -> Apagar pedidos

---

###  StockMovementController
- `POST /stock` -> Criar pedido

- `GET /stock` -> Listar todos os pedidos

- `GET /stock /{id}` -> Procura por ID

- `GET /stock/by-item/{itemId}` -> Procurar pedidos pelo ID do user

- `GET /orders/by-item/{itemId}` -> Procurar movimentos de stock por ID do item

- `GET /stock/by-creationDate/{creationDate}` -> Procurar movimentos de stock (formato dd-MM-yyyy)

- `GET /stock/by-quantity/{quantity}` -> Procurar movimentos de stock por quantidade

- `PUT /orders/{id}` -> Atualiza movimentos de stock

- `DELETE /orders/{id}` -> Apagar movimentos de stock

---

