-- Create schema if not exists
CREATE SCHEMA IF NOT EXISTS finance;

-- Create table with JSONB column
CREATE TABLE IF NOT EXISTS finance.financedata (
    param VARCHAR(10) PRIMARY KEY,
    body  JSONB
);

-- Optional sample data
INSERT INTO finance.financedata (param, body) VALUES
('CODE123', '{"amount": 1200.5, "currency": "USD", "approved": true}'),
('TEST001', '{"message": "Hello, JSON!", "status": "ok"}');

