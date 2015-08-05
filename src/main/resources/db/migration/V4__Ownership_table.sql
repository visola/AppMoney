CREATE TABLE ownership (
  user_id INT NOT NULL,
  account_id INT NOT NULL,
  permission VARCHAR(30) NOT NULL,
  PRIMARY KEY (user_id, account_id, permission)
);

INSERT INTO ownership (user_id, account_id, permission) SELECT owner, id, 'READ' FROM accounts;
INSERT INTO ownership (user_id, account_id, permission) SELECT owner, id, 'WRITE' FROM accounts;
INSERT INTO ownership (user_id, account_id, permission) SELECT owner, id, 'OWNER' FROM accounts;

ALTER TABLE accounts DROP COLUMN owner;
