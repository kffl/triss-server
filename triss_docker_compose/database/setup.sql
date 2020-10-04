CREATE TABLE Employee(
 id INT PRIMARY KEY,
 firstName varchar(255),
 surname varchar(255),
 birthDate DATE,
 birthPlace varchar(255)
);

INSERT INTO employee(id,firstName,surname,birthDate,birthPlace) 
VALUES
(1,'Andrzej','Kmicic','2000-01-01','Poznan'),
(2,'Jan','Kowalski','1990-03-21','Wroclaw'),
(3,'Jerzy','Zbiałowierzy','1980-05-15','Warszawa'),
(4,'Andrzej','Nowak','1988-07-16','Sosnowiec');
