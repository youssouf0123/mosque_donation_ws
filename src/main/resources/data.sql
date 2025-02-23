--INSERT INTO product (id, name, description, quantity, price) VALUES
INSERT INTO product (name, description, price, quantity) values 
('Sugar', '', 19.99, 10),
( 'Oil', '', 9.99, 5),
('Milk', '', 29.99, 30),
( 'Rice', '', 19.99, 10),
( 'Ciment', '', 9.99, 5),
('Bananan', '', 29.99, 30),
('Carrot', '', 19.99, 10),
('Potatoes', '', 9.99, 5),
('Plastics', '', 29.99, 30),
( 'Candy', '', 19.99, 10),
('Yogar', '', 9.99, 5),
('Tomatoes', '', 29.99, 30),
( 'Apple', '', 19.99, 10),
( 'Tire', '', 9.99, 5),
('Brown Bag', '', 29.99, 30),
('Black Bag', '', 19.99, 10),
( 'Nido', '', 9.99, 5),
('Cafe', '', 29.99, 30),
( 'Small Rice', '', 19.99, 10),
( 'Sardine', '', 9.99, 5),
('Corn Beef', '', 29.99, 30),
('Shoes', '', 19.99, 10),
('Vasiline', '', 9.99, 5),
('Bar Soap', '', 29.99, 30),
( 'Liquid Soap', '', 19.99, 10),
('Short Plastic', '', 9.99, 5),
('Light Ball', '', 29.99, 30),
( 'Cigaratte', '', 19.99, 10),
( 'Bread', '', 9.99, 5),
('Farine', '', 29.99, 30);
  
--INSERT INTO customer (firstName, lastName, phoneNum, emailAddress) VALUES
--  ('Jhon', 'Doe', '2222222', 'jhon@doe.com'),
--  ('carl', 'wicks', '4334434', 'carl@wicks.com'),
--  ('matt', 'smith', '665543', 'matt@smith.com'),
--  ('joe', 'peterson', '7765434', 'joe@peterson.com'),
--  ('mark', 'cole', '3343245', 'mark@cole.com');

-- INSERT INTO vehicule (licensePlate, make, model, vin, bodyType,customer_id) VALUES
--  ('LX0023', 'Toyota', 'Camry', 'vin000122323874','Sedan',1),
--  ('LX5633', 'Toyota', 'Corolla', 'vin000666674','Sedan',2),
--  ('LX2645', 'Nissan', 'Altima', 'vin884673874','Sedan',3),
--  ('LX8876', 'Nissan', 'Rogue', 'vin9384334','SUV',4),
--  ('LX1123', 'Honda', 'Accord', 'vin8343299','Sedan',5),
--  ('LX5566', 'Honda', 'CRV', 'vin86736744','SUV',5);
  
  INSERT INTO orders (cust_first_name, cust_last_name, cust_phone, cust_address, cust_city, cust_country, order_dt, status, amount) 
	values 
	('Daman', 'Camara', '240-491-6237', '2030 Main St', 'CodingVille', 'USA', to_date('26-10-20', 'DD-MM-RR'), 0, 59.97),
	('Bruce', 'Wayne', '222-333-7777', 'rue 514', 'Gotham', 'USA', to_date('26-10-20', 'DD-MM-RR'), 0, 59.97),
	('Kwame', 'Nkrumah', '123-456-7777', '1234 very long street name in Ghana', 'Accra', 'Ghana', to_date('08-09-20', 'DD-MM-RR'), 1, 59.97),
	('Dougoutigui', 'Bamako', '444-665-8888', 'rue 2340, Bagadadji', 'Bamako', 'Mali', to_date('08-10-20', 'DD-MM-RR'), 1, 59.97);
--
--INSERT INTO returns (return_dt, amount, order_id) 
--	values 
--	(now(), -19.99, 3),
--	(now(), -29.98, 4);
--	
--INSERT INTO expenses (expense_dt, amount, curr_user, explanation) 
--	values 
--	(now(), -19.99, 'SYSTEM', 'Store expenses');