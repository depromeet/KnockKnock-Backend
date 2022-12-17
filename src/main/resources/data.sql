INSERT INTO tbl_group_category(id, emoji, content , list_order )
VALUES
(1,'','' ,null),
(2,'📒','스터디' , 1),
(3,'🏃','운동' , 10) ,
(4,'🧑‍💼','취업' , 20 ) ,
(5,'🖼','미라클모닝'  , 30),
(6,'📈','재테크' ,40) ,
(7,'⏰','루틴' ,50) ,
(8,'🛹','취미' ,60),
(9,'💬','잡담' ,70),
(10,'📜','명언' ,80),
(11,'🔥','이슈' ,90),
(12,'❤️‍','위로' ,100),
(13,'🎸','기타' ,110)
ON DUPLICATE KEY UPDATE emoji = VALUES (emoji),content = VALUES (content),list_order = VALUES (list_order);



INSERT INTO tbl_group_background_image(id, image_url, list_order)
VALUES
(1,'https://user-images.githubusercontent.com/13329304/207665246-9092f38a-faa3-4cec-95d7-4944ca95071d.png',100),
(2,'https://user-images.githubusercontent.com/13329304/207665248-a01c9376-bc44-404c-acf4-2c2c426e2b1b.png',200),
(3,'https://user-images.githubusercontent.com/13329304/207665251-4a1849c3-1af0-408c-8f45-43b5e0a91853.png',300),
(4,'https://user-images.githubusercontent.com/13329304/207665256-8765c7e4-bf92-414d-9750-a146e05921d8.png',400),
(5,'https://user-images.githubusercontent.com/13329304/207665261-95b4e6f0-aa99-4d94-8899-c500f8522ccc.png',500),
(6,'https://user-images.githubusercontent.com/13329304/207665266-46435184-7aec-4dba-b2ca-f215cb94398c.png',600),
(7,'https://user-images.githubusercontent.com/13329304/207665268-91c67f58-2b81-41a0-af7f-9137b642d9a6.png',700)
ON DUPLICATE KEY UPDATE image_url = VALUES (image_url),list_order = VALUES (list_order);

INSERT INTO tbl_group_thumbnail(id, image_url, list_order)
VALUES
    (1,'https://user-images.githubusercontent.com/13329304/207665687-28f6e5c8-9952-48cc-8055-29b4ad47cd06.png',100),
    (2,'https://user-images.githubusercontent.com/13329304/207665693-2510d240-7744-4b04-ad35-2ec59d25cd2f.png',200),
    (3,'https://user-images.githubusercontent.com/13329304/207665698-4c5b7a46-08fc-47bc-8041-a73d60d0f22e.png',300),
    (4,'https://user-images.githubusercontent.com/13329304/207665700-c1482bcf-decf-4c74-97bb-e65cb8c372a6.png',400),
    (5,'https://user-images.githubusercontent.com/13329304/207665701-5e940985-f612-46d0-a376-1e151368d160.png',500),
    (6,'https://user-images.githubusercontent.com/13329304/207665695-76f1cf83-ffca-4331-af3d-10716684706f.png',600)
ON DUPLICATE KEY UPDATE image_url = VALUES (image_url) , list_order = VALUES (list_order);

INSERT INTO tbl_profile_image(id, image_url, title, list_order)
VALUES
    (1,'https://user-images.githubusercontent.com/13329304/207663879-c2fd2833-5dcb-40b4-9b28-de81e7aa9d40.png','happy',100),
    (2,'https://user-images.githubusercontent.com/13329304/207663927-150dd57b-4564-478b-8bb9-eb4ed5caf942.png','disgusting',200),
    (3,'https://user-images.githubusercontent.com/13329304/207663952-191ab71f-b248-45ca-8b9a-959210817347.png','sad',300),
    (4,'https://user-images.githubusercontent.com/13329304/207663972-4bd876ea-a5b7-4504-8d83-08cb764befee.png','angry',400),
    (5,'https://user-images.githubusercontent.com/13329304/207663987-ff677fd6-6bf2-4022-838f-560e75f13567.png','sorry',500),
    (6,'https://user-images.githubusercontent.com/13329304/207664012-b0c497d4-9ba7-4436-9f4a-dc138bfc026b.png','teasing',600),
    (7,'https://user-images.githubusercontent.com/13329304/207664061-4ff9cac6-129a-43b8-8758-a3ccfea5a108.png','devil',700),
    (8,'https://user-images.githubusercontent.com/13329304/207664084-5b70a239-9a75-464d-b006-0917f276e6ba.png','sunglasses',800),
    (9,'https://user-images.githubusercontent.com/13329304/207664158-c547e4d6-8905-4e8e-a04b-1185d9f1f5d7.png','heart',900),
    (10,'https://user-images.githubusercontent.com/13329304/207664176-f4090ed1-9c45-46c0-b315-f28f6782ddf3.png','fire',1000),
    (11,'https://user-images.githubusercontent.com/13329304/207664192-f91c9508-ea3c-474e-8038-34c2da08eb77.png','party',1100),
    (12,'https://user-images.githubusercontent.com/13329304/207664212-5dfdba3b-4de9-40c4-a564-f14f6474955e.png','eye',1200)
    ON DUPLICATE KEY UPDATE image_url = VALUES (image_url),title = VALUES (title) ,list_order = VALUES (list_order) ;

INSERT INTO tbl_reaction(id, image_url, title, list_order)
VALUES
    (1,'https://user-images.githubusercontent.com/13329304/207663879-c2fd2833-5dcb-40b4-9b28-de81e7aa9d40.png','happy',100),
    (2,'https://user-images.githubusercontent.com/13329304/207663927-150dd57b-4564-478b-8bb9-eb4ed5caf942.png','disgusting',200),
    (3,'https://user-images.githubusercontent.com/13329304/207663952-191ab71f-b248-45ca-8b9a-959210817347.png','sad',300),
    (4,'https://user-images.githubusercontent.com/13329304/207663972-4bd876ea-a5b7-4504-8d83-08cb764befee.png','angry',400),
    (5,'https://user-images.githubusercontent.com/13329304/207663987-ff677fd6-6bf2-4022-838f-560e75f13567.png','sorry',500),
    (6,'https://user-images.githubusercontent.com/13329304/207664012-b0c497d4-9ba7-4436-9f4a-dc138bfc026b.png','teasing',600),
    (7,'https://user-images.githubusercontent.com/13329304/207664061-4ff9cac6-129a-43b8-8758-a3ccfea5a108.png','devil',700),
    (8,'https://user-images.githubusercontent.com/13329304/207664084-5b70a239-9a75-464d-b006-0917f276e6ba.png','sunglasses',800),
    (9,'https://user-images.githubusercontent.com/13329304/207664158-c547e4d6-8905-4e8e-a04b-1185d9f1f5d7.png','heart',900),
    (10,'https://user-images.githubusercontent.com/13329304/207664176-f4090ed1-9c45-46c0-b315-f28f6782ddf3.png','fire',1000),
    (11,'https://user-images.githubusercontent.com/13329304/207664192-f91c9508-ea3c-474e-8038-34c2da08eb77.png','party',1100),
    (12,'https://user-images.githubusercontent.com/13329304/207664212-5dfdba3b-4de9-40c4-a564-f14f6474955e.png','eye',1200)
    ON DUPLICATE KEY UPDATE image_url = VALUES (image_url) ,title = VALUES (title) ,list_order = VALUES (list_order);

INSERT INTO tbl_app_version(id, version)
VALUES
    (1,'0.0.1')
    ON DUPLICATE KEY UPDATE version = VALUES (version);