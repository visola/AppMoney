INSERT INTO categories (name, parent_id) VALUES ('Carro', (SELECT id FROM categories WHERE name = 'Gastos Essenciais'));
