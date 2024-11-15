# Book Exchange Project

## Database Setup

### 1. Using MySQL Database
- Make necessary changes in the `application.yml` file for MySQL configuration.

### 2. Run the Following SQL Scripts

**Create and Use the Database:**

CREATE DATABASE book_exchange_db;
USE book_exchange_db;

### Users Table:
```sql
CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(500) NOT NULL,
    isactive BIGINT NOT NULL,
    attempts BIGINT NOT NULL,
    phone VARCHAR(10) NOT NULL,
    CONSTRAINT chk_phone_format CHECK (phone REGEXP '^[0-9]{1,10}$')
);

### Roles Table:
CREATE TABLE roles (
    role_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

### Insert Default Roles:
INSERT INTO roles(role_name) VALUES('USER');
INSERT INTO roles(role_name) VALUES('ADMIN');

### User Roles Table:
CREATE TABLE user_roles (
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES roles(role_id) 
    ON DELETE CASCADE ON UPDATE CASCADE
);


### Books Table:
CREATE TABLE books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    genre VARCHAR(100),
    book_condition VARCHAR(50),
    location VARCHAR(100),
    is_available BOOLEAN DEFAULT TRUE,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(user_id) 
    ON DELETE CASCADE
);



### Notes:
- Ensure your MySQL server is running before executing these scripts.
- Modify the `application.yml` for MySQL configuration details like username, password, and URL based on your local setup.


### Build and Compile the Project
- To build and compile the project, run the following command:
    mvn clean install
 ### Output 

<pre style="color: green;">
[INFO] Reactor Summary for Book Exchange Platform 0.0.1-SNAPSHOT:
[INFO]
[INFO] Book Exchange Platform ............................. SUCCESS [  3.711 s]
[INFO] API Gateway ........................................ SUCCESS [ 10.627 s]
[INFO] Eureka Service Discovery for Book Exchange Platform  SUCCESS [  4.093 s]
[INFO] User Management .................................... SUCCESS [  8.107 s]
[INFO] Book Management .................................... SUCCESS [ 30.932 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  58.592 s
[INFO] Finished at: 2024-11-15T10:54:15+05:30
[INFO] ------------------------------------------------------------------------
</pre>


### Start the Application
- Navigate to the target folder and run the JAR file using the following command:
java -jar <jar_filename>.jar


