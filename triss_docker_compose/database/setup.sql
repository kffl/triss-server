CREATE TABLE EmployeeType(
id BIGSERIAL PRIMARY KEY,
name VARCHAR(255) UNIQUE NOT NULL
);

INSERT INTO EmployeeType (name) VALUES
    ('USER'),('WILDA'),('RECTOR'),
    ('DIRECTOR');


CREATE TABLE Institute(
id BIGSERIAL PRIMARY KEY,
name varchar(255) UNIQUE NOT NULL
);

INSERT INTO Institute (name) VALUES
    ('Instytut Architektury i Planowania Przestrzennego'),('Instytut Architektury, Urbanistyki i Ochrony Dziedzictwa'),('Instytut Architektury Wnętrz i Wzornictwa Przemysłowego'),
    ('Instytut Automatyki i Robotyki'),('Instytut Elektrotechniki i Elektroniki Przemysłowej'),('Instytut Matematyki'),('Instytut Robotyki i Inteligencji Maszynowej'),
    ('Instytut Informatyki'),('Instytut Radiokomunikacji'),('Instytut Sieci Teleinformatycznych '),('Instytut Telekomunikacji Multimedialnej'),
    ('Instytut Chemii i Elektrochemii Technicznej'),('Instytut Technologii i Inżynierii Chemicznej'),
    ('Instytut Logistyki'),('Instytut Inżynierii Bezpieczeństwa i Jakości'),('Instytut Zarządzania i Systemów Informacyjnych'),
    ('Instytut Elektroenergetyki'),('Instytut Energetyki Cieplnej'),('Instytut Inżynierii Środowiska i Instalacji Budowlanych'),
    ('Instytut Mechaniki Stosowanej'),('Instytut Technologii Mechanicznej'),('Instytut Technologii Materiałów'),('Instytut Konstrukcji Maszyn'),
    ('Instytut Badań Materiałowych i Inżynierii Kwantowej'),('Instytut Fizyki'),('Instytut Inżynierii Materiałowej'),
    ('Instytut Analizy Konstrukcji'),('Instytut Budownictwa'),('Instytut Inżynierii Lądowej'),('Instytut Maszyn Roboczych i Pojazdów Samochodowych'),
    ('Instytut Silników Spalinowych i Napędów'),('Instytut Transportu');

CREATE TABLE Place(
    id BIGSERIAL PRIMARY KEY,
    city VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    UNIQUE(city,country)
);

INSERT INTO Place (city, country) VALUES
    ('Los Angeles', 'USA'),('Warszawa', 'Polska'),('Cambridge','USA'),('Montreal', 'Kanada');

CREATE TABLE PrepaymentFee(
    id BIGSERIAL PRIMARY KEY,
    amount DECIMAL(7,2) NOT NULL,
    paymentType VARCHAR(255) NOT NULL
);

INSERT INTO PrepaymentFee (amount, paymentType) VALUES
    (2500, 'Cash'), (1250, 'Card'), (1400, 'Transfer'), (3000.50, 'Transfer');

CREATE TABLE Prepayment(
    id BIGSERIAL PRIMARY KEY,
    conferenceFeeId BIGINT,
    accommodationFeeId BIGINT,
    CONSTRAINT conferenceFee_fk FOREIGN KEY(conferenceFeeId) REFERENCES PrepaymentFee(id),
    CONSTRAINT accommodationFee_fk FOREIGN KEY(accommodationFeeId) REFERENCES PrepaymentFee(id)
);

INSERT INTO Prepayment (conferenceFeeId, accommodationFeeId) VALUES (1,2), (3,4);

CREATE TABLE Employee(
 id BIGINT PRIMARY KEY,
 firstName varchar(255) NOT NULL,
 surname varchar(255) NOT NULL,
 birthDate DATE NOT NULL,
 academicDegree varchar(255) NOT NULL,
 phoneNumber varchar(255) NOT NULL,
 instituteID BIGINT,
 employeeTypeID BIGINT,
 CONSTRAINT institute_fk FOREIGN KEY(instituteID) REFERENCES Institute(id),
 CONSTRAINT employeeType_fk FOREIGN KEY(employeeTypeID) REFERENCES EmployeeType(id)
);

