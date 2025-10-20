-- Create schema (database) if it doesn't exist
CREATE DATABASE IF NOT EXISTS finance;
USE finance;

-- Create table with native JSON type
CREATE TABLE IF NOT EXISTS financedata (
    param VARCHAR(10) PRIMARY KEY,
    body  JSON
);

-- Optional sample data
INSERT INTO financedata (param, body) VALUES
('USD', JSON_OBJECT(
    'StartDate', '20.10.2025',
    'CurrencyCode', '840',
    'CurrencyCodeL', 'USD',
    'Amount', 41.7308,
    'TimeSign', 'mock_resp',
    'Units', 1
)),
('EUR', JSON_OBJECT(
    'StartDate', '20.10.2025',
    'CurrencyCode', '944',
    'CurrencyCodeL', 'AZN',
    'Amount', 24.5519,
    'TimeSign', 'mock_resp',
    'Units', 1
));
