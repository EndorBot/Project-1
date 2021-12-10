DROP TABLE IF EXISTS ers_users;
DROP TABLE IF EXISTS ers_reimbursement;

CREATE TABLE ers_users (
	user_id SERIAL PRIMARY KEY, 
	ers_username VARCHAR(255) NOT NULL UNIQUE, -- maybe switch from NOT NULL to UNIQUE
	ers_password VARCHAR(255) NOT NULL, 
	user_first_name VARCHAR(255) NOT NULL,
	user_last_name VARCHAR(255) NOT NULL,
	user_email VARCHAR(100) NOT NULL UNIQUE, -- maybe switch from NOT NULL to UNIQUE
	user_role VARCHAR(30) NOT NULL -- maybe CHECK insted of NOT NULL so that it insures that there are differnt functions for Employee or Finance manager

);

INSERT INTO ers_users (
ers_username, ers_password, user_first_name, user_last_name , user_email, user_role
)
VALUES
('Rdant','pass123','Billy','Bob', 'rdant@gmail.com', 'Finance Manager'),
('MasterChief117','Iam1337','John','Spartan', 'john343@outlook.com', 'Employee'),
('Kratos_war','password','Kratos','God', 'spartanGod@aol.com', 'Employee');

CREATE TABLE ers_reimbursement(
	reimb_id SERIAL PRIMARY KEY, -- a Oracle data type, but its similar to NUMERIC and DECIMAL, NUMBER is both in ORACLE
	reimb_amount INTEGER, -- switched NUMBER to INTEGER
	reimb_submitted TIMESTAMP, -- secretly taking in a unix epoc that will be converted into a normal time stamp later in YYYY-MM-DD hh:mm:ss format
	reimb_resolved TIMESTAMP,
	reimb_status VARCHAR(20) DEFAULT 'PENDING',
	reimb_type VARCHAR(20) NOT NULL, -- types are: Lodging, Travel, Food, or Other
	reimb_decription VARCHAR(255) NOT NULL,
	reimb_recipt BYTEA, -- research on BLOB data type
	reimb_author INTEGER,
	reimb_resolver INTEGER, 
	CONSTRAINT reimb_author_fk FOREIGN KEY (reimb_author) REFERENCES ers_users(user_id),
	CONSTRAINT reimb_resolver_fk FOREIGN KEY (reimb_resolver) REFERENCES ers_users(user_id)
);

SET timezone = 'America/New_York';

SELECT NOW():: timestamp(0); -- could use this to insert current timestamp with rounded seconds into database

SELECT extract(epoch from now());
select to_timestamp( extract(epoch from now()));
-- to_timestamp( extract(epoch from now())) will supposedly return a time stamp

INSERT INTO ers_reimbursement (
	reimb_submitted,
	reimb_status,
	reimb_type,
	reimb_decription,
	reimb_author
)
VALUES
(to_timestamp( extract(epoch from now())),'Approved', 'Travel', 'Plane Ticket to NYC', 2 ),
(to_timestamp( extract(epoch from now())),'Approved', 'Travel', 'Plane Ticket to Austin', 3),
(to_timestamp( extract(epoch from now())),'Approved', 'Travel', 'Train to Chicago',2),
(to_timestamp( extract(epoch from now())),'Denied', 'Travel', 'Train to New Jersey from NYC',3);

SELECT * FROM ers_users;

SELECT * FROM ers_reimbursement;

UPDATE ers_reimbursement 
SET
reimb_amount = 500,
reimb_status = 'Approved',
reimb_resolver = 1
WHERE reimb_id = 1;

UPDATE ers_reimbursement 
SET
reimb_amount = 0,
reimb_status = 'Denied',
reimb_resolver = 1
WHERE reimb_id = 3;

UPDATE ers_reimbursement 
SET
reimb_resolved = TO_TIMESTAMP(EXTRACT(epoch FROM NOW())),
reimb_status = 'Denied',
reimb_resolver = 1
WHERE reimb_id = 3;