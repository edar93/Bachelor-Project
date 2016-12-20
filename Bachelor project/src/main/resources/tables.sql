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