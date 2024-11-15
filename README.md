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
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(500),
    email VARCHAR(100),
    isactive INT DEFAULT 0,
    attempts INT DEFAULT 0
);

### Roles Table:
CREATE TABLE roles (
    role_id INT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

### Insert Default Roles:
INSERT INTO roles(role_name) VALUES('USER');
INSERT INTO roles(role_name) VALUES('ADMIN');

### User Roles Table:
CREATE TABLE user_roles (
    user_id INT,
    role_id INT,
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

### Start the Application
- Navigate to the target folder and run the JAR file using the following command:
java -jar <jar_filename>.jar


