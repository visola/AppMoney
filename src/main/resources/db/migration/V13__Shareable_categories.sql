CREATE TABLE shared_categories (
  owner_id INT REFERENCES users(id),
  friend_id  INT REFERENCES users(id),
  PRIMARY KEY (owner_id, friend_id)
);
