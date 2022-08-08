DROP DATABASE IF EXISTS livrosdb;
CREATE DATABASE IF NOT EXISTS livrosdb;
USE livrosdb;

SELECT 'CREATING DATABASE STRUCTURE' as 'INFO';

DROP TABLE IF EXISTS livros,
                     autores;

CREATE TABLE livros (
    livro_id	INT			NOT NULL,
    titulo		VARCHAR(40)	NOT NULL,
    autor		VARCHAR(40)	NOT NULL,
    edicao		INT			NOT NULL,
    ano			INT			NOT NULL,
	editora		VARCHAR(40)	NOT NULL,
	PRIMARY KEY (livro_id)
);


