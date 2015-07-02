CREATE TABLE category (
  id SERIAL NOT NULL PRIMARY KEY,
  name CHARACTER VARYING(250),
  parentId INTEGER REFERENCES CATEGORY (ID)
);

INSERT INTO category (name) VALUES ('Renda');
INSERT INTO category (name, parentId) VALUES ('Remuneração', (SELECT id FROM category WHERE name = 'Renda'));
INSERT INTO category (name, parentId) VALUES ('Bônus', (SELECT id FROM category WHERE name = 'Renda'));
INSERT INTO category (name, parentId) VALUES ('Rendimento', (SELECT id FROM category WHERE name = 'Renda'));

INSERT INTO category (name) VALUES ('Gastos Essenciais');
INSERT INTO category (name, parentId) VALUES ('Moradia', (SELECT id FROM category WHERE name = 'Gastos Essenciais'));
INSERT INTO category (name, parentId) VALUES ('Contas residenciais', (SELECT id FROM category WHERE name = 'Gastos Essenciais'));
INSERT INTO category (name, parentId) VALUES ('Saúde', (SELECT id FROM category WHERE name = 'Gastos Essenciais'));
INSERT INTO category (name, parentId) VALUES ('Educação', (SELECT id FROM category WHERE name = 'Gastos Essenciais'));
INSERT INTO category (name, parentId) VALUES ('Transporte', (SELECT id FROM category WHERE name = 'Gastos Essenciais'));
INSERT INTO category (name, parentId) VALUES ('Mercado-', (SELECT id FROM category WHERE name = 'Gastos Essenciais'));
INSERT INTO category (name, parentId) VALUES ('Condominio', (SELECT id FROM category WHERE name = 'Gastos Essenciais'));

INSERT INTO category (name) VALUES ('Estilo de Vida');
INSERT INTO category (name, parentId) VALUES ('Empregados domésticos', (SELECT id FROM category WHERE name = 'Estilo de Vida'));
INSERT INTO category (name, parentId) VALUES ('TV / Internet / Telefone', (SELECT id FROM category WHERE name = 'Estilo de Vida'));
INSERT INTO category (name, parentId) VALUES ('Taxas bancárias', (SELECT id FROM category WHERE name = 'Estilo de Vida'));
INSERT INTO category (name, parentId) VALUES ('Bares / Restaurantes', (SELECT id FROM category WHERE name = 'Estilo de Vida'));
INSERT INTO category (name, parentId) VALUES ('Lazer', (SELECT id FROM category WHERE name = 'Estilo de Vida'));
INSERT INTO category (name, parentId) VALUES ('Compras', (SELECT id FROM category WHERE name = 'Estilo de Vida'));
INSERT INTO category (name, parentId) VALUES ('Cuidados pessoais', (SELECT id FROM category WHERE name = 'Estilo de Vida'));
INSERT INTO category (name, parentId) VALUES ('Serviços', (SELECT id FROM category WHERE name = 'Estilo de Vida'));
INSERT INTO category (name, parentId) VALUES ('Viagem', (SELECT id FROM category WHERE name = 'Estilo de Vida'));
INSERT INTO category (name, parentId) VALUES ('Presentes / Doações', (SELECT id FROM category WHERE name = 'Estilo de Vida'));
INSERT INTO category (name, parentId) VALUES ('Família / Filhos', (SELECT id FROM category WHERE name = 'Estilo de Vida'));
INSERT INTO category (name, parentId) VALUES ('Despesas do trabalho', (SELECT id FROM category WHERE name = 'Estilo de Vida'));
INSERT INTO category (name, parentId) VALUES ('Outros gastos', (SELECT id FROM category WHERE name = 'Estilo de Vida'));
INSERT INTO category (name, parentId) VALUES ('Impostos', (SELECT id FROM category WHERE name = 'Estilo de Vida'));

INSERT INTO category (name) VALUES ('Empréstimos');
INSERT INTO category (name, parentId) VALUES ('Juros de cartão',(SELECT id FROM category WHERE name = 'Empréstimos'));
INSERT INTO category (name, parentId) VALUES ('Crediário',(SELECT id FROM category WHERE name = 'Empréstimos'));
INSERT INTO category (name, parentId) VALUES ('Cheque especial',(SELECT id FROM category WHERE name = 'Empréstimos'));
INSERT INTO category (name, parentId) VALUES ('Crédito consignado',(SELECT id FROM category WHERE name = 'Empréstimos'));
INSERT INTO category (name, parentId) VALUES ('Carnê',(SELECT id FROM category WHERE name = 'Empréstimos'));
INSERT INTO category (name, parentId) VALUES ('Outros empréstimos',(SELECT id FROM category WHERE name = 'Empréstimos'));
INSERT INTO category (name, parentId) VALUES ('Juros',(SELECT id FROM category WHERE name = 'Empréstimos'));

INSERT INTO category (name) VALUES ('Lançamentos entre contas');
INSERT INTO category (name, parentId) VALUES ('Pagamento de cartão',(SELECT id FROM category WHERE name = 'Lançamentos entre contas'));
INSERT INTO category (name, parentId) VALUES ('Resgate',(SELECT id FROM category WHERE name = 'Lançamentos entre contas'));
INSERT INTO category (name, parentId) VALUES ('Aplicação',(SELECT id FROM category WHERE name = 'Lançamentos entre contas'));
INSERT INTO category (name, parentId) VALUES ('Transferência',(SELECT id FROM category WHERE name = 'Lançamentos entre contas'));
INSERT INTO category (name, parentId) VALUES ('Saques', (SELECT id FROM category WHERE name = 'Lançamentos entre contas'));
