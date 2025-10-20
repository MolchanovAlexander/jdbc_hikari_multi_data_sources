-- Create schema if not exists
CREATE SCHEMA IF NOT EXISTS finance;

-- Create table with JSONB column
CREATE TABLE IF NOT EXISTS finance.financedata (
    param VARCHAR(10) PRIMARY KEY,
    body  JSONB
);

-- Optional sample data
INSERT INTO finance.financedata (param, body) VALUES
('USD', '{"StartDate":"20.10.2025","CurrencyCode":"840","CurrencyCodeL":"USD","Amount":41.7308,"TimeSign":"mock_resp","Units":1}'),
('EUR', '{"StartDate":"20.10.2025","CurrencyCode":"944","CurrencyCodeL":"AZN","Amount":24.5519,"TimeSign":"mock_resp","Units":1}');