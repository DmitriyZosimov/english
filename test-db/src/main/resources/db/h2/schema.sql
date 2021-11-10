DROP TABLE words IF EXISTS;

CREATE TABLE words (
    id INTEGER NOT NULL AUTO_INCREMENT,
    english varchar(50) NOT NULL,
    russian varchar(50) NOT NULL,
    description varchar(255),
    date_of_registry date,
    PRIMARY KEY(id)
);