INSERT INTO Employee VALUES
    (1, 'Andrzej', 'Kmicic', '2000-01-01', 'Prof.' ,'+48 123456789', 1, 2),
    (2, 'Jan', 'Kowalski', '1990-03-21', 'Prof.', '+48 321456987', 2, 3),
    (3, 'Jerzy', 'Zbiałowierzy', '1980-05-15', 'Prof.', '+48 541236987', 3, 4),
    (4, 'Andrzej', 'Nowak', '1988-07-16', 'Prof.', '+48 987456321', 4, 4);

CREATE TABLE IdentityDocument(
    id BIGSERIAL PRIMARY KEY,
    employeeID BIGINT NOT NULL,
    type Int NOT NULL,
    number VARCHAR(255) NOT NULL,
    CONSTRAINT employee_fk FOREIGN KEY(employeeID) REFERENCES Employee(id)
);

INSERT INTO IdentityDocument (employeeID, type, number) VALUES
    (1, 0,  'ABC12345'),
    (1, 1,  'DE6789000'),
    (2, 1, 'ZE8000199');

CREATE TABLE FinancialSource
(
    id BIGSERIAL PRIMARY KEY,
    allocationAccount VARCHAR(255),
    MPK VARCHAR(255),
    financialSource VARCHAR(255),
    project VARCHAR(255)
);

INSERT INTO FinancialSource (allocationAccount, MPK, financialSource, project) VALUES
    ('01 2345 6789 0123 4567 8901 2345', 'MPK_1', 'Financial Source 1', 'Project X');

CREATE TABLE AdvanceApplication(
    id BIGSERIAL PRIMARY KEY,
    placeId BIGINT NOT NULL,
    startDate DATE NOT NULL,
    endDate DATE NOT NULL,
    residenceDietQuantity INTEGER NOT NULL,
    residenceDietAmount DECIMAL(7,2) NOT NULL,
    accommodationQuantity INTEGER NOT NULL,
    accommodationLimit DECIMAL(7,2) NOT NULL,
    travelDietAmount DECIMAL(7,2) NOT NULL,
    travelCosts DECIMAL(7,2) NOT NULL,
    otherCostsDescription VARCHAR(255),
    otherCostsAmount DECIMAL(7,2),
    residenceDietSum DECIMAL(7,2) NOT NULL,
    accommodationSum DECIMAL(7,2) NOT NULL,
    advanceSum DECIMAL(7,2) NOT NULL,
    CONSTRAINT place_fk FOREIGN KEY(placeId) REFERENCES Place(id)
);

INSERT INTO AdvanceApplication (placeId, startDate, endDate, residenceDietQuantity, residenceDietAmount, accommodationQuantity,
                                accommodationLimit, travelDietAmount, travelCosts, otherCostsDescription, otherCostsAmount,residenceDietSum,accommodationSum, advanceSum) VALUES
    (1, '2020-12-12', '2020-12-15', 2, 200, 1, 400, 300, 50, NULL, NULL,400,400, 1150),
    (4, '2020-11-10', '2020-11-13', 1, 500, 1, 500, 400, 50, NULL, NULL,500,500,1450);

CREATE TABLE Application(
    id BIGSERIAL PRIMARY KEY,
    employeeId BIGINT NOT NULL,
    createdOn DATE NOT NULL,
    placeId BIGINT NOT NULL,
    instituteId BIGINT NOT NULL,
    abroadStartDate DATE NOT NULL,
    abroadEndDate DATE NOT NULL,
    purpose VARCHAR(255) NOT NULL,
    conference VARCHAR(255) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    conferenceStartDate DATE NOT NULL,
    conferenceEndDate DATE NOT NULL,
    financialSourceId BIGINT,
    abroadStartDateInsurance DATE NOT NULL,
    abroadEndDateInsurance DATE NOT NULL,
    selfInsured BOOL NOT NULL,
    advanceRequestId BIGINT NOT NULL,
    prepaymentId BIGINT NOT NULL,
    identityDocumentId BIGINT NOT NULL,
    comments VARCHAR(255),
    status VARCHAR(255) NOT NULL,
    CONSTRAINT institute_fk FOREIGN KEY(instituteId) REFERENCES Institute(id),
    CONSTRAINT employee_fk FOREIGN KEY(employeeId) REFERENCES Employee(id),
    CONSTRAINT place_fk FOREIGN KEY(placeId) REFERENCES Place(id),
    CONSTRAINT financialSource_fk FOREIGN KEY(financialSourceId) REFERENCES FinancialSource(id),
    CONSTRAINT advanceRequest_fk FOREIGN KEY(advanceRequestId) REFERENCES AdvanceApplication(id),
    CONSTRAINT prepayment_fk FOREIGN KEY(prepaymentId) REFERENCES Prepayment(id)
);

