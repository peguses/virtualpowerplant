DROP TABLE IF EXISTS batteries;
CREATE TABLE batteries (
id bigint  AUTO_INCREMENT  PRIMARY KEY,
name varchar(255) NOT NULL,
postcode integer  NOT NULL,
capacity integer  NOT NULL
);