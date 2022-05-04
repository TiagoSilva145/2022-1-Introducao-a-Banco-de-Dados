DROP DATABASE IF EXISTS livrosdb;
CREATE DATABASE IF NOT EXISTS livrosdb;
USE livrosdb;

SELECT 'CREATING DATABASE STRUCTURE' as 'INFO';

DROP TABLE IF EXISTS livros,
                     autores;

CREATE TABLE livros (
    livro_id	INT		    	NOT NULL,
    titulo		VARCHAR(500)	NOT NULL,
    autor		VARCHAR(100)	NOT NULL,
    edicao		INT		    	NOT NULL,
    ano			INT		    	NOT NULL,
	editora		VARCHAR(100)	NOT NULL,
	PRIMARY KEY (livro_id),
    UNIQUE KEY  (titulo)
);

SELECT 'LOADING livros' as 'INFO';

LOAD DATA INFILE '/var/lib/mysql-files/livros-db.csv'
    INTO TABLE livros
    FIELDS TERMINATED BY ';'
    ENCLOSED BY '"'
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;
