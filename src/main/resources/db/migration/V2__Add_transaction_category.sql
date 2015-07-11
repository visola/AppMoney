CREATE TABLE categories (
  id SERIAL NOT NULL PRIMARY KEY,
  name CHARACTER VARYING(250),
  parent_id INTEGER REFERENCES categories (ID)
);

INSERT INTO categories (name) VALUES ('Renda');
INSERT INTO categories (name, parent_id) VALUES ('Remuneração', (SELECT id FROM categories WHERE name = 'Renda'));
INSERT INTO categories (name, parent_id) VALUES ('Bônus', (SELECT id FROM categories WHERE name = 'Renda'));
INSERT INTO categories (name, parent_id) VALUES ('Rendimento', (SELECT id FROM categories WHERE name = 'Renda'));

INSERT INTO categories (name) VALUES ('Gastos Essenciais');
INSERT INTO categories (name, parent_id) VALUES ('Moradia', (SELECT id FROM categories WHERE name = 'Gastos Essenciais'));
INSERT INTO categories (name, parent_id) VALUES ('Contas residenciais', (SELECT id FROM categories WHERE name = 'Gastos Essenciais'));
INSERT INTO categories (name, parent_id) VALUES ('Saúde', (SELECT id FROM categories WHERE name = 'Gastos Essenciais'));
INSERT INTO categories (name, parent_id) VALUES ('Educação', (SELECT id FROM categories WHERE name = 'Gastos Essenciais'));
INSERT INTO categories (name, parent_id) VALUES ('Transporte', (SELECT id FROM categories WHERE name = 'Gastos Essenciais'));
INSERT INTO categories (name, parent_id) VALUES ('Mercado', (SELECT id FROM categories WHERE name = 'Gastos Essenciais'));
INSERT INTO categories (name, parent_id) VALUES ('Condominio', (SELECT id FROM categories WHERE name = 'Gastos Essenciais'));

INSERT INTO categories (name) VALUES ('Estilo de Vida');
INSERT INTO categories (name, parent_id) VALUES ('Empregados domésticos', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parent_id) VALUES ('TV / Internet / Telefone', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parent_id) VALUES ('Taxas bancárias', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parent_id) VALUES ('Bares / Restaurantes', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parent_id) VALUES ('Lazer', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parent_id) VALUES ('Compras', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parent_id) VALUES ('Cuidados pessoais', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parent_id) VALUES ('Serviços', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parent_id) VALUES ('Viagem', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parent_id) VALUES ('Presentes / Doações', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parent_id) VALUES ('Família / Filhos', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parent_id) VALUES ('Despesas do trabalho', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parent_id) VALUES ('Outros gastos', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parent_id) VALUES ('Impostos', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));

INSERT INTO categories (name) VALUES ('Empréstimos');
INSERT INTO categories (name, parent_id) VALUES ('Juros de cartão',(SELECT id FROM categories WHERE name = 'Empréstimos'));
INSERT INTO categories (name, parent_id) VALUES ('Crediário',(SELECT id FROM categories WHERE name = 'Empréstimos'));
INSERT INTO categories (name, parent_id) VALUES ('Cheque especial',(SELECT id FROM categories WHERE name = 'Empréstimos'));
INSERT INTO categories (name, parent_id) VALUES ('Crédito consignado',(SELECT id FROM categories WHERE name = 'Empréstimos'));
INSERT INTO categories (name, parent_id) VALUES ('Carnê',(SELECT id FROM categories WHERE name = 'Empréstimos'));
INSERT INTO categories (name, parent_id) VALUES ('Outros empréstimos',(SELECT id FROM categories WHERE name = 'Empréstimos'));
INSERT INTO categories (name, parent_id) VALUES ('Juros',(SELECT id FROM categories WHERE name = 'Empréstimos'));

INSERT INTO categories (name) VALUES ('Lançamentos entre contas');
INSERT INTO categories (name, parent_id) VALUES ('Pagamento de cartão',(SELECT id FROM categories WHERE name = 'Lançamentos entre contas'));
INSERT INTO categories (name, parent_id) VALUES ('Resgate',(SELECT id FROM categories WHERE name = 'Lançamentos entre contas'));
INSERT INTO categories (name, parent_id) VALUES ('Aplicação',(SELECT id FROM categories WHERE name = 'Lançamentos entre contas'));
INSERT INTO categories (name, parent_id) VALUES ('Transferência',(SELECT id FROM categories WHERE name = 'Lançamentos entre contas'));
INSERT INTO categories (name, parent_id) VALUES ('Saques', (SELECT id FROM categories WHERE name = 'Lançamentos entre contas'));
