DROP TABLE IF EXISTS thetheatreroyal.customer;
CREATE TABLE thetheatreroyal.customer(
CustomerID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(20),
email VARCHAR(50),
password VARCHAR(50),
address VARCHAR(50),
card VARCHAR(50),
isEmployee boolean
)
AUTO_INCREMENT = 10050;

Insert INTO customer (name, email, password, isEmployee)
VALUES ('nam', 'admin', 'admin', true);