# Kitchensink
This projects aims to converts legacy jboss application to Spring boot 3.3 project using Mongodb.

### Overview of technologies used
* Spring boot 3.3
* JDK 21
* Mongodb
* Event Listener & Event Producer
* Spring Converter
* MongoDb Connection Pooling
* Junit Test Cases
* Rest Controller
* Reduced member object fetch network call by maintaining in-memory data
* Custom Exception Handling
* JPA repository
* Logging Framework 
* Lombok
* Eureka Client Discovery
* Async Logger and Rollover logging file handling


### Prerequisites
* JDK21 (Java Development Kit) installed on your machine.
* Text Editor or IDE (like IntelliJ IDEA, Eclipse, or VS Code).
* Git tool installed
* Maven 3.6.3 installed
* MongoDb

### Installing
* Install java 21 and set up JAVA_HOME variable
* To set JAVA_HOME run command 
  * export $JAVA_HOME={path where you kept jdk}/Contents/Home
* Install Maven 3.6.3 and Set MAVEN_HOME 
  * export $MAVEN_HOME={path where you kept maven}

### Run
* Create a directory using command: mkdir directoryName
* cd directoryName
* Run https://github.com/nehamittal01/kitchensink.git
* cd kitchensink
* set command line argument in ide -Dlog_path={PROJECT_PATH} (optional)
* mvn spring-boot:run OR run command : java -Dlog_path={PROJECT_PATH} -jar target/kitchensink-0.0.1-SNAPSHOT.jar




