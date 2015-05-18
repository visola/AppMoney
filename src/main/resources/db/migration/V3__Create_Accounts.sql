CREATE TABLE accounts (
	id	int,
	owner   int references owners(id),
	initial_balance   money,
	initial_balance_date timestamp,
	balance money,
	created timestamp,
	create_by int references users(id),
	updated timestamp,
	updated_by int
)