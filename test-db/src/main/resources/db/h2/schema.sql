DROP TABLE words IF EXISTS;
DROP TABLE verbs IF EXISTS;

CREATE TABLE words (
    id INTEGER NOT NULL AUTO_INCREMENT,
    english varchar(50) NOT NULL,
    russian varchar(50) NOT NULL,
    description varchar(255),
    date_of_registry date,
    PRIMARY KEY(id)
);

CREATE TABLE verbs (
    id INTEGER NOT NULL AUTO_INCREMENT,
    russian varchar(50) NOT NULL,
    first_form varchar(50) NOT NULL,
    second_form varchar(50) NOT NULL,
    third_form varchar(50) NOT NULL,
    description varchar(255),
    date_of_registry date,
    PRIMARY KEY(id)
);