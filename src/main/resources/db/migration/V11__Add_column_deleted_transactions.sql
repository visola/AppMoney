ALTER TABLE transactions ADD COLUMN deleted TIMESTAMP;
ALTER TABLE transactions ADD COLUMN deleted_by INT REFERENCES users(id);
