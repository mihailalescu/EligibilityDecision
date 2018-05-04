CREATE USER  IF NOT EXISTS 'eligibility'@'localhost';
ALTER USER 'root'@'localhost' IDENTIFIED BY 'bigsecret';
ALTER USER 'eligibility'@'localhost' IDENTIFIED BY 'bigsecret';
GRANT ALL ON *.* TO eligibility@'%' IDENTIFIED BY 'bigsecret';

CREATE DATABASE  IF NOT EXISTS cardApplication; 
USE cardApplication;

CREATE TABLE applications(
 applicationId INT NOT NULL AUTO_INCREMENT,
 firstName VARCHAR(20) NOT NULL,
 lastName VARCHAR(20) NOT NULL,
 ssn BIGINT NOT NULL,
 applicationStatus ENUM('APPROVED', 'PENDING_MANUAL', 'DENIED'),
 PRIMARY KEY (applicationId));