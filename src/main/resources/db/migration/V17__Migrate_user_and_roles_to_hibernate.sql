-- Create authority table
CREATE TABLE authority (
  id SERIAL NOT NULL PRIMARY KEY,
  authority VARCHAR(100) NOT NULL
);

-- Add missing columns in users table
ALTER TABLE users ADD COLUMN disabled TIMESTAMP;
ALTER TABLE users ADD COLUMN expires_on TIMESTAMP;
ALTER TABLE users ADD COLUMN locked_on TIMESTAMP;
ALTER TABLE users ADD COLUMN password_expired TIMESTAMP;
ALTER TABLE users ADD COLUMN password VARCHAR(1024) NOT NULL DEFAULT '';
ALTER TABLE users RENAME COLUMN email TO username;

-- User Authorities table
CREATE TABLE users_authorities (
  authorities_id INTEGER REFERENCES authority(id),
  user_id INTEGER REFERENCES users (id)
);

-- Create authorities based on existing roles
INSERT INTO authority (authority) SELECT DISTINCT role FROM roles;

-- Associate authorities with users
INSERT INTO users_authorities (authorities_id, user_id)
  SELECT DISTINCT a.id, r.user_id
  FROM roles r
  INNER JOIN authority a ON a.authority = r.role;

DROP TABLE roles;
