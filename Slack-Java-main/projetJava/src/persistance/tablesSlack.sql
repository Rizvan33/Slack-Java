CREATE TABLE USER
(
    EMAIL VARCHAR(255) PRIMARY KEY,
    PSEUDO VARCHAR(255),
    PWD VARCHAR(255)
);

CREATE TABLE CHANNEL
(
    IP VARCHAR(255) PRIMARY KEY,
    TITLE VARCHAR(255),
    ID_ADMIN VARCHAR(255),
    isPrivate BOOLEAN,
    foreign key(ID_ADMIN) references USER(EMAIL)
);


create table Friends
(
	email1 varchar(255),
    email2 varchar(255),
    primary key (email1, email2),
    foreign key (email1) references USER(email),
    foreign key (email2) references USER(email)
);

create table JOINS
(
    ip varchar(255),
    email varchar(255),
    primary key (ip, email),
    foreign key (email) references USER(email),
    foreign key (ip) references CHANNEL(ip)
);

create table Message
(
 ip varchar(255),
 email varchar(255),
 date_msg datetime,
 contenu varchar(255),
 primary key (ip, email, date_msg),
 foreign key (ip) references CHANNEL(ip),
 foreign key (email) references USER(email)
);