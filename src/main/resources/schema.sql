DROP TABLE IF EXISTS product;

CREATE TABLE product
(
    id          int AUTO_INCREMENT PRIMARY KEY,
    name        varchar(255)   NOT NULL,
    description varchar(255),
    price       decimal(10, 2) NOT NULL,
    category    varchar(100)   NOT NULL
);