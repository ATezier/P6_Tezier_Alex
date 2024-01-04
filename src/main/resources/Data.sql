/* Setting up P6 DB */
create database p6;
use p6;

create table roles(
     RID int PRIMARY KEY AUTO_INCREMENT,
     NAME varchar(255) UNIQUE
);

create table user(
UID int PRIMARY KEY AUTO_INCREMENT,
RID int,
EMAIL varchar(255) UNIQUE,
FIRSTNAME varchar(255) NOT NULL,
LASTNAME varchar(255) NOT NULL,
PASSWORD varchar(255) NOT NULL,
AMOUNT double,
AUTH_PROVIDER varchar(255),
FOREIGN KEY (RID) REFERENCES roles(RID)
);

create table account(
AID int PRIMARY KEY AUTO_INCREMENT,
UID int,
NAME varchar(255),
CARD_TYPE varchar(31),
CARD_NUMBER varchar(31),
EXP_MONTH tinyint,
EXP_YEAR smallint,
MODIFIED_DATE DATETIME NOT NULL,
FOREIGN KEY (UID) REFERENCES user(UID)
);

create table transaction(
TID int PRIMARY KEY AUTO_INCREMENT,
PAYER int,
PAID int,
PRICE double,
DATE DATETIME NOT NULL,
LABEL varchar(255),
FOREIGN KEY (PAYER) REFERENCES user(UID),
FOREIGN KEY (PAID) REFERENCES user(UID)
);

create table buddies(
  BID int PRIMARY KEY AUTO_INCREMENT,
  UID1 int,
  UID2 int,
  FOREIGN KEY (UID1) REFERENCES  user(UID),
  FOREIGN KEY (UID2) REFERENCES  user(UID)
);

insert into user(EMAIL, FIRSTNAME, LASTNAME, PASSWORD, AMOUNT, AUTH_PROVIDER) values('test@example.com', 'test', 'tester', 'test', 1000, 'local');
insert into user(EMAIL, FIRSTNAME, LASTNAME, PASSWORD, AMOUNT, AUTH_PROVIDER) values('test1@example.com', 'test1', 'tester1', 'test', 0, 'local');

commit;
