DROP DATABASE IF EXISTS articles_desnormalizado;
CREATE DATABASE IF NOT EXISTS articles_desnormalizado;
USE articles_desnormalizado;

DROP TABLE IF EXISTS `article`;
DROP TABLE IF EXISTS `author`;
DROP TABLE IF EXISTS `keyword`;
DROP TABLE IF EXISTS `collection`;
DROP TABLE IF EXISTS `citations`;



-- ************************************** `article`

CREATE TABLE IF NOT EXISTS `collection`
(
 `collection_id`      int NOT NULL ,
 `name`        varchar(45) NOT NULL ,
 `description` varchar(300) NOT NULL ,

PRIMARY KEY (`collection_id`)
);

CREATE TABLE IF NOT EXISTS `article`
(
 `article_id` int NOT NULL ,
 `title`      varchar(45) NOT NULL ,
 `collection_id`     int NOT NULL ,
 `page_num`   int NOT NULL ,
 `tipo`       varchar(45) NOT NULL ,
 `abstract`   varchar(3000) NOT NULL ,
 `citatons`   varchar(300) NOT NULL ,

PRIMARY KEY (`article_id`),
KEY `FK_70` (`collection_id`),
CONSTRAINT `FK_68` FOREIGN KEY `FK_70` (`collection_id`) REFERENCES `collection` (`collection_id`)
);

CREATE TABLE IF NOT EXISTS `author`
(
 `author_id`   int NOT NULL ,
 `article_id`  int NOT NULL ,
 `name`        varchar(45) NOT NULL ,
 `institution` varchar(300) NOT NULL ,
 `email`       varchar(100) NOT NULL ,
 `citations`   varchar(300) NOT NULL ,

PRIMARY KEY (`author_id`),
KEY `FK_20` (`article_id`),
CONSTRAINT `author of the article` FOREIGN KEY `FK_20` (`article_id`) REFERENCES `article` (`article_id`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `keyword`
(
 `keyword_id` int NOT NULL ,
 `article_id` int NOT NULL ,
 `keyword`    varchar(20) NOT NULL ,

PRIMARY KEY (`keyword_id`),
KEY `FK_33` (`article_id`),
CONSTRAINT `FK_31` FOREIGN KEY `FK_33` (`article_id`) REFERENCES `article` (`article_id`)
);

CREATE TABLE IF NOT EXISTS `citations`
(
 `citation_id` int NOT NULL ,
 `article_id`  int NOT NULL ,
 `citation`    varchar(20) NOT NULL ,

PRIMARY KEY (`citation_id`),
KEY `FK_56` (`article_id`),
CONSTRAINT `FK_54` FOREIGN KEY `FK_56` (`article_id`) REFERENCES `article` (`article_id`)
);

