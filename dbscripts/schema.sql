/* Password for all users is password 
 * Every student in every course MUST have an entry in the enrolled_courses table
 * */
DROP table admins;
DROP table assignments;
DROP table buildings;
DROP table courses;
DROP table enrolled_courses;
DROP table grade_weights;
DROP table users;
DROP table registrations;
DROP table pending_courses;
DROP table user_type_changes;

CREATE CACHED TABLE Assignments(
  id INTEGER IDENTITY, 
  course_id INTEGER NOT NULL,
  student_id INTEGER NOT NULL,
  name VARCHAR(80) NOT NULL,
  due_date DATE NOT NULL,
  grade_weight_id INTEGER NOT NULL,
  earned_points INTEGER NOT NULL,
  possible_points INTEGER NOT NULL		
 );

CREATE CACHED TABLE Grade_Weights (
	id INTEGER IDENTITY,
	name VARCHAR(80) NOT NULL,
  	weight INTEGER NOT NULL,
  	course_id INTEGER 
);

CREATE CACHED TABLE Courses (
  	id INTEGER IDENTITY, 
  	name VARCHAR(80) NOT NULL, 
  	professor_id INTEGER NOT NULL,
  	time VARCHAR(80) NOT NULL, 
  	course_num INTEGER NOT NULL,
	sec_num INTEGER NOT NULL,
	credits INTEGER NOT NULL,
	weekly_days VARCHAR(80) NOT NULL,
	location VARCHAR(80) NOT NULL,
	crn INTEGER NOT NULL,
	description VARCHAR(80) NOT NULL
);

CREATE CACHED TABLE Enrolled_Courses (
 	id INTEGER IDENTITY, 
 	student_id INTEGER NOT NULL, 
	professor_id INTEGER NOT NULL, 
	course_id INTEGER NOT NULL,
	grade INTEGER NOT NULL
);

CREATE CACHED TABLE Users (
	id INTEGER IDENTITY, 
	username VARCHAR(80) NOT NULL,
	name VARCHAR(80) NOT NULL,
	password VARCHAR(32) NOT NULL,
	salt VARCHAR(16) NOT NULL,
	emailAddress VARCHAR(80) NOT NULL,
	userType INTEGER NOT NULL,
	major VARCHAR(80) NOT NULL,
	commuter BOOLEAN NOT NULL,
	phoneNumber VARCHAR (20) NOT NULL,
	officeNumber VARCHAR (20) NOT NULL,
	biography VARCHAR (250) NOT NULL
);

CREATE CACHED TABLE Admins (
	id INTEGER IDENTITY, 
	username VARCHAR(80) NOT NULL,
	password VARCHAR(32) NOT NULL,
	salt VARCHAR(16) NOT NULL,
);

CREATE CACHED TABLE Buildings (
	id INTEGER IDENTITY,
	building_name VARCHAR(80) NOT NULL
);

CREATE CACHED TABLE Registrations (
	id INTEGER IDENTITY, 
	username VARCHAR(80) NOT NULL,
	email_address VARCHAR(80) NOT NULL,
	url VARCHAR(80) NOT NULL,
	expiration TIMESTAMP NOT NULL
);

CREATE CACHED TABLE Pending_Courses (
  	id INTEGER IDENTITY, 
  	course_name VARCHAR(80) NOT NULL, 
  	professor_name VARCHAR(80) NOT NULL,
  	email_address VARCHAR(80) NOT NULL,
  	time VARCHAR(80) NOT NULL, 
  	course_num INTEGER NOT NULL,
	sec_num INTEGER NOT NULL,
	credits INTEGER NOT NULL,
	weekly_days VARCHAR(80) NOT NULL,
	location VARCHAR(80) NOT NULL,
	crn INTEGER NOT NULL,
	description VARCHAR(80) NOT NULL
);

CREATE CACHED TABLE User_Type_Changes (
	id INTEGER IDENTITY,
	user_name VARCHAR(80) NOT NULL,
	email_address VARCHAR(80) NOT NULL,
	new_user_type VARCHAR(80) NOT NULL
);

/* Run this after creating all the tables*/
INSERT INTO Buildings values(NULL, 'KEC');
INSERT INTO Buildings values(NULL, 'HUM');
INSERT INTO Buildings values(NULL, 'CH');
INSERT INTO Buildings values(NULL, 'LS');
INSERT INTO Buildings values(NULL, 'GH');
INSERT INTO Buildings values(NULL, 'WOLF');
INSERT INTO Buildings values(NULL, 'GC');
INSERT INTO Buildings values(NULL, 'MKH');
INSERT INTO Buildings values(NULL, 'LIBRY');
INSERT INTO Buildings values(NULL, 'NESC');

