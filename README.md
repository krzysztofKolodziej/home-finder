# HomeFinder

## Project Overview
HomeFinder is a RESTful web application built with Java 17 and Spring Boot for searching, browsing, and managing real estate offers. The platform provides comprehensive property management features along with credit worthiness calculations and estate agent management.

## Key Features

**Property Management**
- Advanced property search with multiple filtering criteria
- Multi-currency support with automatic conversion
- Complete CRUD operations for property offers
- Detailed property information including technical specifications

**Credit Calculator**
- Real-time credit worthiness assessment
- Multiple income source analysis
- Calculation history tracking
- Personalized credit recommendations

**Estate Agent System**
- Agent registration and management
- Property assignment to specific agents
- Contact and communication system

## Tech/Framework Used
- **Java 17**
- **Spring Boot 3.2.5**
  - Spring Web
  - Spring Data JPA
  - Spring Validation
  - Spring Test
- **Hibernate**
- **Lombok**
- **Database:** MySQL (production), H2 (testing)
- **Testing:** JUnit, Mockito
- **Documentation:** Swagger
- **Build Tool:** Maven 

## Installation and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL database

### Database Configuration
1. Copy `.env.example` to `.env`
2. Update database credentials in `.env` file:
   ```
   DB_USERNAME=your_username
   DB_PASSWORD=your_password
   ```

### Build and Run
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd homeFinder
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

By default, the application will start on port 8080.

## API Endpoints

### Property Offers

**GET /offers/{currency}/all**
Retrieve all property offers with filtering options.

Query parameters:
- `minPrice`, `maxPrice` - Price range filter
- `city` - City filter
- `kindOfProperty` - Property type filter
- Additional filtering parameters available

Example:
```bash
curl "http://localhost:8080/offers/USD/all?city=Warsaw&minPrice=100000&maxPrice=500000"
```

**GET /offers/{id}**
Retrieve detailed information about a specific offer.

**POST /add**
Add a new property offer.

Request body example:
```json
{
  "title": "Beautiful Apartment in City Center",
  "description": "Modern 3-bedroom apartment",
  "price": 350000,
  "city": "Warsaw",
  "kindOfProperty": "APARTMENT",
  "offerDetails": {
    "area": 85.5,
    "rooms": 3,
    "buildingType": "APARTMENT_BUILDING",
    "heating": "CENTRAL_HEATING"
  }
}
```

**PUT /offers/{id}**
Update an existing property offer.

**DELETE /offers/{id}**
Remove a property offer.

### Credit Calculator

**POST /creditCalculator**
Calculate credit worthiness based on provided financial information.

Request body example:
```json
{
  "monthlyIncome": 5000,
  "monthlyExpenses": 2000,
  "existingCredits": 500,
  "sourceOfIncome": "EMPLOYMENT",
  "creditAmount": 200000,
  "creditPeriod": 25
}
```

Response: Credit worthiness score (integer)

### Estate Agents

**GET /agents**
Retrieve all registered estate agents.

**POST /agents**
Register a new estate agent.

Request body example:
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "+1234567890",
  "agency": "Premium Real Estate"
}
```

## Error Handling

The application provides comprehensive error handling with detailed error messages using the `ErrorRespond` record:

```json
{
  "status": "BAD_REQUEST",
  "message": "Validation failed for property offer"
}
```

Common error responses:
- **400 Bad Request** - Invalid input data or JSON format
- **404 Not Found** - Resource not found
- **500 Internal Server Error** - Server-side errors

## Testing

Run tests:
```bash
mvn test
```

The project includes:
- Unit tests for services and mappers
- Integration tests for controllers
- Validation tests for request/response handling
- Repository tests with H2 in-memory database

