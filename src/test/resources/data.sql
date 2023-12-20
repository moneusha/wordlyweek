INSERT INTO writer (name, bio)
SELECT 'John Doe', 'Famous writer of fantasy tales'
WHERE NOT EXISTS (SELECT 1 FROM writer WHERE id = 1);

INSERT INTO writer (name, bio)
SELECT 'Jane Smith', 'Renowned journalist and editor'
WHERE NOT EXISTS (SELECT 2 FROM writer WHERE id = 2);

INSERT INTO writer (name, bio)
SELECT 'Emily Brontë', 'Author of Wuthering Heights'
WHERE NOT EXISTS (SELECT 3 FROM writer WHERE id = 3);

INSERT INTO writer (name, bio)
SELECT 'Ernest Hemingway', 'Nobel Prize-winning author known for works like The Old Man and the Sea'
WHERE NOT EXISTS (SELECT 4 FROM writer WHERE id = 4);

INSERT INTO magazine (title, publicationDate)
SELECT 'Fantasy Tales', '2023-10-05'
WHERE NOT EXISTS (SELECT 1 FROM magazine WHERE id = 1);

INSERT INTO magazine (title, publicationDate)
SELECT 'Journalist Weekly', '2023-09-15'
WHERE NOT EXISTS (SELECT 2 FROM magazine WHERE id = 2);

INSERT INTO magazine (title, publicationDate)
SELECT 'Classic Literature Monthly', '2023-10-15'
WHERE NOT EXISTS (SELECT 3 FROM magazine WHERE id = 3);

INSERT INTO magazine (title, publicationDate)
SELECT 'Modern Writers Digest', '2023-09-20'
WHERE NOT EXISTS (SELECT 4 FROM magazine WHERE id = 4);

INSERT INTO writer_magazine (writerId, magazineId)
SELECT 1, 1
WHERE NOT EXISTS (SELECT 1 FROM writer_magazine WHERE writerId = 1 AND magazineId = 1);

INSERT INTO writer_magazine (writerId, magazineId)
SELECT 1, 2
WHERE NOT EXISTS (SELECT 2 FROM writer_magazine WHERE writerId = 1 AND magazineId = 2);

INSERT INTO writer_magazine (writerId, magazineId)
SELECT 2, 2
WHERE NOT EXISTS (SELECT 3 FROM writer_magazine WHERE writerId = 2 AND magazineId = 2);

INSERT INTO writer_magazine (writerId, magazineId)
SELECT 3, 3
WHERE NOT EXISTS (SELECT 4 FROM writer_magazine WHERE writerId = 3 AND magazineId = 3);

INSERT INTO writer_magazine (writerId, magazineId)
SELECT 4, 3
WHERE NOT EXISTS (SELECT 5 FROM writer_magazine WHERE writerId = 4 AND magazineId = 3);

INSERT INTO writer_magazine (writerId, magazineId)
SELECT 4, 4
WHERE NOT EXISTS (SELECT 6 FROM writer_magazine WHERE writerId = 4 AND magazineId = 4);