

CREATE TABLE user (
    userID INT AUTO_INCREMENT PRIMARY KEY,
    accountNumber BIGINT,
    pin VARCHAR(4) NOT NULL,
    userName VARCHAR(50) NOT NULL,
    userSurname varchar(50) NoT NULL,
    userEmail VARCHAR(50),
    userPhone BIGINT,
    userBalance BIGINT CHECK(userBalance > 0), 
    userStatus BOOLEAN 
);

DELIMITER //
CREATE TRIGGER before_insert_user
BEFORE INSERT ON user
FOR EACH ROW
BEGIN
    SET NEW.accountNumber = new_function();
END //
DELIMITER ;





create table admin(
adminID int AUTO_INCREMENT primary key,
adminEmail varchar(20)not null,
pin varchar(4) not null,
userName varchar(50) not null,
adminRoll varchar(50),
transactionHitoryAllowed boolean,
atmInfo boolean,
userInfo boolean,
userStatus boolean);

create table transaction(
 transactionID INT AUTO_INCREMENT PRIMARY KEY,
 userID int,
foreign key (userID) references user(userID),
transactionType varchar(20) not null,
transactionAmount BIGINT not null, 
transactionTime TIMESTAMP not null default current_timestamp
);

create table atmInfo(
atmID int primary key,
atmNumber int not null,
atmStatus boolean,
cash int not null
);