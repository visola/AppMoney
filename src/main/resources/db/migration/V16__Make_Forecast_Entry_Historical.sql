CREATE TABLE monthly_forecast_entry_amount (
  id SERIAL NOT NULL PRIMARY KEY,
  forecast_entry_id int references forecast_entry(id),
  amount DECIMAL(19,4) NOT NULL,
  month INT NOT NULL,
  year INT NOT NULL
);

INSERT INTO monthly_forecast_entry_amount (forecast_entry_id, amount, month, year)
  SELECT id, amount, 1, 2017 FROM forecast_entry;

ALTER TABLE forecast_entry DROP COLUMN amount;
