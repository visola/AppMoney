ALTER TABLE transactions
  ADD COLUMN forecast_entry_id INTEGER REFERENCES forecast_entry(id);
