DROP TABLE IF EXISTS ers_users;

CREATE TABLE ers_users (
	user_id NUMBER PRIMARY KEY, 
	ers_username VARCHAR(255) NOT NULL UNIQUE, -- maybe switch from NOT NULL to UNIQUE
	ers_password VARCHAR(255) NOT NULL, 
	user_first_name VARCHAR(255) NOT NULL,
	user_last_name VARCHAR(255) NOT NULL,
	user_email VARCHAR(100) NOT NULL UNIQUE, -- maybe switch from NOT NULL to UNIQUE
	user_role VARCHAR(30) NOT NULL -- maybe CHECK insted of NOT NULL so that it insures that there are differnt functions for Employee or Finance manager

);

DROP TABLE IF EXISTS ers_reimbursement;

CREATE TABLE ers_reimbursement(
	reimb_id NUMBER PRIMARY KEY, -- a Oracle data type, but its similar to NUMERIC and DECIMAL, NUMBER is both in ORACLE
	reimb_amount NUMBER NOT NULL, -- has a * in the table diagram, why?
	reimb_submitted TIMESTAMP NOT NULL, -- stored in YYYY-MM-DD hh:mm:ss format
	reimb_resolved TIMESTAMP NOT NULL,
	reimb_status VARCHAR(20) NOT NULL,
	reimb_type VARCHAR(20) NOT NULL,
	reimb_decription VARCHAR(255) NOT NULL,
	reimb_recipt BLOB NOT NULL, -- research on BLOB data type
	reimb_author NUMBER NOT NULL,
	reimb_resolver NUMBER NOT NULL
	
	CONSTRAINT fk_author FOREIGN KEY(reimb_author)
			REFERENCES ers_users(user_id)
	CONSTRAINT fk_resolver FOREIGN KEY(reimb_resolver)
			REFERENCES ers_users(user_id)		
);

SELECT * FROM ers_users;

SELECT * FROM ers reimbursement;

