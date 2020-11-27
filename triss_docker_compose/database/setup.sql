-- CREATE TABLE EmployeeType
-- (
--     id   BIGSERIAL PRIMARY KEY,
--     name VARCHAR(255) UNIQUE NOT NULL
-- );
--
-- INSERT INTO EmployeeType (name)
-- VALUES ('USER'),
--        ('WILDA'),
--        ('RECTOR'),
--        ('DIRECTOR');



CREATE TABLE Institute
(
    id     BIGSERIAL PRIMARY KEY,
    name   varchar(255) UNIQUE NOT NULL,
    active boolean default True
);

INSERT INTO Institute (name)
VALUES ('Instytut Architektury i Planowania Przestrzennego'),
       ('Instytut Architektury, Urbanistyki i Ochrony Dziedzictwa'),
       ('Instytut Architektury Wnętrz i Wzornictwa Przemysłowego'),
       ('Instytut Automatyki i Robotyki'),
       ('Instytut Elektrotechniki i Elektroniki Przemysłowej'),
       ('Instytut Matematyki'),
       ('Instytut Robotyki i Inteligencji Maszynowej'),
       ('Instytut Informatyki'),
       ('Instytut Radiokomunikacji'),
       ('Instytut Sieci Teleinformatycznych '),
       ('Instytut Telekomunikacji Multimedialnej'),
       ('Instytut Chemii i Elektrochemii Technicznej'),
       ('Instytut Technologii i Inżynierii Chemicznej'),
       ('Instytut Logistyki'),
       ('Instytut Inżynierii Bezpieczeństwa i Jakości'),
       ('Instytut Zarządzania i Systemów Informacyjnych'),
       ('Instytut Elektroenergetyki'),
       ('Instytut Energetyki Cieplnej'),
       ('Instytut Inżynierii Środowiska i Instalacji Budowlanych'),
       ('Instytut Mechaniki Stosowanej'),
       ('Instytut Technologii Mechanicznej'),
       ('Instytut Technologii Materiałów'),
       ('Instytut Konstrukcji Maszyn'),
       ('Instytut Badań Materiałowych i Inżynierii Kwantowej'),
       ('Instytut Fizyki'),
       ('Instytut Inżynierii Materiałowej'),
       ('Instytut Analizy Konstrukcji'),
       ('Instytut Budownictwa'),
       ('Instytut Inżynierii Lądowej'),
       ('Instytut Maszyn Roboczych i Pojazdów Samochodowych'),
       ('Instytut Silników Spalinowych i Napędów'),
       ('Instytut Transportu');

CREATE TABLE Place
(
    id      BIGSERIAL PRIMARY KEY,
    city    VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    UNIQUE (city, country)
);

INSERT INTO Place (city, country)
VALUES ('Los Angeles', 'USA'),
       ('Warszawa', 'Polska'),
       ('Cambridge', 'USA'),
       ('Montreal', 'Kanada');

CREATE TABLE PrepaymentFee
(
    id          BIGSERIAL PRIMARY KEY,
    amount      DECIMAL(7, 2) NOT NULL,
    paymentType VARCHAR(255)  NOT NULL
);

-- INSERT INTO PrepaymentFee (amount, paymentType)
-- VALUES (2500, 'Cash'),
--        (1250, 'Card'),
--        (1400, 'Transfer'),
--        (3000.50, 'Transfer');

CREATE TABLE Prepayment
(
    id                 BIGSERIAL PRIMARY KEY,
    conferenceFeeId    BIGINT,
    accommodationFeeId BIGINT,
    CONSTRAINT conferenceFee_fk FOREIGN KEY (conferenceFeeId) REFERENCES PrepaymentFee (id),
    CONSTRAINT accommodationFee_fk FOREIGN KEY (accommodationFeeId) REFERENCES PrepaymentFee (id)
);

-- INSERT INTO Prepayment (conferenceFeeId, accommodationFeeId)
-- VALUES (1, 2),
--        (3, 4);

