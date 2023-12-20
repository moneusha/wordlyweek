insert into writer(name,bio) values('John Doe','Famous writer of fantasy tales');
insert into writer(name,bio) values('Jane Smith','Renowned journalist and editor');
insert into writer(name,bio) values('Emily Brontë','Author of Wuthering Heights');
insert into writer(name,bio) values('Ernest Hemingway','Nobel Prize-winning author known for works like The Old Man and the Sea');


insert into magazine(title,publicationDate) values('Fantasy Tales','2023-10-05');
insert into magazine(title,publicationDate) values('Journalist Weekly','2023-09-15');
insert into magazine(title,publicationDate) values('Classic Literature Monthly','2023-10-15');
insert into magazine(title,publicationDate) values('Modern Writers Digest','2023-09-20');

insert into writer_magazine(writerId,magazineId) values(1,1);
insert into writer_magazine(writerId,magazineId) values(1,2);
insert into writer_magazine(writerId,magazineId) values(2,2);
insert into writer_magazine(writerId,magazineId) values(3,3);
insert into writer_magazine(writerId,magazineId) values(4,3);
insert into writer_magazine(writerId,magazineId) values(4,4);