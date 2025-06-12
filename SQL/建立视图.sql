--视图1学生成绩查询视图
CREATE VIEW View_StuGrade 
AS 
SELECT lz_Sno01,lz_Sname01,lz_Cono01,lz_Coname01,lz_Score01,lz_Coyear01,lz_Coterm01,liuz_stu_grade01.lz_Tno01,lz_Tname01,liuz_course01.lz_Cocredit01
FROM liuz_stu_grade01 NATURAL JOIN liuz_student01 NATURAL JOIN liuz_course01 
LEFT JOIN liuz_teacher01 on liuz_stu_grade01.lz_Tno01 = liuz_teacher01.lz_Tno01;
SELECT *FROM View_StuGrade;

--建立教师任课情况查询视图
CREATE VIEW View_TeaCourse 
AS SELECT
lz_Tno01,lz_Cono01,lz_Coname01,lz_Coyear01,lz_Coterm01,lz_Teach_time01,lz_Teach_place01
FROM liuz_teach_course01 NATURAL JOIN liuz_course01 NATURAL JOIN liuz_class01;
SELECT *FROM View_TeaCourse;

--建立学院开设课程情况查询视图
CREATE VIEW View_xueyuankaike 
AS SELECT 
lz_Cno01,lz_cname01,lz_Cono01,lz_Coname01,lz_Teach_time01,
		lz_Teach_place01,lz_Coyear01,lz_Coterm01
FROM liuz_teach_course01 NATURAL JOIN liuz_course01 NATURAL JOIN liuz_college01;

SELECT *FROM View_xueyuankaike