CREATE TABLE Employee
(
    id             BIGINT PRIMARY KEY,
    firstName      varchar(255) NOT NULL,
    surname        varchar(255) NOT NULL,
    birthDate      DATE         NOT NULL,
    academicDegree varchar(255) NOT NULL,
    phoneNumber    varchar(255) NOT NULL,
    employeeType   varchar(255),
    instituteID    BIGINT,
    CONSTRAINT institute_fk FOREIGN KEY (instituteID) REFERENCES Institute (id)
);

INSERT INTO Employee
VALUES (170387, 'Jan', 'Kowalczyk', '2000-01-01', 'Prof.', '+48 123456789', 'USER', 1),
       (2, 'Jan', 'Kowalski', '1990-03-21', 'Prof.', '+48 321456987', 'WILDA', 1),
       (3, 'Jerzy', 'Zbiałowierzy', '1980-05-15', 'Prof.', '+48 541236987', 'RECTOR', 1),
       (167711, 'Andrzej', 'Nowak', '1988-07-16', 'Prof.', '+48 987456321', 'DIRECTOR', 1);

CREATE TABLE IdentityDocument
(
    id         BIGSERIAL PRIMARY KEY,
    employeeID BIGINT       NOT NULL,
    type       VARCHAR(255) NOT NULL,
    number     VARCHAR(255) NOT NULL,
    CONSTRAINT employee_fk FOREIGN KEY (employeeID) REFERENCES Employee (id)
);

INSERT INTO IdentityDocument (employeeID, type, number)
VALUES (170387, 'IdCard', 'ABC12345'),
       (170387, 'Passport', 'DE6789000'),
       (2, 'Passport', 'ZE8000199');

CREATE TABLE FinancialSource
(
    id                   BIGSERIAL PRIMARY KEY,
    allocationAccount    VARCHAR(255),
    MPK                  VARCHAR(255),
    financialSourceValue VARCHAR(255),
    project              VARCHAR(255)
);

-- INSERT INTO FinancialSource (allocationAccount, MPK, financialSourceValue, project)
-- VALUES ('01 2345 6789 0123 4567 8901 2345', 'MPK_1', 'Financial Source 1', 'Project X');

CREATE TABLE AdvanceApplication
(
    id                    BIGSERIAL PRIMARY KEY,
    placeId               BIGINT        NOT NULL,
    startDate             DATE          NOT NULL,
    endDate               DATE          NOT NULL,
    residenceDietQuantity INTEGER       NOT NULL,
    residenceDietAmount   DECIMAL(7, 2) NOT NULL,
    accommodationQuantity INTEGER       NOT NULL,
    accommodationLimit    DECIMAL(7, 2) NOT NULL,
    travelDietAmount      DECIMAL(7, 2) NOT NULL,
    travelCosts           DECIMAL(7, 2) NOT NULL,
    otherCostsDescription VARCHAR(255),
    otherCostsAmount      DECIMAL(7, 2),
    residenceDietSum      DECIMAL(7, 2) NOT NULL,
    accommodationSum      DECIMAL(7, 2) NOT NULL,
    advanceSum            DECIMAL(7, 2) NOT NULL,
    CONSTRAINT place_fk FOREIGN KEY (placeId) REFERENCES Place (id)
);

-- INSERT INTO AdvanceApplication (placeId, startDate, endDate, residenceDietQuantity, residenceDietAmount,
--                                 accommodationQuantity,
--                                 accommodationLimit, travelDietAmount, travelCosts, otherCostsDescription,
--                                 otherCostsAmount, residenceDietSum, accommodationSum, advanceSum)
-- VALUES (1, '2020-12-12', '2020-12-15', 2, 200, 1, 400, 300, 50, NULL, NULL, 400, 400, 1150),
--        (4, '2020-11-10', '2020-11-13', 1, 500, 1, 500, 400, 50, NULL, NULL, 500, 500, 1450);

