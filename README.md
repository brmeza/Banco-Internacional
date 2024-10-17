
# Banco Internacional

## frond-end.. in progress...
This project simulates the backend of a banking system using **Spring Boot**. The application allows basic operations for managing users, accounts, and transactions.

## Features

- **Spring Boot** for the backend.
- **PostgreSQL** as the database.
- **JPA/Hibernate** for ORM management.
- **Swagger** for API documentation.
- CRUD operations for **users, accounts, and transactions**.

## Prerequisites

Ensure you have the following installed:

- **Java 11** or higher.
- **Maven** for dependency management.
- **PostgreSQL** configured and running.
- An API client such as **Postman** to test the endpoints.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/brmeza/Banco-Internacional.git
   cd Banco-Internacional
   ```

2. Configure the **PostgreSQL** connection:  
   Update the `application.properties` file in `src/main/resources/` with your database credentials:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/banco
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. Build the project:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## Usage

Once the application is running, access the API documentation at:  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Main Endpoints

1. **Users**:
   - **GET /api/users**: List all users.
   - **POST /api/users**: Create a new user.
   - **PUT /api/users/{id}**: Update a user’s information.
   - **DELETE /api/users/{id}**: Delete a user.

2. **Accounts**:
   - **GET /api/accounts**: List all accounts.
   - **POST /api/accounts**: Create a new bank account.
   - **PUT /api/accounts/{id}**: Update an account.
   - **DELETE /api/accounts/{id}**: Delete an account.

3. **Transactions**:
   - **GET /api/transactions**: List all transactions.
   - **POST /api/transactions**: Record a new transaction.
   - **GET /api/transactions/{id}**: View a specific transaction.

## Data Model

- **User**: ID, name, email, and associated accounts.
- **Account**: ID, account number, type, balance, and associated user.
- **Transaction**: ID, type (deposit, withdrawal), amount, origin, and destination accounts.

## Testing

You can use **Postman** or any other HTTP client to test the endpoints.

### Example: Create a User  
```json
POST /api/users
{
  "name": "John Doe",
  "email": "john@example.com"
}
```

### Example: Create a Bank Account  
```json
POST /api/accounts
{
  "accountNumber": "1234567890",
  "type": "Savings",
  "balance": 10000.50,
  "userId": 1
}
```

### Example: Record a Transaction  
```json
POST /api/transactions
{
  "type": "Deposit",
  "amount": 5000.00,
  "originAccountId": 1,
  "destinationAccountId": null
}
```

```json
POST /api/transactions
{
  "type": "Transfer",
  "amount": 2000.00,
  "originAccountId": 1,
  "destinationAccountId": 2
}
```

### Example: Get All Transactions  
```bash
GET /api/transactions
```

## Contributions

Contributions are welcome. If you would like to collaborate, open an *issue* or submit a *pull request* with your changes.
