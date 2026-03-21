create database if not exists tourist_guide CHARACTER SET utf8mb4;

use tourist_guide;

drop table if exists attraction_tag;
drop table if exists attraction;
drop table if exists tag;
drop table if exists city;


create table city
(
    city_id int auto_increment primary key,
    name    varchar(100) unique not null
);

create table tag
(
    tag_id int auto_increment primary key,
    name   varchar(100) unique not null
);

create table attraction
(
    attraction_id int auto_increment primary key,
    name          varchar(150) not null,
    description   varchar(500),
    city_id       int not null,
    foreign key (city_id) references city (city_id)
);

create table attraction_tag
(
    attraction_id int not null,
    tag_id        int not null,
    primary key (attraction_id, tag_id),
    foreign key (attraction_id) references attraction (attraction_id) ON DELETE CASCADE,
    foreign key (tag_id) references tag (tag_id) ON DELETE RESTRICT
);


INSERT INTO city (name)
VALUES ("Grenaa"),    -- 1
       ("København"), -- 2
       ("Århus"),     -- 3
       ("Roskilde"),  -- 4
       ("Slagelse"); -- 5

INSERT INTO tag (name)
VALUES ("Cafe"),          -- 1
       ("Cats"),          -- 2
       ("Cozy"),          -- 3
       ("Sea Creatures"), -- 4
       ("Acrobatics"); -- 5

INSERT INTO attraction (name,
                        description,
                        city_id)
VALUES ("Dolphin Show", "A show with dolphins", 1), -- 1
       ("Cat Cafe", "A cafe with cats you can pet", 2); -- 2


INSERT INTO attraction_tag (attraction_id, tag_id)
VALUES (1, 4),
       (1, 5),
       (2, 1),
       (2, 2),
       (2, 3);