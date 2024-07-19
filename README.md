# FileManager

FileManager is a Spring Boot application designed to process text files and convert them to JSON format. It validates input files, processes the content, and returns the outcome in a structured JSON format. The application also features IP validation using an external API and logs requests to an H2 database.

## Features

- **File Processing**: Converts text files to JSON format.
- **Validation**: Validates the structure and content of input files.
- **IP Validation**: Ensures requests come from whitelisted IP addresses.
- **Logging**: Logs request details to an H2 database.
- **REST API**: Exposes an endpoint for file processing via HTTP POST.

## Technologies Used

- **Java 17**
- **Spring Boot 3.3.1**
- **Spring Data JPA**
- **H2 Database**
- **JUnit 5 and JUnit 4 for testing**
- **WireMock for mocking external API calls**
- **Lombok for boilerplate code reduction**
- **Gradle for project management and build automation**

## Installation and Setup

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/manav993/filemanager.git
   cd filemanager
   ```

2. **Build the Project**:
   ```bash
   ./gradlew build
   ```

3. **Run the Application**:
   ```bash
   ./gradlew bootRun
   ```

4. **Access the Application**:
   The application will be accessible at `http://localhost:8080`.

## Usage

### API Endpoint

- **Endpoint**: `/api/files/process`
- **Method**: `POST`
- **Description**: Processes an uploaded text file and returns the result in JSON format.
- **Request Parameters**:
    - `file`: The text file to be processed.
    - `skipValidation`: Boolean flag to skip IP validation.

### Example Request

```bash
curl -F "file=@EntryFile.txt" -F "skipValidation=false" http://localhost:8080/api/files/process
```

### Example Response

```json
[
  {
    "name": "John Smith",
    "transport": "Rides A Bike",
    "topSpeed": "12.1"
  },
  {
    "name": "Mike Smith",
    "transport": "Drives an SUV",
    "topSpeed": "95.5"
  },
  {
    "name": "Jenny Walters",
    "transport": "Rides A Scooter",
    "topSpeed": "15.3"
  }
]
```

## Testing

### Running Unit Tests

The project includes unit tests for the service and controller layers using JUnit and WireMock.

```bash
./gradlew test
```

### Test Summary

A summary of test results will be displayed in the terminal, detailing the status of each test.

## Logging

Request details are logged into an H2 database. You can access the H2 console at `http://localhost:8080/h2-console` using the following credentials:

- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: `password`

## Contact

If you have any questions or need further assistance, please feel free to contact me at manav993@example.com.
```
