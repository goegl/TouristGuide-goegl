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