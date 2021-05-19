CREATE SEQUENCE IF NOT EXISTS cliente_id_seq;

CREATE TABLE IF NOT EXISTS cliente (
	id INT8 NOT NULL,
	nome VARCHAR(100) not null,
	cpf VARCHAR(11) not null,
	data_nascimento date NOT NULL,
	data_criacao timestamp default CURRENT_TIMESTAMP,
	CONSTRAINT pk_cliente PRIMARY KEY (id)
);

ALTER TABLE cliente ALTER COLUMN id SET DEFAULT NEXTVAL('cliente_id_seq');
