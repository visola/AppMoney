CREATE TABLE transactions (
  id SERIAL NOT NULL PRIMARY KEY,
  "value" DECIMAL(19,4) NOT NULL,
  happened DATE NOT NULL,
  from_account_id INTEGER NOT NULL REFERENCES accounts(id),
  to_account_id INTEGER REFERENCES accounts(id),
  category_id INTEGER NOT NULL REFERENCES categories(id),
  created timestamp,
  created_by int references users(id),
  updated timestamp,
  updated_by int references users(id)
);
