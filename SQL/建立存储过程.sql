--学生加权成绩计算
CREATE OR REPLACE PROCEDURE calculate_gpa(sno VARCHAR(20),out ave FLOAT8) AS 
DECLARE
	score DECIMAL(5,0);
	credit DECIMAL(5,1);
  score_sum float8;
  credit_sum float8;
  CURSOR C IS 
    SELECT lz_Score01,lz_Cocredit01 
    FROM view_stugrade WHERE lz_Score01 is not null and lz_Sno01=sno;
BEGIN
	score_sum:=0;
	credit_sum:=0;
    OPEN C;
    LOOP
        FETCH C INTO score,credit;
        EXIT WHEN C%NOTFOUND;
				
        score_sum:=score_sum+score*credit;
				credit_sum:=credit_sum+credit;
    END LOOP;
    CLOSE C;
    ave:=score_sum/credit_sum;
END;
/

--学院管理员或教师通过输入课程与教师信息，向教师授课表中插入数据
CREATE OR REPLACE PROCEDURE add_teach_course(tno VARCHAR(20),cono VARCHAR(10),ttime VARCHAR(20),place VARCHAR(20)) AS 
	declare
    lz_Cono VARCHAR(10);
    lz_Tno VARCHAR(20);
    lz_Time VARCHAR(20);
    lz_Place VARCHAR(20);
BEGIN
    SET lz_Cono=cono;
	SET lz_Tno=tno;
	SET lz_Time=ttime;
	SET lz_Place=place;
	INSERT INTO liuz_teach_course01(lz_cono01,lz_tno01,lz_teach_time01,lz_teach_place01)
		VALUES(lz_Cono,lz_Tno,lz_Time,lz_Place);
END;
/

--学生选课
CREATE OR REPLACE PROCEDURE stu_get_courese(sno VARCHAR(20),cono VARCHAR(10),tno VARCHAR(20)) AS
DECLARE
	lz_sno VARCHAR(20);
	lz_cono VARCHAR(10);
	lz_tno VARCHAR(20);
BEGIN
	lz_sno:=sno;
	lz_cono:=cono;
	lz_tno:=tno;
	INSERT INTO liuz_stu_grade01(lz_sno01,lz_cono01,lz_tno01)
	VALUES(lz_sno,lz_cono,lz_tno);
END;
/
--教师登录输入编号查询密码--
CREATE OR REPLACE PROCEDURE login_teacher(tno  VARCHAR(20),out pass VARCHAR(20)) AS
DECLARE
	lz_tno VARCHAR(20);
	lz_password VARCHAR2(20);
BEGIN
	lz_tno:=tno;
	SELECT lz_tea_password01 INTO lz_password FROM liuz_teacheraccount01 WHERE lz_tno01 = lz_tno;
	pass:=lz_password;
END;
/
--学生登录输入编号查询密码--
CREATE OR REPLACE PROCEDURE login_student(sno  VARCHAR(20),out pass VARCHAR(20)) AS
DECLARE
	lz_sno VARCHAR(20);
	lz_password VARCHAR2(20);
BEGIN
	lz_sno:=sno;
	SELECT lz_stu_password01 INTO lz_password FROM liuz_studentaccount01 WHERE lz_sno01 = lz_sno;
	pass:=lz_password;
END;
/

--课程成绩平均分数计算
CREATE OR REPLACE PROCEDURE calcu_course_grade(cono VARCHAR(20),out ave FLOAT8) AS 
DECLARE
	score DECIMAL(5,0);
	credit DECIMAL(5,1);
  score_sum float8;
  credit_sum float8;
  CURSOR C IS 
    SELECT lz_Score01,lz_Cocredit01 
    FROM view_stugrade WHERE lz_Score01 is not null and lz_cono01=cono;
BEGIN
	score_sum:=0;
	credit_sum:=0;
    OPEN C;
    LOOP
        FETCH C INTO score,credit;
        EXIT WHEN C%NOTFOUND;
				
        score_sum:=score_sum+score*credit;
				credit_sum:=credit_sum+credit;
    END LOOP;
    CLOSE C;
    ave:=score_sum/credit_sum;
END;
/

