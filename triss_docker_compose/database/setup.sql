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

CREATE TABLE Request(
    id INT PRIMARY KEY,
    employeeId INT,
    createdOn DATE,
    placeId INT,
    conferenceStartDate DATE,
    conferenceEndDate DATE,
    purpose VARCHAR(255),
    description VARCHAR(MAX),
    subject VARCHAR(255),
    financialSourceId INT,
    vehicleList VARCHAR(MAX),
    routeList VARCHAR(MAX),
    departureTime DATETIME,
    carrier VARCHAR(255),
    abroadStartDate DATE NULL,
    abroadEndDate DATE NULL,
    selfInsured BOOL,
    advanceRequestId INT,
    prepaymentId INT,
    identityDocument INT,
    comments VARCHAR(MAX)
);

INSERT INTO Request
VALUES
(1, 1, '2008-11-11', 1, '2008-11-11', '2008-11-11', 'purpose', 'description',
 'subject', 1, 'vehicleList', 'routeList', '2008-11-11 13:23:44', 'carrier', NULL, NULL,
 0, 1, 1, 1, 'comments'),
(2, 1, '2008-11-11', 1, '2008-11-11', '2008-11-11', 'purpose', 'description',
    'subject', 1, 'vehicleList', 'routeList', '2008-11-11 13:23:44', 'carrier', NULL, NULL,
    0, 1, 1, 1, 'comments'),
(3, 3, '2008-11-11', 1, '2008-11-11', '2008-11-11', 'purpose', 'description',
    'subject', 1, 'vehicleList', 'routeList', '2008-11-11 13:23:44', 'carrier', NULL, NULL,
    0, 1, 1, 1, 'comments')
