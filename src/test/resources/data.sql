//Drop table member;
CREATE TABLE member (
                               id varchar(100) NOT NULL ,
                               name varchar(100) DEFAULT NULL,
                               email varchar(100) DEFAULT NULL,
                               phoneNumber varchar(100) DEFAULT NULL
);

INSERT INTO member (id, name, email, phoneNumber)  VALUES('11', 'member1', 'member1@gmail.com', '9876543211');
