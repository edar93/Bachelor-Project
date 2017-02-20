create table gameinqueue (
owner varchar(100) primary key,
maxPlayersCount int);

create table user(
login varchar(100) primary key,
password varchar(100),
enabled int,
gameinqueue varchar(100),
FOREIGN KEY (gameinqueue) REFERENCES gameinqueue(owner)
);

/*suport scripts*/
select * from user;
select * from gameinqueue;

SET SQL_SAFE_UPDATES = 0;
update user set gameinqueue = null ;
delete from gameinqueue;
