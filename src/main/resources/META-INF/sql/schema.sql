drop schema if exists doit;
create schema doit;
grant all privileges on doit.* to 'doit'@'localhost' identified by 'doit';
grant all privileges on doit.* to 'doit'@'%' identified by 'doit';

