const { Client } = require('pg');

const pgclient = new Client({
    host: process.env.POSTGRES_HOST,
    port: process.env.POSTGRES_PORT,
    user: 'postgres',
    password: 'postgres',
    database: 'wordsdb'
});

pgclient.connect();

const table = 'CREATE TABLE words (\n' +
    '    id SERIAL,\n' +
    '    english varchar(50) NOT NULL,\n' +
    '    russian varchar(50) NOT NULL,\n' +
    '    description varchar(255),\n' +
    '    date_of_registry date,\n' +
    '    transcription varchar(50),\n' +
    '    PRIMARY KEY(id)\n' +
    ');\n' +
    '\n' +
    'CREATE TABLE verbs (\n' +
    '    id SERIAL,\n' +
    '    russian varchar(50) NOT NULL,\n' +
    '    first_form varchar(50) NOT NULL,\n' +
    '    second_form varchar(50) NOT NULL,\n' +
    '    third_form varchar(50) NOT NULL,\n' +
    '    description varchar(255),\n' +
    '    date_of_registry date,\n' +
    '    PRIMARY KEY(id)\n' +
    ');'
const text = 'INSERT INTO words(english, russian, description, date_of_registry, transcription) VALUES($1, $2, $3, $4, $5) RETURNING *'
const values = ['hello', 'здравствуйте', 'здравствуйте', '2021-01-01', null]

pgclient.query(table, (err, res) => {
    if (err) throw err
});

pgclient.query(text, values, (err, res) => {
    if (err) throw err
});

pgclient.query('SELECT * FROM words', (err, res) => {
    if (err) throw err
    console.log(err, res.rows) // Print the data in student table
    pgclient.end()
});