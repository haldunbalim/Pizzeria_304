INSERT INTO Users
VALUES (37967366135, 'Susan', 'Avery', 'susanavery', '001-436-409-9831x72616', '(8P)wDrE)z', 'CUSTOMER');
INSERT INTO Users
VALUES (57190774585, 'Antonio', 'Obrien', 'antonioobrien', '767-177-4557', 'b+2Xsnr0))', 'CUSTOMER');
INSERT INTO Users
VALUES (87301800085, 'Kristin', 'Love', 'kristinlove', '889-973-0991', '@f1FZUyD+1', 'CUSTOMER');
-- INSERT INTO Users VALUES (20437982648, 'Abigail', 'Horn',  'abigailhorn',  '496.680.8629', 'e&24wDTrnf', 'CUSTOMER');
-- INSERT INTO Users VALUES (83686353736, 'Renee', 'Frey', 'reneefrey',  '+1-395-019-7158x50039', ')OF6tTVzu0', 'CUSTOMER');
INSERT INTO Users
VALUES (99127443217, 'Jeffrey', 'Ballard', 'jeffreyballard', '001-703-439-5413x58066', '4RdrqDWq+P'), 'CUSTOMER';
INSERT INTO Users
VALUES (30876994580, 'Justin', 'Davis', 'justindavis', '857-192-6362x9509', '_3fBiZL6h2', 'CUSTOMER');
INSERT INTO Users
VALUES (25757763306, 'Michelle', 'Parrish', 'michelleparrish', '423-722-6747x705', 'k!(5KASq(o', 'CUSTOMER');
INSERT INTO Users
VALUES (36045244890, 'Christopher', 'Blanchard', 'christopherblanchard', '343-305-6803x6669', 'wOS9*O6d1!', 'CUSTOMER');
INSERT INTO Users
VALUES (42033432168, 'Lacey', 'Green', 'laceygreen', '(099)248-0593x3769', '9Dn81OSo2_', 'CUSTOMER');
INSERT INTO Users
VALUES (11111111111, 'Alex', 'Grey', 'alexgrey', '(099)248-0593x3770', '9Dn81OSo3_', 'EMPLOYEE');
INSERT INTO Users
VALUES (22222222222, 'Alexa', 'Brown', 'alexabrown', '(099)248-0593x3771', '9Dn81OSo4_', 'EMPLOYEE');
INSERT INTO Users
VALUES (33333333333, 'Jack', 'Black', 'jackblack', '(099)248-0593x3772', '9Dn81OSo5_', 'EMPLOYEE');
INSERT INTO Users
VALUES (44444444444, 'Adam', 'Lotus', 'adamlotus', '(099)248-0593x3773', '9Dn81OSo6_', 'EMPLOYEE');
INSERT INTO Users
VALUES (55555555555, 'Alo', 'low', 'alolow', '(099)248-0593x3774', '9Dn81OSo7_', 'MANAGER');

----------------------------------------------------------------

INSERT INTO CustomerType
VALUES ('Gold', 0.45, 5, 59, '13-01-2021');
INSERT INTO CustomerType
VALUES ('Silver', 0.17, 3, 28, '05-12-2020');
INSERT INTO CustomerType
VALUES ('Bronze', 0.28, 2, 79, '15-03-2021');

------------------------------------------------------------------------------------------------

