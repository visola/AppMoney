CREATE TABLE owners
(
	id serial NOT NULL primary key,
	owner character varying(40)
);

CREATE TABLE users
(
	id serial NOT NULL primary key,
	"user" character varying(40)
);

CREATE TABLE accounts (
	id	int NOT NULL primary key,
	owner   int references owners(id),
	initial_balance numeric,
	initial_balance_date timestamp,
	balance numeric,
	created timestamp,
	create_by int references users(id),
	updated timestamp,
	updated_by int references users(id)
);