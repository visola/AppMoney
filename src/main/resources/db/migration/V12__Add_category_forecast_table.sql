CREATE TABLE forecasts (
  id SERIAL NOT NULL PRIMARY KEY,
  start_day_of_month INT NOT NULL,
  created timestamp,
  created_by int references users(id),
  updated timestamp,
  updated_by int references users(id)
);

CREATE TABLE forecast_permissions (
  user_id INT NOT NULL,
  forecast_id INT NOT NULL,
  permission VARCHAR(30) NOT NULL,
  PRIMARY KEY (user_id, forecast_id, permission)
);

CREATE TABLE category_forecast_entries (
  id SERIAL NOT NULL PRIMARY KEY,
  forecast_id int references forecasts(id),
  title CHARACTER VARYING(256) NOT NULL,
  amount DECIMAL(19,4) NOT NULL,
  category_id INTEGER NOT NULL REFERENCES categories(id),
  created timestamp,
  created_by int references users(id),
  updated timestamp,
  updated_by int references users(id)
);
