CREATE TABLE IF NOT EXISTS transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id VARCHAR(50),
    amount DOUBLE,
    date DATE
);