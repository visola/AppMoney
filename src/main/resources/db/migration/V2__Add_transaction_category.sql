CREATE TABLE categories (
  id SERIAL NOT NULL PRIMARY KEY,
  name CHARACTER VARYING(250),
  parentId INTEGER REFERENCES categories (ID)
);

INSERT INTO categories (name) VALUES ('Renda');
INSERT INTO categories (name, parentId) VALUES ('Remuneração', (SELECT id FROM categories WHERE name = 'Renda'));
INSERT INTO categories (name, parentId) VALUES ('Bônus', (SELECT id FROM categories WHERE name = 'Renda'));
INSERT INTO categories (name, parentId) VALUES ('Rendimento', (SELECT id FROM categories WHERE name = 'Renda'));

INSERT INTO categories (name) VALUES ('Gastos Essenciais');
INSERT INTO categories (name, parentId) VALUES ('Moradia', (SELECT id FROM categories WHERE name = 'Gastos Essenciais'));
INSERT INTO categories (name, parentId) VALUES ('Contas residenciais', (SELECT id FROM categories WHERE name = 'Gastos Essenciais'));
INSERT INTO categories (name, parentId) VALUES ('Saúde', (SELECT id FROM categories WHERE name = 'Gastos Essenciais'));
INSERT INTO categories (name, parentId) VALUES ('Educação', (SELECT id FROM categories WHERE name = 'Gastos Essenciais'));
INSERT INTO categories (name, parentId) VALUES ('Transporte', (SELECT id FROM categories WHERE name = 'Gastos Essenciais'));
INSERT INTO categories (name, parentId) VALUES ('Mercado-', (SELECT id FROM categories WHERE name = 'Gastos Essenciais'));
INSERT INTO categories (name, parentId) VALUES ('Condominio', (SELECT id FROM categories WHERE name = 'Gastos Essenciais'));

INSERT INTO categories (name) VALUES ('Estilo de Vida');
INSERT INTO categories (name, parentId) VALUES ('Empregados domésticos', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parentId) VALUES ('TV / Internet / Telefone', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parentId) VALUES ('Taxas bancárias', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parentId) VALUES ('Bares / Restaurantes', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parentId) VALUES ('Lazer', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parentId) VALUES ('Compras', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parentId) VALUES ('Cuidados pessoais', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parentId) VALUES ('Serviços', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parentId) VALUES ('Viagem', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parentId) VALUES ('Presentes / Doações', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parentId) VALUES ('Família / Filhos', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parentId) VALUES ('Despesas do trabalho', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parentId) VALUES ('Outros gastos', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));
INSERT INTO categories (name, parentId) VALUES ('Impostos', (SELECT id FROM categories WHERE name = 'Estilo de Vida'));

INSERT INTO categories (name) VALUES ('Empréstimos');
INSERT INTO categories (name, parentId) VALUES ('Juros de cartão',(SELECT id FROM categories WHERE name = 'Empréstimos'));
INSERT INTO categories (name, parentId) VALUES ('Crediário',(SELECT id FROM categories WHERE name = 'Empréstimos'));
INSERT INTO categories (name, parentId) VALUES ('Cheque especial',(SELECT id FROM categories WHERE name = 'Empréstimos'));
INSERT INTO categories (name, parentId) VALUES ('Crédito consignado',(SELECT id FROM categories WHERE name = 'Empréstimos'));
INSERT INTO categories (name, parentId) VALUES ('Carnê',(SELECT id FROM categories WHERE name = 'Empréstimos'));
INSERT INTO categories (name, parentId) VALUES ('Outros empréstimos',(SELECT id FROM categories WHERE name = 'Empréstimos'));
INSERT INTO categories (name, parentId) VALUES ('Juros',(SELECT id FROM categories WHERE name = 'Empréstimos'));

INSERT INTO categories (name) VALUES ('Lançamentos entre contas');
INSERT INTO categories (name, parentId) VALUES ('Pagamento de cartão',(SELECT id FROM categories WHERE name = 'Lançamentos entre contas'));
INSERT INTO categories (name, parentId) VALUES ('Resgate',(SELECT id FROM categories WHERE name = 'Lançamentos entre contas'));
INSERT INTO categories (name, parentId) VALUES ('Aplicação',(SELECT id FROM categories WHERE name = 'Lançamentos entre contas'));
INSERT INTO categories (name, parentId) VALUES ('Transferência',(SELECT id FROM categories WHERE name = 'Lançamentos entre contas'));
INSERT INTO categories (name, parentId) VALUES ('Saques', (SELECT id FROM categories WHERE name = 'Lançamentos entre contas'));
