CREATE TABLE transactions (
  id SERIAL NOT NULL PRIMARY KEY,
  title CHARACTER VARYING(256) NOT NULL,
  "value" DECIMAL(19,4) NOT NULL,
  happened DATE NOT NULL,
  from_account_id INTEGER REFERENCES accounts(id),
  to_account_id INTEGER NOT NULL REFERENCES accounts(id),
  category_id INTEGER NOT NULL REFERENCES categories(id),
  created timestamp,
  created_by int references users(id),
  updated timestamp,
  updated_by int references users(id)
);
