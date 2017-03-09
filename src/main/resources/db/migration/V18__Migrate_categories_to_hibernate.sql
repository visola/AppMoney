-- Rename it to the default JPA name
ALTER TABLE categories RENAME TO category;

-- Drop categories_users since we won't use it anymore
DROP TABLE categories_users;

-- Add the owner id column, nullable for now
ALTER TABLE category RENAME COLUMN created_by TO owner_id;

-- Duplicate the categories that the users used in the past
INSERT INTO category (name, parent_id, owner_id)
  SELECT DISTINCT name, parent_id, u.id as owner_id
  FROM category c
  JOIN users u ON 1 = 1
  WHERE c.owner_id IS NULL
  AND c.id IN (
    SELECT t.category_id
    FROM transactions t
    WHERE t.created_by = u.id
  );

-- Duplicate parent categories for the newly created categories
INSERT INTO category (name, parent_id, owner_id)
  SELECT DISTINCT parent.name, parent.parent_id, u.id as owner_id
  FROM category parent
  JOIN users u ON 1 = 1
  JOIN category c ON parent.id = c.parent_id AND c.owner_id = u.id
  WHERE parent.owner_id IS NULL
  AND parent.name NOT IN (SELECT name FROM category WHERE owner_id = u.id);

-- Migrate parent categories
UPDATE category AS c
  SET parent_id = new_parent.id
  FROM category AS new_parent
  JOIN category AS old_parent
    ON new_parent.name = old_parent.name
  WHERE c.parent_id = old_parent.id
  AND new_parent.owner_id = c.owner_id;

-- Migrate all categories for transactions
UPDATE transactions AS t
  SET category_id = new.id
  FROM category AS new
  WHERE new.owner_id = t.created_by
  AND new.name = (
    SELECT old.name
    FROM category AS old
    WHERE old.id = t.category_id
  );

-- Update all forecast entries
UPDATE forecast_entry AS f
  SET category_id = new.id
  FROM category AS new
  WHERE new.owner_id = f.created_by
  AND new.name = (
    SELECT old.name
    FROM category AS old
    WHERE old.id = f.category_id
  );

-- Remove default categories with parents first
DELETE FROM category
WHERE owner_Id IS NULL
  AND parent_id IN (SELECT id FROM category WHERE owner_id IS NULL);

-- Remove default categories
DELETE FROM category WHERE owner_Id IS NULL;

-- Make owner_id not nullable
ALTER TABLE category ALTER COLUMN owner_id SET NOT NULL;

-- Rename sharing table
ALTER TABLE shared_categories RENAME TO friendship;
