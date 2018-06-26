# demo-crud-poc

This repository is a sample CRUD for assignment purposes.

## Technology Stack
   - SpringBoot
   - H2 Database (Console Enabled)
   - JPA With Hibernate (Hikari Connection Pool)
   - Java 8
   - Spring Rest
   - Swagger API for api defination
   - Swagger UI to test API in browser
   
   [![swagger.png](https://s33.postimg.cc/w088shvtr/swagger.png)](https://postimg.cc/image/d7wdowzff/)  
   
 ## API
   - All the API Docs are available here http://localhost:9090/v2/api-docs  
   
 
 ## Testing
   - JUnit
   - Mockito for mocking
   - RestAssured for testing and validating Rest Controller
   
 ## Testing Coverage
   - Jacoco to get coverage
   - Overall coverage > 99%
   
   [![coverage.png](https://s33.postimg.cc/5um1mu127/coverage.png)](https://postimg.cc/image/mv4xvie3f/)
   
 
 ## How to check out and run
 
  - git clone https://github.com/ajayoemail/demo-crud-poc.git
  - mvn clean install (This will run all the tests)
  - mvn spring-boot:run (This will start up the springBoot)
  - It can be run after building it (java -jar target/poc-0.0.1-SNAPSHOT.jar)
  - access via http://localhost:9090/
  
 ## H2 console
  - When the SpringBoot starts up you can access H2 console
  - Use JDBC URL : jdbc:h2:mem:demo_employee_db
  
  [![H2_Console.png](https://s33.postimg.cc/hjq1avcm7/H2_Console.png)](https://postimg.cc/image/y7hjdd7dn/)
  
 ## Swagger UI
  - Swagger UI is enabled and can be accessed http://localhost:9090/swagger-ui.html
  
 ## Docker
   - Its Docker Enabled and can be built image by running command 
   - mvn package docker:build -DskipTests
 
  
 
 
    
   
