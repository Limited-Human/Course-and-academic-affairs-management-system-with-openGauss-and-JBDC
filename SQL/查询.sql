SELECT lz_tea_password01 FROM liuz_teacheraccount01
WHERE lz_tno01=;
SELECT * FROM liuz_student01;
UPDATE liuz_student01

--查询成绩
SELECT lz_cono01,lz_coname01,lz_sno01,lz_sname01,lz_tno01
FROM liuz_stu_grade01 NATURAL JOIN liuz_course01 NATURAL JOIN liuz_student01;

SELECT * FROM liuz_teach_Course01 
WHERE lz_sno01='jike01' AND lz_coyear01='2023';

--学生课表查询
SELECT *
FROM liuz_stu_grade01 NATURAL JOIN liuz_course01 NATURAL JOIN liuz_teach_course01;
--按学号进行学生课表查询
SELECT lz_coname01, lz_tname01,lz_teach_time01,lz_teach_place01,lz_coway01,lz_cocredit01,lz_coyear01,lz_coterm01
FROM(
	SELECT *
	FROM(
		SELECT *
		FROM (
		SELECT lz_cono01,lz_tno01
		FROM liuz_stu_grade01
		WHERE lz_sno01='jike01') NATURAL JOIN liuz_teach_course01) NATURAL JOIN liuz_course01) NATURAL JOIN liuz_teacher01;
		
SELECT lz_coname01, lz_tname01,lz_teach_time01,lz_teach_place01,lz_coway01,lz_cocredit01,lz_coyear01,lz_coterm01
FROM(
	SELECT *
	FROM(
		SELECT *
		FROM (
		SELECT lz_cono01,lz_tno01
		FROM liuz_stu_grade01
		WHERE lz_sno01='jike01') NATURAL JOIN liuz_teach_course01) NATURAL JOIN liuz_course01
		WHERE lz_coyear01='2023' AND lz_coterm01=1
		) NATURAL JOIN liuz_teacher01;
		
--学生成绩查询
SELECT lz_cono01,lz_coname01,lz_cocredit01,lz_score01,lz_tname01,lz_coyear01,lz_coterm01
FROM(
	SELECT lz_cono01,lz_coname01,lz_cocredit01,lz_score01,lz_tno01,lz_coyear01,lz_coterm01
	FROM(
		SELECT lz_cono01,lz_score01,lz_tno01 FROM liuz_stu_grade01
		WHERE lz_sno01='jike01')NATURAL JOIN liuz_course01) NATURAL JOIN liuz_teacher01
		
--学生选课查询
SELECT lz_cono01,lz_tno01,lz_tname01,lz_teach_time01,lz_teach_place01 FROM 
 liuz_teach_course01 NATURAL JOIN liuz_teacher01 NATURAL JOIN liuz_course01
WHERE lz_cono01 NOT IN (
	SELECT lz_cono01 FROM liuz_stu_grade01
	WHERE lz_sno01 = 'jike01') AND lz_coyear01='2023' AND lz_coterm01=2;

	
--更新教学评价分数
UPDATE liuz_stu_grade01 SET lz_eva_score01 = 95
WHERE lz_cono01='jisuanji01' AND lz_sno01='jike01';

--查询某位教师所教授的课程与上课学生进行打分
SELECT lz_sno01, lz_sname01,lz_cono01,lz_coname01,lz_score01,lz_coyear01,lz_coterm01
FROM(SELECT lz_cono01,lz_coname01,lz_coyear01,lz_coterm01 FROM liuz_course01)AS b NATURAL JOIN(
	SELECT lz_sno01, lz_sname01,lz_cono01,lz_score01 FROM liuz_student01 NATURAL JOIN(
		SELECT lz_sno01, lz_cono01,lz_score01 FROM liuz_stu_grade01
		WHERE lz_tno01 = 't01')AS a)
ORDER BY lz_cono01;

UPDATE liuz_teacher01 set lz_tphone01 ='12345678'
WHERE lz_tno01='t01';

--班主任查询班级成员
SELECT * FROM liuz_student01
WHERE lz_clno01 IN(
SELECT lz_clno01 FROM liuz_class01
WHERE lz_tno01='t01')
ORDER BY lz_clno01;

--班主任查询班级的成绩
SELECT lz_sno01,lz_sname01,lz_cono01,lz_coname01,lz_score01 FROM(
	SELECT lz_sno01,lz_sname01,lz_cono01,lz_score01 FROM liuz_stu_grade01 NATURAL JOIN (
	SELECT lz_sno01,lz_clno01,lz_sname01 FROM liuz_student01
	WHERE lz_clno01 IN(
		SELECT lz_clno01 FROM liuz_class01
		WHERE lz_tno01='t01'))) NATURAL JOIN liuz_course01;

--查询学院编号
SELECT lz_cno01,lz_cname01 FROM liuz_college01;
--查询系统身份
SELECT lz_rno01,lz_rname01 FROM liuz_role01;
--查询生源地学生数量
SELECT lz_saddress01,COUNT(*) AS StudentCount
FROM liuz_student01
GROUP BY lz_saddress01
ORDER BY StudentCount DESC

--查询学院
SELECT * FROM liuz_college01;
SELECT lz_sno01, lz_sname01,lz_cono01,lz_coname01,lz_score01,lz_coyear01,lz_coterm01 
	FROM(SELECT lz_cono01,lz_coname01,lz_coyear01,lz_coterm01 
		FROM liuz_course01  WHERE lz_coyear01 ='" +year+"' AND lz_coterm01 ="+term+")AS b NATURAL JOIN(SELECT lz_sno01, lz_sname01,lz_cono01,lz_score01 FROM liuz_student01 NATURAL JOIN(SELECT lz_sno01, lz_cono01,lz_score01 FROM liuz_stu_grade01 WHERE lz_tno01 = '"+TeaID+"' and lz_cono01 = '"+txtCono.getText().toString()+"')AS a) ORDER BY lz_cono01;