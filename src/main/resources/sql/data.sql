INSERT INTO city (name)
VALUES ("Grenaa"),    -- 1
       ("København"), -- 2
       ("Århus"),     -- 3
       ("Roskilde"),  -- 4
       ("Slagelse"); -- 5

INSERT INTO tag (name)
VALUES ("Café"),          -- 1
       ("Katte"),          -- 2
       ("Hyggeligt"),          -- 3
       ("Havdyr"), -- 4
       ("Akrobatik"); -- 5

INSERT INTO attraction (name,
                        description,
                        city_id)
VALUES ("Delfinshow", "Et show med springende delfiner", 1), -- 1
       ("Kattecafé", "En café fyldt med katte du kan nusse med", 2), -- 2
       ("Bæver Workshop", "En sammenkomst hvor vi drøfter hvad vi kan lære af den ædle canadiske bæver", 3), --3
       ("Tigercirkus", "Verdens førende fremvisning af hvordan man får meget store tigere til at hoppe igennem utroligt små huller", 4),
       ("Bettinas Vilde Vidunder", "Pop-up Fusion Café hvor man på uforståelig vis har kombineret konceptet af en kattecafé i underetagen med
        hoppende og springende havoddere på øverste etage, Japan-style. Better see it to believe it!", 5);



INSERT INTO attraction_tag (attraction_id, tag_id)
VALUES (1, 4),
       (1, 5),
       (2, 1),
       (2, 2),
       (2, 3),
       (3, 3),
       (3, 4),
       (4, 2),
       (4, 5),
       (5, 1),
       (5, 2),
       (5, 3),
       (5, 4),
       (5, 5);