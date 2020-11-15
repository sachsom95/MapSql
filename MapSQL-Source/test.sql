CREATE TABLE test(name CHAR(30) NOT NULL, age int);

INSERT INTO test(name, age) VALUES('sachinsoman', 25);
INSERT INTO test(name, age) VALUES('rad', 20);
INSERT INTO test(name, age) VALUES('cvc', 15);
INSERT INTO test(name, age) VALUES('c', 15);
INSERT INTO test(name, age) VALUES('cvcasdadasda', 15);

SELECT * from test;

select * from test where age <> 25;
select * from test where age <50;

select * from test where name LIKE '%soman';
select * from test where name LIKE 'sachin%';
select * from test where name LIKE '%sac%';

UPDATE test SET name = 'Alfred Schmidt' WHERE age = 25;

DELETE from test WHERE AGE = 25;

INSERT INTO test (age) VALUES(25);

DROP TABLE test;

--SOURCE "test.sql";