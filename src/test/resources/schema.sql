DROP TABLE IF EXISTS batteries;
CREATE TABLE batteries (
id bigint  AUTO_INCREMENT  PRIMARY KEY,
uuid uuid NOT null,
name varchar(255) NOT NULL,
postcode integer  NOT NULL,
capacity integer  NOT NULL,
UNIQUE (uuid)
);