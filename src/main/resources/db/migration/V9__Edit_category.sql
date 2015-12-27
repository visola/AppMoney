ALTER TABLE categories ADD COLUMN created_by int references users(id);

CREATE TABLE categories_users (
  category_id INT REFERENCES categories(id),
  user_id INT REFERENCES users(id),
  hidden BOOLEAN,
  PRIMARY KEY (category_id, user_id)
);

INSERT INTO categories_users (SELECT c.id, u.id, false FROM categories c, users u);
