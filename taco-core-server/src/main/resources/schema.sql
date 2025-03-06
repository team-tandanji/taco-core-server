CREATE TABLE products
(
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     title VARCHAR(255),
     imagePath VARCHAR(255),
     tradeMethod VARCHAR(255),
     price BIGINT,
     priceOffer BOOLEAN,
     description TEXT,
     location VARCHAR(255)
);
