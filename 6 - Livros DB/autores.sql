USE livrosdb;
SELECT 'CREATING autores' AS 'INFO';

DROP TABLE IF EXISTS autores;

CREATE TABLE autores(
	nome	VARCHAR(50)		NOT NULL,
	titulo	VARCHAR(500)	NOT NULL,
	FOREIGN KEY (titulo) REFERENCES livros(titulo) ON DELETE CASCADE,
	PRIMARY KEY (nome,titulo)
);
