#Travel Agency System
This project is a travel agency system Defined by [sepehrNamdar](https://github.com/sepehrNamdar).
It aims to provide a comprehensive solution for clients to plan their trips, book reservations, manage their bookings, and perform currency conversions.
---

The Travel Agency System is a comprehensive Java-based 
application that enables clients to search for flights, 
book reservations, manage existing reservations, 
and perform currency conversions. 



**Features**
+ **Flight Plan Selection:** Search and select flight plans based on preferred departure and destination locations, dates, and times.

+ **Flight Selection:** Browse and choose flights within selected plans, filtering by departure time, airline, or price.

+ **Reservation Booking:** Book reservations by providing passenger details, contact information, and payment details.

+ **Reservation Search:** Search for existing reservations using criteria like reservation ID, passenger name, or flight details.

+ **Reservation Cancellation:** Cancel reservations by providing the necessary details.

+ **Currency Conversion:** Convert amounts to different currencies using up-to-date exchange rates.

## Technologies Used

The travel agency project utilizes the following technologies and libraries:

- **Java SE**: Core programming language for building the application.
- **JDBC**: Java Database Connectivity for connecting to the MySQL database and executing SQL queries.
- **MySQL**: Relational database management system for storing flight, reservation, and client information.
- **Java Swing**: GUI toolkit for creating the graphical user interface of the travel agency system.
- **JUnit**: Testing framework for unit testing the application's functionality.
- **AssertJ**: Fluent assertions library for writing concise and readable assertions in tests.
- **Google Gson**: Library for converting Java objects to JSON representation and vice versa.
- **Jakarta**: Framework for building web services in Java.
- **JAX-RS**: Java API for RESTful web services, used for developing the exchange rate web service.
- **Maven**: Build automation and dependency management tool.
- **JBoss/WildFly 27**: Application server for running the Exchange Rate Web Service.

These technologies and libraries work together to provide a robust and efficient travel agency system.

## Getting Started

To get started with the Travel Agency project, follow the steps below:

1. Clone the project repository to your local machine.  
`   git clone https://github.com/amir-original/Travel-Agency.git
`

2. Ensure you have Maven installed on your system. Maven is a build automation and dependency management tool.
   You can download it from the official website: [Apache Maven](https://maven.apache.org/download.cgi)

3. Open a terminal or command prompt and navigate to the project's root directory.

4. Build the project using Maven by running the following command:
`   mvn clean package
`

5. Maven will download all the project dependencies and build the application.

6. Once the build is successful, you can run the application by `App` Class in ui package


## Running the Exchange Rate Web Service Locally

To run the Exchange Rate Web Service locally using JBoss/WildFly 27, follow the steps below:

1. Download and install JBoss/WildFly 27 from the official website.

2. Start the JBoss/WildFly server by running the appropriate startup command for your operating system.

3. Deploy the Exchange Rate Web Service to the server. You can do this by copying the web service WAR file to the server's deployment directory or using the server's administration console.

4. Once the web service is deployed, it should be running and accessible at the specified endpoint. Make a note of the service URL for later use.

5. Ensure that the Exchange Rate Web Service is kept running while the Travel Agency application is being used. You may need to configure the server to run as a service or keep it running in the background.

By following these steps, the Exchange Rate Web Service will be available locally for the Travel Agency application to connect to and retrieve exchange rate information.

