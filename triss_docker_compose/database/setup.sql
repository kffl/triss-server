CREATE TABLE Employee(
 id bigint PRIMARY KEY,
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

CREATE TYPE mood AS ENUM ('sad', 'ok', 'happy');

CREATE TABLE Application(
    id bigint PRIMARY KEY,
    employeeId bigint,
    createdOn DATE,
    placeId bigint,
    conferenceStartDate DATE,
    conferenceEndDate DATE,
    purpose VARCHAR(255),
    description VARCHAR(255),
    subject VARCHAR(255),
    financialSourceId bigint,
    vehicleList VARCHAR(255),
    routeList VARCHAR(255),
    departureTime timestamp,
    carrier VARCHAR(255),
    abroadStartDate DATE NULL,
    abroadEndDate DATE NULL,
    selfInsured BOOL,
    advanceRequestId bigint,
    prepaymentId bigint,
    identityDocumentID bigint,
    comments VARCHAR(255)
);

INSERT INTO Application
VALUES
(1, 1, '2008-11-11', 1, '2008-11-11', '2008-11-11', 'purpose', 'description',
 'subject', 1, 'vehicleList', 'routeList', '2008-11-11 13:23:44', 'carrier', NULL, NULL,
 false, 1, 1, 1, 'comments'),
(2, 1, '2008-11-11', 1, '2008-11-11', '2008-11-11', 'purpose', 'description',
    'subject', 1, 'vehicleList', 'routeList', '2008-11-11 13:23:44', 'carrier', NULL, NULL,
    false, 1, 1, 1, 'comments'),
(3, 3, '2008-11-11', 1, '2008-11-11', '2008-11-11', 'purpose', 'description',
    'subject', 1, 'vehicleList', 'routeList', '2008-11-11 13:23:44', 'carrier', NULL, NULL,
    false, 1, 1, 1, 'comments')
