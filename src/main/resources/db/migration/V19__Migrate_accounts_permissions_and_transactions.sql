-- Rename Accounts
ALTER TABLE accounts RENAME TO account;

-- Rename Account columns
ALTER TABLE account RENAME COLUMN created_by TO created_by_id;
ALTER TABLE account RENAME COLUMN updated_by TO updated_by_id;
ALTER TABLE account RENAME COLUMN deleted_by TO deleted_by_id;

-- Create new permission table
CREATE TABLE user_account_permission (
  id serial not null primary key,
  user_id INT NOT NULL,
  account_id INT NOT NULL,
  permission VARCHAR(30) NOT NULL
);

-- Move permission data
INSERT INTO user_account_permission (user_id, account_id, permission)
  SELECT user_id, account_id, permission
  FROM permissions;

-- Drop old permissions table
DROP TABLE permissions;

-- Rename Transactions table
ALTER TABLE transactions RENAME TO transaction;

-- Update transaction column types
ALTER TABLE transaction ALTER COLUMN happened TYPE TIMESTAMP;

-- Rename Transaction columns
ALTER TABLE transaction RENAME COLUMN created_by TO created_by_id;
ALTER TABLE transaction RENAME COLUMN updated_by TO updated_by_id;
ALTER TABLE transaction RENAME COLUMN deleted_by TO deleted_by_id;