CREATE TABLE Application
(
    id                       BIGSERIAL PRIMARY KEY,
--     Employee Info
    firstName                varchar(255) NOT NULL,
    surname                  varchar(255) NOT NULL,
    birthDate                DATE         NOT NULL,
    academicDegree           varchar(255) NOT NULL,
    phoneNumber              varchar(255) NOT NULL,
    employeeId               BIGINT       NOT NULL,
--  Document Info
    identityDocumentType     VARCHAR(255) NOT NULL,
    identityDocumentNumber   VARCHAR(255) NOT NULL,
    createdOn                DATE         NOT NULL,
    placeId                  BIGINT       NOT NULL,
    instituteId              BIGINT       NOT NULL,
    abroadStartDate          DATE         NOT NULL,
    abroadEndDate            DATE         NOT NULL,
    purpose                  VARCHAR(255) NOT NULL,
    conference               VARCHAR(255) NOT NULL,
    subject                  VARCHAR(255) NOT NULL,
    conferenceStartDate      DATE         NOT NULL,
    conferenceEndDate        DATE         NOT NULL,
    financialSourceId        BIGINT,
    abroadStartDateInsurance DATE         NOT NULL,
    abroadEndDateInsurance   DATE         NOT NULL,
    selfInsured              BOOL         NOT NULL,
    advanceApplicationId     BIGINT       NOT NULL,
    prepaymentId             BIGINT       NOT NULL,
    comments                 VARCHAR(255),
    wildaComments            VARCHAR(255),
    directorComments         VARCHAR(255),
    rectorComments           VARCHAR(255),
    status                   VARCHAR(255) NOT NULL,
    CONSTRAINT institute_fk FOREIGN KEY (instituteId) REFERENCES Institute (id),
    CONSTRAINT employee_fk FOREIGN KEY (employeeId) REFERENCES Employee (id),
    CONSTRAINT place_fk FOREIGN KEY (placeId) REFERENCES Place (id),
    CONSTRAINT financialSource_fk FOREIGN KEY (financialSourceId) REFERENCES FinancialSource (id),
    CONSTRAINT advanceApplication_fk FOREIGN KEY (advanceApplicationId) REFERENCES AdvanceApplication (id),
    CONSTRAINT prepayment_fk FOREIGN KEY (prepaymentId) REFERENCES Prepayment (id)
);

-- INSERT INTO Application (firstName, surname, birthDate, phoneNumber, academicDegree, employeeId, identityDocumentType,
--                          identityDocumentNumber, createdOn, placeId, instituteId, abroadStartDate, abroadEndDate,
--                          purpose, conference, subject,
--                          conferenceStartDate, conferenceEndDate, financialSourceId, abroadStartDateInsurance,
--                          abroadEndDateInsurance,
--                          selfInsured, advanceApplicationId, prepaymentId, comments, wildaComments, directorComments,
--                          rectorComments, status)
-- VALUES ('Jan', 'Kowalczyk', '2000-01-01', '+48 123456789', 'Prof.', 170387, 'IdCard', 'ABC12345', '2020-11-03', 1, 1,
--         '2020-12-12',
--         '2020-12-15', 'Konferencja', 'AntyCovid2020',
--         ' TRISS: Wirtualizacja funkcjonowania Sekcji Współpracy z Zagranicą',
--         '2020-12-13', '2020-12-14', NULL, '2020-12-12', '2020-12-15', FALSE, 1, 1, NULL, NULL, NULL, NULL,
--         'WaitingForDirector'),
--        ('Jan', 'Kowalski', '1990-03-21', '+48 321456987', 'Prof.', 2, 'Passport', 'DE6789000', '2020-11-04', 4, 2,
--         '2020-11-10',
--         '2020-11-13', 'Konferencja', 'AntyCovid2020',
--         ' TRISS: Wirtualizacja funkcjonowania Sekcji Współpracy z Zagranicą',
--         '2020-11-10', '2020-11-13', 1, '2020-11-10', '2020-11-13', FALSE, 2, 2, NULL, NULL, NULL, NULL,
--         'WaitingForDirector');

