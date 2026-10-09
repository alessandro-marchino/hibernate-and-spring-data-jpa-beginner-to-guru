DROP DATABASE IF EXISTS bookdb;
DROP USER IF EXISTS 'bookadmin'@'%';
DROP USER IF EXISTS 'bookuser'@'%';

CREATE DATABASE IF NOT EXISTS bookdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'bookadmin'@'%' IDENTIFIED BY 'password';
CREATE USER 'bookuser'@'%' IDENTIFIED BY 'password';

GRANT ALL PRIVILEGES ON bookdb.* TO 'bookadmin'@'%';

GRANT SELECT, INSERT, UPDATE, DELETE, SHOW VIEW ON bookdb.* TO 'bookuser'@'%';
