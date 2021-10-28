create database ilewedb;

create user 'ilewe_user'@'localhost'identified by 'ilewe123'
grant all privileges on ilewedb.* to 'ilewe_user'@'localhost';
flush privileges ;