-- create statements
 -- TODO: add user_type
CREATE TABLE Users
(
    user_id   INTEGER PRIMARY KEY,
    name      varchar(20) not null,
    surname   varchar(20) not null,
    username  varchar(40) not null UNIQUE,
    phoneNo   varchar(22),
    password  varchar(40) not null,
    user_type varchar(20)
);

-- if we want to add automatic incrementing to user_id
-- CREATE SEQUENCE user_id_seq;
-- create trigger user_bi
--     before insert on Users
--     FOR EACH ROW
--     BEGIN
--         Select user_id_seq.nextval
--             INTO :new.user_id
--         FROM dual;
--     end;


create table CityPostalCode
(
    City       Varchar(20) not null,
    PostalCode VARCHAR(15) PRIMARY KEY
);

CREATE TABLE Address
(
    ADDRESS_ID INTEGER,
    StreetName VarChar(40),
    HouseNr    INTEGER,
    PostalCode VarChar(10),
    PRIMARY KEY (ADDRESS_ID),
    FOREIGN KEY (PostalCode) references CityPostalCode (PostalCode)
);

CREATE TABLE Branch
(
    bid        INTEGER,
    Name       varchar(20),
    ADDRESS_ID INTEGER,
    PRIMARY KEY (bid),
    FOREIGN KEY (ADDRESS_ID) references Address (ADDRESS_ID)
        on delete cascade
);

create table VehicleModelBrand
(
    model varchar(20) PRIMARY KEY,
    brand varchar(20) not null
);

create table Vehicle
(
    license_plate varchar(8) PRIMARY KEY,
    model         varchar(20) not null,
    availability  char(1) default 'Y',
    bid           INTEGER,
    FOREIGN KEY (model) references VehicleModelBrand,
    FOREIGN KEY (bid) references Branch (bid)
        on delete cascade
);


CREATE TABLE Deliverable
(
    did   INTEGER,
    Name  varchar(20),
    Price number(*, 2),
    bid   INTEGER,
    PRIMARY KEY (did),
    FOREIGN KEY (bid) references Branch (bid)
        on delete cascade
);

CREATE TABLE Orders
(
    order_id    INTEGER,
    user_id     INTEGER,
    order_date  DATE,
    order_state varchar(20) default 'pending',
    PRIMARY KEY (order_id),
    FOREIGN KEY (user_id) references Users (user_id) on delete set null
);

CREATE TABLE PizzaType
(
    type   varchar(20),
    cheese varchar(15) not null,
    sauce  varchar(15) not null,
    PRIMARY KEY (type)
);

create TABLE Pizza
(
    crust Varchar(10) not null,
    type  varchar(20),
    did   INTEGER,
    PRIMARY KEY (did),
    FOREIGN KEY (type) references PizzaType on delete cascade
);

-- relations
-- customer inherits from Users
create table Customer
(
    user_id    INTEGER,
    points     INTEGER,
    ADDRESS_ID INTEGER,
    PRIMARY key (user_id),
    FOREIGN key (user_id) references Users (user_id) on delete cascade,
    FOREIGN KEY (ADDRESS_ID) references Address (ADDRESS_ID)
        on delete cascade
);

CREATE TABLE Route
(
    fromId INTEGER,
    toId   INTEGER,
    PRIMARY key (fromId, toId),
    FOREIGN KEY (fromId) references Address (ADDRESS_ID)
        on delete CASCADE,
    FOREIGN KEY (toId) references Address (ADDRESS_ID)
        on delete cascade
);

create table DriverDrivesVehicle
(
    license_plate varchar(8),
    user_id       INTEGER,
    PRIMARY KEY (license_plate, user_id),
    FOREIGN KEY (license_plate) references Vehicle (LICENSE_PLATE) on DELETE CASCADE,
    FOREIGN KEY (user_id) references Users (user_id) on DELETE CASCADE
);


create table DriverCarriesOrder
(
    license_plate varchar(8),
    user_id       INTEGER,
    order_id      INTEGER,
    PRIMARY KEY (license_plate, user_id, order_id),
    FOREIGN KEY (license_plate) references Vehicle (license_plate) on DELETE CASCADE,
    FOREIGN KEY (user_id) references Users (user_id) on DELETE CASCADE,
    FOREIGN KEY (order_id) references Orders (ORDER_ID) on DELETE CASCADE
);

CREATE table EmployeeWorksAtBranch
(
    user_id INTEGER,
    bid     INTEGER,
    PRIMARY KEY (bid, user_id),
    FOREIGN KEY (bid) references Branch (bid) on delete SET NULL,
    FOREIGN KEY (user_id) references Users (user_id) on DELETE CASCADE
);

CREATE TABLE OrderContainsDeliverables
(
    order_id INTEGER,
    did      INTEGER,
    amount   INTEGER,
    PRIMARY KEY (order_id, did),
    FOREIGN KEY (order_id) references Orders (order_id) on DELETE CASCADE,
    FOREIGN KEY (did) references Deliverable (DID) on DELETE SET NULL
);


