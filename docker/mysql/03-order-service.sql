DROP DATABASE IF EXISTS orderdb;
DROP USER IF EXISTS 'orderadmin'@'%';
DROP USER IF EXISTS 'orderuser'@'%';

CREATE DATABASE IF NOT EXISTS orderdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'orderadmin'@'%' IDENTIFIED BY 'password';
CREATE USER 'orderuser'@'%' IDENTIFIED BY 'password';

GRANT ALL PRIVILEGES ON orderdb.* TO 'orderadmin'@'%';

GRANT SELECT, INSERT, UPDATE, DELETE, SHOW VIEW ON orderdb.* TO 'orderuser'@'%';
