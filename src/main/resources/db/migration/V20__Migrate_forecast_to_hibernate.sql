-- Rename Forecasts to match default table name 
ALTER TABLE forecasts RENAME TO forecast;

-- Rename Forecast column names to match default
ALTER TABLE forecast RENAME COLUMN created_by TO created_by_id;
ALTER TABLE forecast RENAME COLUMN updated_by TO updated_by_id;

-- Create forecast permissions table
CREATE TABLE forecast_permission (
  id serial not null primary key,
  user_id INT NOT NULL,
  forecast_id INT NOT NULL,
  permission VARCHAR(30) NOT NULL
);

-- Migrate data from old permissions table
INSERT INTO forecast_permission (user_id, forecast_id, permission)
  SELECT user_id, forecast_id, permission
  FROM forecast_permissions;

-- Drop old permissions table
DROP TABLE forecast_permissions;

-- Rename Forecast Entry columns names to match default
ALTER TABLE forecast_entry RENAME COLUMN created_by TO created_by_id;
ALTER TABLE forecast_entry RENAME COLUMN updated_by TO updated_by_id;