-- INSERT INTO CityPostalCode VALUES ('Vancouver', 'V5H 3Z7');
-- INSERT INTO CityPostalCode VALUES ('Vancouver', 'V5J 1A1');
INSERT INTO CityPostalCode
VALUES ('Vancouver', 'V5J 5H4');
-- INSERT INTO CityPostalCode VALUES ('Vancouver', 'V5K 0A1');
INSERT INTO CityPostalCode
VALUES ('Vancouver', 'V5K 0A2');
-- INSERT INTO CityPostalCode VALUES ('Vancouver', 'V5K 0A3');
-- INSERT INTO CityPostalCode VALUES ('Vancouver', 'V5K 0A4');
-- INSERT INTO CityPostalCode VALUES ('Toronto', 'M4V M4T');
INSERT INTO CityPostalCode
VALUES ('Toronto', 'M5R M5P');
INSERT INTO CityPostalCode
VALUES ('Toronto', 'M5N M4N');
-- INSERT INTO CityPostalCode VALUES ('Toronto', 'M4P M4R');
INSERT INTO CityPostalCode
VALUES ('Montreal', 'H1A 0A1');
-- INSERT INTO CityPostalCode VALUES ('Montreal', 'H1A 1J5');
-- INSERT INTO CityPostalCode VALUES ('Montreal', 'H1A 1L3');
-- INSERT INTO CityPostalCode VALUES ('Montreal', 'H1A 1L4');
INSERT INTO CityPostalCode
VALUES ('Montreal', 'H1A 1L5');
-- INSERT INTO CityPostalCode VALUES ('Montreal', 'H1A 1L6');
INSERT INTO CityPostalCode
VALUES ('Montreal', 'H1A 1L7');
-- INSERT INTO CityPostalCode VALUES ('Montreal', 'H1A 1L8');
-- INSERT INTO CityPostalCode VALUES ('Montreal', 'H1A 1L9');
INSERT INTO CityPostalCode
VALUES ('Montreal', 'H1A 1M1');
INSERT INTO CityPostalCode
VALUES ('Montreal', 'H1A 1R2');
-- INSERT INTO CityPostalCode VALUES ('Montreal', 'H1A 1X9');
-- INSERT INTO CityPostalCode VALUES ('Montreal', 'H1A 1Y1');
-- INSERT INTO CityPostalCode VALUES ('Montreal', 'H1A 1Y2');
INSERT INTO CityPostalCode
VALUES ('Montreal', 'H1A 2G7');

------------------------------------------------------------------------------------------------

INSERT INTO Address
VALUES ('East Colton', 110, 'H1A 1M1');
INSERT INTO Address
VALUES ('West Michelletown', 898, 'V5J 5H4');
INSERT INTO Address
VALUES ('Williammouth', 573, 'H1A 1R2');
INSERT INTO Address
VALUES ('Erikaport', 783, 'V5K 0A2');
INSERT INTO Address
VALUES ('2299', 681, 'M5N M4N');
INSERT INTO Address
VALUES ('East Fernando', 466, 'H1A 2G7');
INSERT INTO Address
VALUES ('East Sierra', 852, 'H1A 1L7');
INSERT INTO Address
VALUES ('West Prattton', 811, 'M5R M5P');
INSERT INTO Address
VALUES ('West Toddfort', 659, 'H1A 1L5');
INSERT INTO Address
VALUES ('3692', 263, 'H1A 0A1');
INSERT INTO Address
VALUES ('4490', 852, 'H1A 1L7');
INSERT INTO Address
VALUES ('9800', 852, 'H1A 1L7');

----------------------------------------------------------------

INSERT INTO Customer
VALUES (25757763306, 'Silver', 'East Colton', 110, 'H1A 1M1');
INSERT INTO Customer
VALUES (42033432168, 'Gold', 'West Michelletown', 898, 'V5J 5H4');
INSERT INTO Customer
VALUES (99127443217, 'Silver', 'Erikaport', 783, 'V5K 0A2');
INSERT INTO Customer
VALUES (30876994580, 'Silver', 'East Fernando', 466, 'H1A 2G7');
INSERT INTO Customer
VALUES (57190774585, 'Silver', 'East Fernando', 466, 'H1A 2G7');
INSERT INTO Customer
VALUES (37967366135, 'Bronze', 'East Sierra', 852, 'H1A 1L7');
INSERT INTO Customer
VALUES (36045244890, 'Gold', '2299', 681, 'M5N M4N');
-- INSERT INTO Customer VALUES (20437982648,'Bronze',  '2299', 681, 'M5N M4N');
-- INSERT INTO Customer VALUES (83686353736,'Silver', 'Williammouth', 573, 'H1A 1R2');

------------------------------------------------------------------------------------------------

INSERT INTO Deliverable
VALUES (523783, 'Margherita Thin', 38.285564306866341);
INSERT INTO Deliverable
VALUES (195660, 'Margherita Thick', 44.41972732819434);
INSERT INTO Deliverable
VALUES (776869, 'Romana Thick', 38.44730749355436);
INSERT INTO Deliverable
VALUES (875812, 'Large Pepsi', 3.7774015205219778);
INSERT INTO Deliverable
VALUES (389407, 'Vegetable Thin', 29.39716795549408);
INSERT INTO Deliverable
VALUES (663390, 'Garlic Bread', 9.932485854266442);
INSERT INTO Deliverable
VALUES (409173, 'Margherita Thin', 42.78112693735229);

