ALTER TABLE ownership ADD CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users (id) MATCH FULL;
ALTER TABLE ownership ADD CONSTRAINT account_fk FOREIGN KEY (account_id) REFERENCES accounts (id) MATCH FULL;
