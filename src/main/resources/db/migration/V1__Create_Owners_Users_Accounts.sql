CREATE TABLE users (
  id serial not null primary key,
  email character varying(256)
);

CREATE TABLE roles (
  user_id int references users(id),
  role character varying(50),
  primary key (user_id, role)
);

CREATE TABLE accounts (
  id serial not null primary key,
  owner int references users(id),
  initial_balance decimal(19,4),
  initial_balance_date timestamp,
  balance decimal(19,4),
  created timestamp,
  created_by int references users(id),
  updated timestamp,
  updated_by int references users(id),
  "type" character varying(15)
);

insert into users (email) values ('fernando.gandini@gmail.com');
insert into roles (user_id, role) values ((select id from users where email = 'fernando.gandini@gmail.com'), 'ADMIN');
insert into roles (user_id, role) values ((select id from users where email = 'fernando.gandini@gmail.com'), 'USER');

insert into users (email) values ('viniciusisola@gmail.com');
insert into roles (user_id, role) values ((select id from users where email = 'viniciusisola@gmail.com'), 'ADMIN');
insert into roles (user_id, role) values ((select id from users where email = 'viniciusisola@gmail.com'), 'USER');
