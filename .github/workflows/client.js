const { Client } = require('pg');

const pgclient = new Client({
    host: process.env.POSTGRES_HOST,
    port: process.env.POSTGRES_PORT,
    user: 'postgres',
    password: 'postgres',
    database: 'wordsdb'
});

pgclient.connect();

const table = 'CREATE TABLE words (id INTEGER NOT NULL SERIAL, english varchar(50) NOT NULL, russian varchar(50) NOT NULL, description varchar(255), date_of_registry date, transcription varchar(50), PRIMARY KEY(id)); CREATE TABLE verbs (id INTEGER NOT NULL SERIAL, russian varchar(50) NOT NULL, first_form varchar(50) NOT NULL, second_form varchar(50) NOT NULL, third_form varchar(50) NOT NULL, description varchar(255), date_of_registry date, PRIMARY KEY(id));'
const text = 'INSERT INTO words(english, russian, description, date_of_registry, transcription) VALUES($1, $2, $3, $4, $5) RETURNING *'
const values = ['hello', 'здравствуйте', 'здравствуйте', '2021-01-01', null]

pgclient.query(table, (err, res) => {
    if (err) throw err
});

pgclient.query(text, values, (err, res) => {
    if (err) throw err
});

pgclient.query('SELECT * FROM student', (err, res) => {
    if (err) throw err
    console.log(err, res.rows) // Print the data in student table
    pgclient.end()
});