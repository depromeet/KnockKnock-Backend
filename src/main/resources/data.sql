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



INSERT INTO tbl_group_background_image(id, background_image_url )
VALUES
(1,'https://i.pinimg.com/564x/2e/ba/8c/2eba8c6bc08626a0929b83347eff3b05.jpg'),
(2,'https://i.pinimg.com/474x/71/ba/4a/71ba4aae314afe6bb4d7bc9b4845d9e1.jpg'),
(3,'https://i.pinimg.com/474x/fc/bc/bd/fcbcbdc83d2b62801f731e1941e99aa9.jpg'),
(4,'https://i.pinimg.com/474x/96/f7/43/96f743918d23fd9107fa567e6926827a.jpg'),
(5,'https://i.pinimg.com/474x/7a/90/f9/7a90f9be363d08e70130a52aa2628b3d.jpg')
ON DUPLICATE KEY UPDATE background_image_url = VALUES (background_image_url);

INSERT INTO tbl_group_thumbnail(id, thumbnail_image_url )
VALUES
    (1,'https://i.pinimg.com/474x/46/01/f7/4601f773e41c094849e10288a7aec5e8.jpg'),
    (2,'https://i.pinimg.com/474x/e3/fc/f6/e3fcf6baaae5099edae7867dccd23854.jpg'),
    (3,'https://i.pinimg.com/474x/18/03/0a/18030a87d8370ab68eb276782ba9c2e1.jpg'),
    (4,'https://i.pinimg.com/474x/cb/be/20/cbbe20d4e9dc547e332c96862a58ba57.jpg'),
    (5,'https://i.pinimg.com/474x/40/e4/a0/40e4a0168183abc210bb7bde626551a6.jpg')
ON DUPLICATE KEY UPDATE thumbnail_image_url = VALUES (thumbnail_image_url);