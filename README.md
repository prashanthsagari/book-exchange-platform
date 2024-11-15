I am using MySQL database -- feel free to modify mysql related changes in application.yml file
*************************
Run below scripts:
**create database book_exchange_db;**
**use book_exchange_db;**

Create TABLES:
**************
create table users (
 user_id INT primary key AUTO_INCREMENT, 
 username varchar(50) NOT NULL UNIQUE, 
 password varchar(500), 
 email varchar(100), 
 isactive INT default 0, 
 attempts INT default 0
);

create table roles (
role_id int primary key auto_increment, 
role_name varchar(50) NOT NULL UNIQUE
);

INSERT INTO roles(role_name) VALUES('USER');
INSERT INTO roles(role_name) VALUES('ADMIN');


create table user_roles (
 user_id int, 
 role_id int, 
 PRIMARY KEY (user_id, role_id),
 CONSTRAINT fk_user_id FOREIGN KEY (user_id) references users(user_id), 
 CONSTRAINT fk_role_id FOREIGN KEY (role_id) references roles(role_id) ON DELETE cascade on UPDATE cascade
);

CREATE TABLE books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    genre VARCHAR(100),
    book_condition VARCHAR(50),
    location VARCHAR(100),
    is_available BOOLEAN DEFAULT TRUE,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


Build and Compile:
mvn clean install

To Start:  Open command prompt from target folder then run below java command
java -jar <jar_filename>   