INSERT INTO Users values(NULL,'TestStudent', 'John Smith', '075293660cfd83e0644b52d5703243cc ', '7c99cda63beb37f8', 'teststudent@whiteboard.org', 1, 'CS', true, '1111111111', 'HUM 111', 'Biography goes here');
INSERT INTO Users values(NULL,'TestProfessor', 'Bill Smith', '075293660cfd83e0644b52d5703243cc ', '7c99cda63beb37f8', 'testprofessor@whiteboard.org', 2, 'NONE', false, '1111111111', 'HUM 111', 'Biography goes here');
INSERT INTO Users values(NULL,'TestBoth', 'Ron John', '075293660cfd83e0644b52d5703243cc ', '7c99cda63beb37f8', 'testboth@whiteboard.org', 3, 'CS', true, '1111111111', 'HUM 111', 'Biography goes here');
INSERT INTO Users values(NULL,'msteppe', 'Mike Steppe', '075293660cfd83e0644b52d5703243cc ', '7c99cda63beb37f8', 'msteppe@whiteboard.org', 1, 'CS', false, '1111111111', 'HUM 111', 'Biography goes here');
INSERT INTO Users values(NULL,'mhoke', 'Mike Hoke', '075293660cfd83e0644b52d5703243cc ', '7c99cda63beb37f8', 'mhoke@whiteboard.org', 1, 'CS', true, '1111111111', 'HUM 111', 'Biography goes here');
INSERT INTO Users values(NULL,'pvo', 'Paul Vo', '075293660cfd83e0644b52d5703243cc ', '7c99cda63beb37f8', 'pvo@whiteboard.org', 1, 'CS', false, '1111111111', 'HUM 111', 'Biography goes here');
INSERT INTO Users values(NULL,'nbrady', 'Nick Brady', '075293660cfd83e0644b52d5703243cc ', '7c99cda63beb37f8', 'nbrady@whiteboard.org', 1, 'CS', true, '1111111111', 'HUM 111', 'Biography goes here');
INSERT INTO Users values(NULL,'TestStudent2', 'Noah Moses', '075293660cfd83e0644b52d5703243cc ', '7c99cda63beb37f8', 'whiteboardseniordesign@gmail.com', 1, 'CS', true, '1111111111', 'HUM 111', 'Biography goes here');

INSERT INTO Courses values(NULL, 'Calculus I', 1, '12:00-12:50', 101, 101, 4, 'MWF', 'CHM 223', 99999, 'This is a calculus I class.');
INSERT INTO Courses values(NULL, 'CPADS', 1, '1:00-2:40', 101, 101, 4, 'WF', 'CS 119', 88888, 'This is a CPADS class.');
INSERT INTO Courses values(NULL, 'Spanish I', 2, '1:00-1:50', 101, 101, 4, 'MWF', 'CHM 301', 77777, 'This is a spanish I class.');
INSERT INTO Courses values(NULL, 'Spanish II', 2, '2:00-2:50', 201, 104, 4, 'TR', 'CHM 201', 66666, 'This is a spanish II class.');

INSERT INTO Enrolled_Courses values(NULL, 0, 1, 0, 85);
INSERT INTO Enrolled_Courses values(NULL, 0, 2, 2, 100);
INSERT INTO Enrolled_Courses values(NULL, 2, 1, 1, 65);
INSERT INTO Enrolled_Courses values(NULL, 1, 1, 1, 75);
INSERT INTO Enrolled_Courses values(NULL, 3, 1, 0, 50);

INSERT INTO Assignments values(NULL, 0, 0, 'Homework 1', '2012-11-24', 0, 10, 10);
INSERT INTO Assignments values(NULL, 0, 0, 'Homework 2', '2012-11-20', 0, 9, 10);
INSERT INTO Assignments values(NULL, 0, 0, 'Homework 3', '2012-11-19', 0, 8, 10);
INSERT INTO Assignments values(NULL, 0, 0, 'Exam 1', '2012-11-22', 1, 80, 100);
INSERT INTO Assignments values(NULL, 0, 0, 'Exam 2', '2012-09-29', 1, 95, 100);
INSERT INTO Assignments values(NULL, 0, 3, 'Homework 1', '2012-09-08', 0, 7, 10);
INSERT INTO Assignments values(NULL, 0, 3, 'Homework 2', '2012-09-15', 0, 4, 10);
INSERT INTO Assignments values(NULL, 0, 3, 'Homework 3', '2012-09-22', 0, 5, 10);
INSERT INTO Assignments values(NULL, 0, 3, 'Exam 1', '2012-09-01', 1, 75, 100);
INSERT INTO Assignments values(NULL, 0, 3, 'Exam 2', '2012-09-29', 1, 85, 100);

INSERT INTO Grade_weights values(NULL, 'Homework', 20, 0);
INSERT INTO Grade_weights values(NULL, 'Exams', 80, 0);


INSERT INTO Admins values(NULL, 'TestAdmin', '075293660cfd83e0644b52d5703243cc ', '7c99cda63beb37f8');