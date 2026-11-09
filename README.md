# Order Manager

REST API to manage items, stock and orders. Orders are fulfilled automatically as stock allows it. (FIFO principle used)

- Creating an order consumes available stock (oldest stock first).
- Creating a stock movement serves open orders (oldest order first).
- Each match is stored as an allocation, so you can trace order -> stock movements and stock movement -> orders.

**Stack**: Java 21, Spring Boot 4, Spring Data JPA, H2 (in-memory), Logback.

## Run
```bash
./mvnw spring-boot:run        # Windows: mvnw.cmd spring-boot:run
./mvnw test                   # run the tests
```

The API runs on http://localhost:8080. Data is in memory and is lost on restart.

Easiest way to try it: open http://localhost:8080/swagger-ui.html and call the routes from the browser.

## Routes
# API Resources & Routes
| Resource | Routes |
| :--- | :--- |
| **Customers** | `POST/GET /customers`, `GET/PUT/DELETE /customers/{id}` |
| **Items** | `POST/GET /items`, `GET/PUT/DELETE /items/{id}` |
| **Orders** | `POST/GET /orders`, `GET/PUT/DELETE /orders/{id}` |
| **Stock** | `POST/GET /stocks`, `GET/PUT/DELETE /stocks/{id}` |

- `GET /orders/{id}`shows **fulfilledQuantity**, **completion** (%), **status (OPEN/COMPLETE)** and the stock movements used.
- `GET /stocks/{id}` shows **remainingQuantity** and the orders it served.
- `PUT` on orders and stock only changes the quantity.


## Log file

Business events are written to `logs/orders.log`: orders created and completed, stock movements, allocations, emails sent and errors.

## Notes

- **Email is simulated**: it is only written to the log, behind a **NotificationService** interface. A failed notification is logged and does not undo the allocation.
- **Concurrency**: every operation on an item's orders or stock takes a pessimistic lock on the Item row, so the same stock is never allocated twice.
  - Locks the item row so allocations for the same item run one at a time (prevents allocating the same stock twice).
- Orders and stock that already have allocations cannot be changed or deleted.