USE testdb;

CREATE TABLE IF NOT EXISTS financedata (
    param VARCHAR(10) PRIMARY KEY,
    body JSON
);

INSERT INTO financedata (param, body) VALUES
('MDL', JSON_OBJECT(
    'StartDate', '20.10.2025',
    'CurrencyCode', '498',
    'CurrencyCodeL', 'MDL',
    'Amount', 2.4748,
    'TimeSign', 'mock_resp_msql',
    'Units', 1
)),
('AZN', JSON_OBJECT(
    'StartDate', '20.10.2025',
    'CurrencyCode', '944',
    'CurrencyCodeL', 'AZN',
    'Amount', 24.5519,
    'TimeSign', 'mock_resp_msql',
    'Units', 1
));
