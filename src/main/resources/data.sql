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
ON DUPLICATE KEY UPDATE emoji = VALUES (emoji),content = VALUES (content),list_order = VALUES (list_order)  ;