-------------------------------------------------------------------
INSERT INTO PizzaType
VALUES ('Vegan', 'Mozerella', 'Spicy');
INSERT INTO PizzaType
VALUES ('Four Cheese', 'Mozerella', 'Spicy');
INSERT INTO PizzaType
VALUES ('Pepperoni', 'Parmesan', 'Spicy');
INSERT INTO PizzaType
VALUES ('Chicken Tofu', 'Parmesan', 'Extra Sauce');
INSERT INTO PizzaType
VALUES ('Spinach', 'Feta', 'Spicy');
INSERT INTO PizzaType
VALUES ('Vegetable', 'Feta', 'Normal');
INSERT INTO PizzaType
VALUES ('Mushroom', 'Parmesan', 'Extra Sauce');
INSERT INTO PizzaType
VALUES ('Hawai', 'Parmesan', 'Normal');
INSERT INTO PizzaType
VALUES ('Margherita', 'Mozerella', 'Normal');
INSERT INTO PizzaType
VALUES ('Romana', 'Mozerella', 'Extra Sauce');

-------------------------------------------------------------------

INSERT INTO Pizza
VALUES ('thin', 'Vegetable', 389407);
INSERT INTO Pizza
VALUES ('thick', 'Romana', 776869);
INSERT INTO Pizza
VALUES ('thick', 'Margherita', 195660);
INSERT INTO Pizza
VALUES ('thin', 'Margherita', 523783);
-- INSERT INTO Pizza VALUES ('super thin', 'Vegan', 409175);
-- INSERT INTO Pizza VALUES ('thin', 'Mushroom', 523789);
-- INSERT INTO Pizza VALUES ('thick', 'Chicken Tofu', 389408);
-- INSERT INTO Pizza VALUES ('thin', 'Margherita', 409173);
-- INSERT INTO Pizza VALUES ('thin', 'Mushroom', 409174);
-- INSERT INTO Pizza VALUES ('thick', 'Margherita', 195661);

------------------------------------------------------------------------------------------------

INSERT INTO VehicleModelBrand
VALUES ('Mustang', 'Ford');
INSERT INTO VehicleModelBrand
VALUES ('Chevrolet', 'Ford');
INSERT INTO VehicleModelBrand
VALUES ('Iron 883', 'Harley-Davidson');
INSERT INTO VehicleModelBrand
VALUES ('Road King', 'Harley-Davidson');
--INSERT INTO VehicleModelBrand VALUES ('Prius', 'Toyota');
INSERT INTO VehicleModelBrand
VALUES ('Supra', 'Toyota');
--INSERT INTO VehicleModelBrand VALUES ('Corolla', 'Toyota');

------------------------------------------------------------------------------------------------

INSERT INTO Vehicle
VALUES ('VWE 653', 'Mustang');
INSERT INTO Vehicle
VALUES ('FGA 870', 'Iron 883');
INSERT INTO Vehicle
VALUES ('RVZ 897', 'Mustang');
INSERT INTO Vehicle
VALUES ('FFV 166', 'Mustang');
INSERT INTO Vehicle
VALUES ('WNS 253', 'Supra');

------------------------------------------------------------------------------------------------
INSERT INTO Menu
VALUES (11, 'Vegan', 12.64);
INSERT INTO Menu
VALUES (12, 'Heaven', 19.94);
--INSERT INTO Menu VALUES (13, 'Hell Yeah', 7.09);
INSERT INTO Menu
VALUES (14, 'Gangam', 13.26);
INSERT INTO Menu
VALUES (15, 'Gotham', 4.10);
--INSERT INTO Menu VALUES (17, 'Salem', 9.54);
INSERT INTO Menu
VALUES (18, 'Jaskirat''s Special', 9.39);
--INSERT INTO Menu VALUES (19, 'Haldun''s Date', 7.98);
--INSERT INTO Menu VALUES (16, 'Sustainable', 9.05);
--INSERT INTO Menu VALUES (20, 'Gul''s Dream', 21.09);
--delete from MENU where MID=11;
--select * from BRANCH;
------------------------------------------------------------------------------------------------

