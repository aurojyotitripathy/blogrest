DROP database IF EXISTS `blogrest`;
create database `blogrest`;
ALTER USER 'blogrest'@'localhost' IDENTIFIED WITH mysql_native_password BY 'Blogrest@707';
GRANT ALL PRIVILEGES ON blogrest.* TO 'blogrest'@'localhost';
FLUSH PRIVILEGES;