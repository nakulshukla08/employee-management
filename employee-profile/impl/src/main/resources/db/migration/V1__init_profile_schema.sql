CREATE SCHEMA IF NOT EXISTS employee_profile;

CREATE SEQUENCE employee_profile.primary_sequence START WITH 10000 INCREMENT BY 1;

CREATE  TABLE employee_profile.employee (
	employee_id          integer  NOT NULL  ,
	first_name           varchar(50)    ,
	last_name            varchar(50)    ,
	department_id        integer    ,
	CONSTRAINT employee_pkey PRIMARY KEY ( employee_id )
 );

CREATE  TABLE employee_profile.address (
	address_id           integer  NOT NULL  ,
	employee_id          integer    ,
	street               varchar(100)    ,
	city                 varchar(50)    ,
	"state"              varchar(50)    ,
	postal_code          varchar(10)    ,
	CONSTRAINT address_pkey PRIMARY KEY ( address_id )
 );

CREATE  TABLE employee_profile.department (
	department_id        integer  NOT NULL  ,
	department_name      varchar(50)    ,
	CONSTRAINT department_pkey PRIMARY KEY ( department_id )
 );

CREATE UNIQUE INDEX unq_department ON employee_profile.department ( department_name );

ALTER TABLE employee_profile.address ADD CONSTRAINT fk_address_employee FOREIGN KEY ( employee_id ) REFERENCES employee_profile.employee( employee_id );

ALTER TABLE employee_profile.employee ADD CONSTRAINT fk_employee_department FOREIGN KEY ( department_id ) REFERENCES employee_profile.department( department_id );

INSERT INTO employee_profile.department
	( department_id, department_name) VALUES ( 1, 'Engineering' );