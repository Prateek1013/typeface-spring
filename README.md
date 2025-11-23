# Typeface Dropbox Backend

A Spring Boot application providing file storage and management APIs with JWT authentication.

## Prerequisites

- Java 17 or higher
- Docker
- Maven

## Database Setup

### Start PostgreSQL using Docker

Run the following command to start a PostgreSQL container:

```bash
docker run --name typeface-postgres \
  -e POSTGRES_DB=typeface \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  -d postgres:15
```

This will:
- Create a container named `typeface-postgres`
- Set up a database named `typeface`
- Use username `postgres` and password `postgres`
- Map port 5432 to your local machine

### Verify Database is Running

```bash
docker ps
```

You should see the `typeface-postgres` container running.

### Stop the Database

```bash
docker stop typeface-postgres
```

### Start the Database Again

```bash
docker start typeface-postgres
```

### Remove the Database Container

```bash
docker stop typeface-postgres
docker rm typeface-postgres
```

## Application Setup

### Configure Application Properties

The application is pre-configured to connect to the PostgreSQL database. Default settings in `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/typeface
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### Run the Application

```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`.

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and receive JWT token

### File Management (Requires Authentication)
- `POST /api/files/upload` - Upload a file
- `GET /api/files` - List all user files
- `GET /api/files/{id}` - Download a file
- `DELETE /api/files/{id}` - Delete a file

## Testing the API

### Register a User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"password123"}'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password123"}'
```

### Upload a File
```bash
curl -X POST http://localhost:8080/api/files/upload \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -F "file=@/path/to/your/file.txt"
```

### List Files
```bash
curl http://localhost:8080/api/files \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Technologies Used

- Spring Boot 3.x
- Spring Security with JWT
- PostgreSQL
- JPA/Hibernate
- Maven
