ALTER TABLE accounts ADD COLUMN deleted TIMESTAMP;
ALTER TABLE accounts ADD COLUMN deleted_by INT REFERENCES users(id);