INSERT INTO Branch
VALUES (101, 'Lee', 15, 'West Prattton', 811, 'M5R M5P');
INSERT INTO Branch
VALUES (102, 'Donna', 11, 'West Toddfort', 659, 'H1A 1L5');
INSERT INTO Branch
VALUES (103, 'Sandra', 18, '3692', 263, 'H1A 0A1');
INSERT INTO Branch
VALUES (104, 'Karen', 14, '4490', 852, 'H1A 1L7');
INSERT INTO Branch
VALUES (105, 'Brendan', 15, '9800', 852, 'H1A 1L7');

------------------------------------------------------------------------------------------------

INSERT INTO Orders
VALUES (75286929591, 25757763306, '04-06-2019', 'delivered');
INSERT INTO Orders
VALUES (72303304103, 25757763306, '10-12-2019', 'delivered');
INSERT INTO Orders
VALUES (73771990889, 42033432168, '06-10-2019', 'delivered');
INSERT INTO Orders
VALUES (79420102790, 36045244890, '09-03-2020', 'delivered');
INSERT INTO Orders
VALUES (32900409908, 57190774585, '02-01-2020', 'delivered');
-- INSERT INTO Orders VALUES (76335077271, 99127443217, '15-03-2020', 'delivered');
-- INSERT INTO Orders VALUES (96539053815, 87301800085, '12-02-2020', 'delivered');
-- INSERT INTO Orders VALUES (86191257638, 25757763306, '24-02-2020', 'delivered');
-- INSERT INTO Orders VALUES (42017439603, 36045244890, '01-03-2020', 'delivered');
-- INSERT INTO Orders VALUES (53536185057, 30876994580, '15-01-2020', 'delivered');

------------------------------------------------------------------------------------------------

INSERT INTO Route
VALUES ('West Prattton', 811, 'M5R M5P',
        'West Michelletown', '898', 'V5J 5H4');
INSERT INTO Route
VALUES ('West Prattton', 811, 'M5R M5P',
        'East Colton', 110, 'H1A 1M1');
INSERT INTO Route
VALUES ('West Prattton', 811, 'M5R M5P',
        'Williammouth', 573, 'H1A 1R2');
INSERT INTO Route
VALUES ('West Prattton', 811, 'M5R M5P',
        'Erikaport', 783, 'V5K 0A2');
INSERT INTO Route
VALUES ('West Prattton', 811, 'M5R M5P',
        'East Sierra', 852, 'H1A 1L7');

------------------------------------------------------------------------------------------------

INSERT INTO DriverDrivesVehicle
VALUES ('VWE 653', 11111111111);
INSERT INTO DriverDrivesVehicle
VALUES ('WNS 253', 22222222222);
INSERT INTO DriverDrivesVehicle
VALUES ('FFV 166', 33333333333);
INSERT INTO DriverDrivesVehicle
VALUES ('RVZ 897', 44444444444);
INSERT INTO DriverDrivesVehicle
VALUES ('FGA 870', 55555555555);

------------------------------------------------------------------------------------------------

INSERT INTO EmployeeWorksAtBranch
VALUES (101, 11111111111);
INSERT INTO EmployeeWorksAtBranch
VALUES (102, 22222222222);
INSERT INTO EmployeeWorksAtBranch
VALUES (103, 33333333333);
INSERT INTO EmployeeWorksAtBranch
VALUES (104, 44444444444);
INSERT INTO EmployeeWorksAtBranch
VALUES (105, 55555555555);

------------------------------------------------------------------------------------------------

INSERT INTO OrderContainsDeliverables
VALUES (75286929591, 523783, 2);
INSERT INTO OrderContainsDeliverables
VALUES (72303304103, 523783, 1);
INSERT INTO OrderContainsDeliverables
VALUES (73771990889, 776869, 3);
INSERT INTO OrderContainsDeliverables
VALUES (79420102790, 875812, 2);
INSERT INTO OrderContainsDeliverables
VALUES (32900409908, 663390, 4);
-- INSERT INTO OrderContainsDeliverables VALUES (76335077271, 409173, 1);
-- INSERT INTO OrderContainsDeliverables VALUES (96539053815, 195660, 3);
-- INSERT INTO OrderContainsDeliverables VALUES (86191257638, 195660, 4 );
-- INSERT INTO OrderContainsDeliverables VALUES (42017439603, 195660,2);
-- INSERT INTO OrderContainsDeliverables VALUES (53536185057, 195660, 2);
