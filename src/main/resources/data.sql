--INSERT INTO product (id, name, description, quantity, price) VALUES
INSERT INTO product (name, phone, donation_type, quantity) VALUES
('John Doe', '1234567890', 'Clothes', 10),
('Jane Smith', '0987654321', 'Food', 20),
('Michael Brown', '1122334455', 'Books', 15),
('Emily Johnson', '6677889900', 'Toys', 8),
('Chris Lee', '1231231234', 'Electronics', 5),
('Sarah Wilson', '3213214321', 'Furniture', 2),
('David Kim', '5556667777', 'Medical Supplies', 30),
('Laura Scott', '8889990000', 'Shoes', 12),
('James White', '4445556666', 'Food', 25),
('Olivia Davis', '7778889999', 'Clothes', 18);

  
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