INSERT INTO Application (employeeId, createdOn, placeId,instituteId, abroadStartDate, abroadEndDate, purpose, conference, subject,
                         conferenceStartDate, conferenceEndDate, financialSourceId, abroadStartDateInsurance, abroadEndDateInsurance,
                         selfInsured, advanceRequestId, prepaymentId, identityDocumentID, comments, status) VALUES
    (1, '2020-11-03', 1,1, '2020-12-12', '2020-12-15', 'Konferencja', 'AntyCovid2020', ' TRISS: Wirtualizacja funkcjonowania Sekcji Współpracy z Zagranicą',
     '2020-12-13','2020-12-14', NULL, '2020-12-12', '2020-12-15', FALSE, 1, 1, 1, NULL, 'WaitingForRector'),
    (2, '2020-11-04', 4,2,'2020-11-10', '2020-11-13', 'Konferencja', 'AntyCovid2020', ' TRISS: Wirtualizacja funkcjonowania Sekcji Współpracy z Zagranicą',
     '2020-11-10', '2020-11-13', 1 , '2020-11-10', '2020-11-13', FALSE, 2, 2, 3, NULL, 'WaitingForRector');

CREATE TABLE Transport
(
 id BIGSERIAL PRIMARY KEY,
 applicationID BIGINT,
 destinationFrom VARCHAR(255) NOT NULL,
 destinationTo VARCHAR(255) NOT NULL,
 departureDay DATE NOT NULL,
 departureHour INTEGER NOT NULL,
 departureMinute INTEGER NOT NULL,
 carrier VARCHAR(255) NOT NULL,
 vehicleSelect VARCHAR(255) NOT NULL,
 CONSTRAINT application_fk FOREIGN KEY(applicationID) REFERENCES Application(id)
);

INSERT INTO Transport (applicationID, destinationFrom, destinationTo, departureDay, departureHour, departureMinute,vehicleSelect, carrier) VALUES
    (1, 'Poznań', 'Los Angeles', '2020-12-12', 6, 30,'Plane', 'LOT'),
    (1, 'Los Angeles', 'Poznań', '2020-12-14', 20, 10,'Plane', 'RyanAir'),
    (2, 'Poznań', 'Montreal', '2020-11-10', 4, 24,'Plane', 'LOT'),
    (2, 'Montreal', 'Poznań', '2020-11-13', 5, 30,'Plane', 'RyanAir');

CREATE VIEW ApplicationRow AS
SELECT Application.id, employeeId, country, city, abroadStartDate, abroadEndDate, status
FROM Application JOIN Place
ON Place.id = Application.placeId;



CREATE VIEW ApplicationFull AS
SELECT A.id,createdOn, abroadStartDate, abroadEndDate, purpose, conference, subject, conferenceStartDate, conferenceEndDate, abroadStartDateInsurance, abroadEndDateInsurance, selfInsured, comments, status,
I.name,
firstName, surname, birthDate, academicDegree, phoneNumber,
city, country,
allocationAccount, MPK, financialSource, project,
startDate, endDate, residenceDietQuantity, residenceDietAmount, accommodationQuantity, accommodationLimit, travelDietAmount, travelCosts, otherCostsDescription, otherCostsAmount, residenceDietSum, accommodationSum, advanceSum,
PFA.amount as accommodationFeeValue, PFA.paymentType as accommodationFeePaymentTypeSelect,
PFC.amount as conferenceFeeValue, PFC.paymentType as conferenceFeePaymentTypeSelect,
D.number,D.type
FROM Application A
JOIN Institute I on A.instituteId = I.id
JOIN Employee E on A.employeeId = E.id
JOIN Place P on A.placeId = P.id
LEFT JOIN FinancialSource FS on A.financialSourceId = FS.id
JOIN AdvanceApplication AA on A.advanceRequestId = AA.id
JOIN Prepayment P2 on A.prepaymentId = P2.id
JOIN PrepaymentFee PFA on P2.accommodationFeeId = PFA.id
JOIN PrepaymentFee PFC on P2.conferenceFeeId = PFC.id
JOIN IdentityDocument D on A.identityDocumentID=D.id