CREATE TABLE Transport
(
    id              BIGSERIAL PRIMARY KEY,
    applicationID   BIGINT,
    destinationFrom VARCHAR(255) NOT NULL,
    destinationTo   VARCHAR(255) NOT NULL,
    departureDay    DATE         NOT NULL,
    departureHour   INTEGER      NOT NULL,
    departureMinute INTEGER      NOT NULL,
    carrier         VARCHAR(255) NOT NULL,
    vehicleSelect   VARCHAR(255) NOT NULL,
    CONSTRAINT application_fk FOREIGN KEY (applicationID) REFERENCES Application (id)
);

-- INSERT INTO Transport (applicationID, destinationFrom, destinationTo, departureDay, departureHour, departureMinute,
--                        vehicleSelect, carrier)
-- VALUES (1, 'Poznań', 'Los Angeles', '2020-12-12', 6, 30, 'Plane', 'LOT'),
--        (1, 'Los Angeles', 'Poznań', '2020-12-14', 20, 10, 'Plane', 'RyanAir'),
--        (2, 'Poznań', 'Montreal', '2020-11-10', 4, 24, 'Plane', 'LOT'),
--        (2, 'Montreal', 'Poznań', '2020-11-13', 5, 30, 'Plane', 'RyanAir');

CREATE VIEW ApplicationRow AS
SELECT Application.id,
       E.firstName,
       E.surname,
       employeeId,
       I.id   as instituteId,
       I.name as instituteName,
       country,
       city,
       abroadStartDate,
       abroadEndDate,
       status
FROM Application
         JOIN Place
              ON Place.id = Application.placeId
         JOIN Employee E on Application.employeeId = E.id
         JOIN Institute I on Application.instituteId = I.id;

CREATE VIEW ApplicationFull As
SELECT a.id,
       firstName,
       surname,
       birthDate,
       academicDegree,
       phoneNumber,
       employeeId,
       identityDocumentType,
       identityDocumentNumber,
       createdOn,
       a.placeId,
       instituteId,
       abroadStartDate,
       abroadEndDate,
       purpose,
       conference,
       subject,
       conferenceStartDate,
       conferenceEndDate,
       financialSourceId,
       abroadStartDateInsurance,
       abroadEndDateInsurance,
       selfInsured,
       advanceApplicationId,
       prepaymentId,
       comments,
       wildaComments,
       directorComments,
       rectorComments,
       status,
       P.id                  as pId,
       P.city                as pCity,
       P.country             as pCountry,
       i.id                  as iId,
       I.name                as iName,
       I.active              as iActive,
       F.id                  as fId,
       allocationAccount     as fAllocationAccount,
       MPK                   as fMPK,
       financialSourceValue  as fFinancialSource,
       project               as fProject,
       AA.id                 as aaId,
       AA.placeId            as aaPlaceId,
       startDate             as aaStartDate,
       endDate               as aaEndDate,
       residenceDietQuantity as aaResidenceDietQuantity,
       residenceDietAmount   as aaResidenceDietAmount,
       accommodationQuantity as aaAccommodationQuantity,
       accommodationLimit    as aaAccommodationLimit,
       travelDietAmount      as aaTravelDietAmount,
       travelCosts           as aaTravelCosts,
       otherCostsDescription as aaOtherCostsDescription,
       otherCostsAmount      as aaOtherCostsAmount,
       residenceDietSum      as aaResidenceDietSum,
       accommodationSum      as aaAccommodationSum,
       advanceSum            as aaAdvanceSum,
       PA.id                 as paId,
       PA.amount             as paAmount,
       PA.paymentType        as paPaymentType,
       PC.id                 as pcId,
       PC.amount             as pcAmount,
       PC.paymentType        as pcPaymentType
FROM APPLICATION A
         JOIN Institute I on A.instituteId = I.id
         JOIN Place P on A.placeId = P.id
         LEFT JOIN FinancialSource F on A.financialSourceId = F.id
         JOIN AdvanceApplication AA on A.advanceApplicationId = AA.id
         JOIN Prepayment PR on A.prepaymentId = PR.id
         JOIN PrepaymentFee PA on PR.accommodationFeeId = PA.id
         JOIN PrepaymentFee PC on PR.conferenceFeeId = PC.id;









