CREATE TABLE Employee(
 id BIGINT PRIMARY KEY,
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
    id BIGINT PRIMARY KEY,
    employeeId BIGINT,
    createdOn DATE,
    placeId BIGINT,
    conferenceStartDate DATE,
    conferenceEndDate DATE,
    purpose VARCHAR(255),
    description VARCHAR(255),
    subject VARCHAR(255),
    financialSourceId BIGINT,
    vehicleList VARCHAR(255),
    routeList VARCHAR(255),
    departureTime TIMESTAMP,
    carrier VARCHAR(255),
    abroadStartDate DATE NULL,
    abroadEndDate DATE NULL,
    selfInsured BOOL,
    advanceRequestId BIGINT,
    prepaymentId BIGINT,
    identityDocumentID BIGINT,
    comments VARCHAR(255),
    status VARCHAR(255)
);

INSERT INTO Application
VALUES
(1, 1, '2008-11-11', 1, '2008-11-11', '2008-11-11', 'purpose', 'description',
 'subject', 1, 'vehicleList', 'routeList', '2008-11-11 13:23:44', 'carrier', '2008-11-12', '2021-12-12',
 false, 1, 1, 1, 'comments', 'Odrzucono'),
(2, 1, '2008-11-11', 2, '2008-11-11', '2008-11-11', 'purpose', 'description',
 'subject', 1, 'vehicleList', 'routeList', '2008-11-11 13:23:44', 'carrier', '2018-05-4', '2018-05-07',
 false, 1, 1, 1, 'comments', 'Oczekiwanie na decyzję Dyrektora'),
(3, 3, '2008-11-11', 2, '2008-11-11', '2008-11-11', 'purpose', 'description',
 'subject', 1, 'vehicleList', 'routeList', '2008-11-11 13:23:44', 'carrier', '2008-9-11', '2008-11-11',
 false, 1, 1, 1, 'comments', 'Zatwierdzono');

CREATE TABLE Place(
    id BIGINT PRIMARY KEY,
    country VARCHAR(255),
    city VARCHAR(255),
    Address VARCHAR (255)
);

INSERT INTO Place
VALUES
( 1, 'U.S.A', 'Los Angeles', '3466 Division C.T' ),
( 2, 'Poland', 'Pobiedziska', 'Lesna 54 62-010');

CREATE TABLE IdentityDocument(
    id BIGINT PRIMARY KEY,
    type VARCHAR(255),
    name VARCHAR(255),
    number VARCHAR(255)
);

INSERT INTO IdentityDocument
VALUES
(1, 'IdCard', 'Name1', 'ABC12345'),
(2, 'Passport', 'Name2', 'ZE8000199');

CREATE TABLE AdvanceApplication(
    id BIGINT PRIMARY KEY,
    placeId BIGINT,
    startDate DATE,
    endDate DATE,
    residenceDietQuantity BIGINT,
    residenceDietAmount DECIMAL(7,2),
    accommodationQuantity BIGINT,
    aalimit VARCHAR(255),
    travelDietAmount DECIMAL(7,2),
    travelCosts DECIMAL(7,2),
    otherCostsDescription VARCHAR(255),
    otherCostsAmount DECIMAL(7,2),
    advanceSum DECIMAL(7,2)
);

CREATE TABLE Fee(
    id BIGINT PRIMARY KEY,
    amount DECIMAL(7,2),
    paymentType VARCHAR(255)
);

CREATE TABLE Prepayment(
    id BIGINT PRIMARY KEY,
    conferenceFeeId BIGINT,
    accommodationFeeId BIGINT
);

CREATE VIEW ApplicationRow AS
SELECT Application.id, employeeId, country, city, abroadStartDate, abroadEndDate, status
FROM Application JOIN Place
ON Place.id = Application.placeId