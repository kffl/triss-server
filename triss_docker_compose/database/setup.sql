
CREATE TABLE EmployeeType(
id SERIAL PRIMARY KEY,
name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE Institute(
id SERIAL PRIMARY KEY,
name varchar(255) UNIQUE NOT NULL
);

CREATE TABLE Place(
    id SERIAL PRIMARY KEY,
    city VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL
);

CREATE TABLE PrepaymentFee(
    id SERIAL PRIMARY KEY,
    amount DECIMAL(7,2) NOT NULL,
    paymentType VARCHAR(255) NOT NULL
);

CREATE TABLE Prepayment(
    id SERIAL PRIMARY KEY,
    conferenceFeeId BIGINT,
    accommodationFeeId BIGINT,
    CONSTRAINT conferenceFee_fk FOREIGN KEY(conferenceFeeId) REFERENCES PrepaymentFee(id),
    CONSTRAINT accommodationFee_fk FOREIGN KEY(accommodationFeeId) REFERENCES PrepaymentFee(id)
);


CREATE TABLE Employee(
 id BIGINT PRIMARY KEY,
 firstName varchar(255) NOT NULL,
 surname varchar(255) NOT NULL,
 birthDate DATE NOT NULL,
 academicDegree varchar(255) NOT NULL,
 phoneNumber INTEGER NOT NULL,
 instituteID BIGINT,
 employeeTypeID BIGINT,
 CONSTRAINT institute_fk FOREIGN KEY(instituteID) REFERENCES Institute(id),
 CONSTRAINT employeeType_fk FOREIGN KEY(employeeTypeID) REFERENCES EmployeeType(id)
);


CREATE TABLE IdentityDocument(
    id SERIAL PRIMARY KEY,
    employeeID BIGINT NOT NULL,
    type VARCHAR(255) NOT NULL,
    number VARCHAR(255) NOT NULL,
    CONSTRAINT employee_fk FOREIGN KEY(employeeID) REFERENCES Employee(id)
);


CREATE TABLE FinancialSource
(
    id SERIAL PRIMARY KEY,
    allocationAccount VARCHAR(255) NOT NULL,
    MPK VARCHAR(255) NOT NULL,
    financialSource VARCHAR(255) NOT NULL,
    project VARCHAR(255) NOT NULL
);

CREATE TABLE AdvanceApplication(
    id SERIAL PRIMARY KEY,
    placeId BIGINT NOT NULL,
    startDate DATE NOT NULL,
    endDate DATE NOT NULL,
    residenceDietQuantity INTEGER NOT NULL,
    residenceDietAmount DECIMAL(7,2) NOT NULL,
    accommodationQuantity INTEGER NOT NULL,
    accommodationLimit DECIMAL(7,2) NOT NULL,
    travelDietAmount DECIMAL(7,2) NOT NULL,
    travelCosts DECIMAL(7,2) NOT NULL,
    otherCostsDescription VARCHAR(255) NOT NULL,
    otherCostsAmount DECIMAL(7,2) NOT NULL,
    advanceSum DECIMAL(7,2) NOT NULL,
    CONSTRAINT place_fk FOREIGN KEY(placeId) REFERENCES Place(id)
);

CREATE TABLE Application(
    id SERIAL PRIMARY KEY,
    employeeId BIGINT,
    createdOn DATE,
    placeId BIGINT,
    abroadStartDate DATE NOT NULL,
    abroadEndDate DATE NOT NULL,
    purpose VARCHAR(255) NOT NULL,
    conference VARCHAR(255) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    conferenceStartDate DATE,
    conferenceEndDate DATE,
    financialSourceId BIGINT,
    abroadStartDateInsurance DATE,
    abroadEndDateInsurance DATE,
    selfInsured BOOL,
    advanceRequestId BIGINT,
    prepaymentId BIGINT,
    identityDocumentID BIGINT,
    comments VARCHAR(255),
    status VARCHAR(255),
    CONSTRAINT employee_fk FOREIGN KEY(employeeId) REFERENCES Employee(id),
    CONSTRAINT place_fk FOREIGN KEY(placeId) REFERENCES Place(id),
    CONSTRAINT financialSource_fk FOREIGN KEY(financialSourceId) REFERENCES FinancialSource(id),
    CONSTRAINT advanceRequest_fk FOREIGN KEY(advanceRequestId) REFERENCES AdvanceApplication(id),
    CONSTRAINT prepayment_fk FOREIGN KEY(prepaymentId) REFERENCES Prepayment(id)
);


CREATE TABLE TRANSPORT
(
 id SERIAL PRIMARY KEY,
 applicationID BIGINT,
 destinationFrom VARCHAR(255) NOT NULL,
 destinationTo VARCHAR(255) NOT NULL,
 departureDay DATE NOT NULL,
 departureMinute INTEGER NOT NULL,
 departureHour INTEGER NOT NULL,
 carrier VARCHAR(255) NOT NULL,
 CONSTRAINT application_fk FOREIGN KEY(applicationID) REFERENCES Application(id)
);





CREATE VIEW ApplicationRow AS
SELECT Application.id, employeeId, country, city, abroadStartDate, abroadEndDate, status
FROM Application JOIN Place
ON Place.id = Application.placeId
