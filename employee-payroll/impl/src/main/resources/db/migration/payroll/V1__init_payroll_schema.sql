CREATE SCHEMA IF NOT EXISTS employee_payroll;

CREATE SEQUENCE employee_payroll.primary_sequence START WITH 10000 INCREMENT BY 1;

CREATE  TABLE employee_payroll.salary (
	salary_id            integer  NOT NULL  ,
	employee_id          integer    ,
	gross_salary         numeric(10,2)    ,
	net_salary           numeric(10,2)    ,
	CONSTRAINT salary_pkey PRIMARY KEY ( salary_id )
 );

CREATE UNIQUE INDEX unq_salary_employee_id ON employee_payroll.salary ( employee_id );

CREATE  TABLE employee_payroll.tax_class (
	tax_class_id         integer  NOT NULL  ,
	class_name           varchar(50)    ,
	CONSTRAINT tax_class_pkey PRIMARY KEY ( tax_class_id )
 );

CREATE  TABLE employee_payroll.tax_rate (
	tax_rate_id          integer  NOT NULL  ,
	tax_class_id         integer    ,
	lower_limit          numeric(10,2)    ,
	upper_limit          numeric(10,2)    ,
	rate                 numeric(5,2)    ,
	CONSTRAINT tax_rate_pkey PRIMARY KEY ( tax_rate_id )
 );

 ALTER TABLE employee_payroll.tax_rate ADD CONSTRAINT fk_tax_rate_class FOREIGN KEY ( tax_class_id ) REFERENCES employee_payroll.tax_class( tax_class_id );
