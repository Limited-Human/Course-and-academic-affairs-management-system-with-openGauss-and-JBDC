--当向student表中插入、更新或者删除数据时，更新相应的班级、专业、学院人数
CREATE OR REPLACE FUNCTION tri_stu_insert() 
RETURNS TRIGGER AS $$
DECLARE
BEGIN
IF TG_OP='INSERT' THEN
--更新班级表中的人数
	UPDATE liuz_class01
	SET lz_stuCount01=lz_stuCount01+1
	WHERE liuz_class01.lz_Clno01 =new.lz_Clno01;
--更新专业表中人数
	UPDATE liuz_major01
	SET lz_maj_Count01=lz_maj_Count01+1
	WHERE lz_Mno01 in
	(SELECT lz_Mno01 FROM liuz_class01
	WHERE liuz_class01.lz_Clno01=new.lz_Clno01);
--更新学院表中人数
	UPDATE liuz_college01
	SET lz_Count01=lz_Count01+1 
	WHERE liuz_college01.lz_Cno01 IN
		(SELECT lz_Cno01 FROM liuz_major01 WHERE lz_Mno01 In
			(SELECT lz_Mno01 FROM liuz_class01 WHERE lz_Clno01 =new.lz_Clno01));
--将新加入的学生注册到学生账户表中
	INSERT INTO liuz_studentaccount01 
	VALUES(NEW.lz_sno01,'zjut123456');
	return NEW;
END IF;
END
$$ LANGUAGE PLPGSQL;

CREATE TRIGGER tri_student_insert
AFTER INSERT ON liuz_student01 
FOR EACH ROW
EXECUTE PROCEDURE tri_stu_insert();

--学生表更新触发器
CREATE OR REPLACE FUNCTION tri_stu_update() 
RETURNS TRIGGER AS $$ DECLARE
BEGIN
	--如果更新了学号，那么重置学生账户表
	IF OLD.lz_sno01 != NEW.lz_sno01 THEN
		UPDATE liuz_studentaccount01
		SET liuz_studentaccount01.lz_sno01 = NEW.lz_sno01
		WHERE liuz_studentaccount01.lz_sno01=OLD.lz_sno01;
	END IF;
	--如果更新了班级，班级人数变化
	IF OLD.lz_clno01 != NEW.lz_clno01 THEN
	 UPDATE liuz_class01
	 SET liuz_class01.lz_stucount01=liuz_class01.lz_stucount01+1
	 WHERE liuz_class01.lz_clno01 =NEW.lz_clno01;
	 
	 UPDATE liuz_class01
	 SET liuz_class01.lz_stucount01=liuz_class01.lz_stucount01-1
	 WHERE liuz_class01.lz_clno01 =OLD.lz_clno01;
 END IF;
 
 RETURN NEW;
END
$$ LANGUAGE PLPGSQL;

CREATE TRIGGER tri_student_update
AFTER UPDATE ON liuz_student01 
FOR EACH ROW
EXECUTE PROCEDURE tri_stu_update();

--学生表删除触发器
CREATE OR REPLACE FUNCTION tri_stu_delete() 
RETURNS TRIGGER AS $$ DECLARE
BEGIN
	--更新学生账号
	DELETE liuz_studentaccount01
	WHERE liuz_studentaccount01.lz_sno01 = OLD.lz_sno01;
	--更新班级人数
  UPDATE liuz_class01
	SET liuz_class01.lz_stucount01 = liuz_class01.lz_stucount01-1
	WHERE liuz_class01.lz_clno01 = OLD.lz_clno01; 
	--更新专业表中人数
	UPDATE liuz_major01
	SET lz_maj_Count01=lz_maj_Count01-1
	WHERE lz_Mno01 in
	(SELECT lz_Mno01 FROM liuz_class01
	WHERE liuz_class01.lz_Clno01=OLD.lz_Clno01);
--更新学院表中人数
	UPDATE liuz_college01
	SET lz_Count01=lz_Count01-1 
	WHERE liuz_college01.lz_Cno01 IN
		(SELECT lz_Cno01 FROM liuz_major01 WHERE lz_Mno01 In
			(SELECT lz_Mno01 FROM liuz_class01 WHERE lz_Clno01 =OLD.lz_Clno01));
 RETURN OLD;
END
$$ LANGUAGE PLPGSQL;

CREATE TRIGGER tri_student_delete
BEFORE DELETE ON liuz_student01 
FOR EACH ROW
EXECUTE PROCEDURE tri_stu_delete();

--当向Teacher表中插入数据时，增加教师账号表的账号
CREATE OR REPLACE FUNCTION tri_teacher1()
RETURNS TRIGGER AS $$
DECLARE
BEGIN
	IF TG_OP='INSERT' THEN
		INSERT INTO liuz_teacheraccount01(lz_tno01,lz_tea_password01)
		VALUES(NEW.lz_tno01,'zjut654321');
		RETURN NEW;
	ELSIF TG_OP='UPDATE' THEN
		IF OLD.lz_tno01 != NEW.lz_tno01 THEN
			UPDATE liuz_teacheraccount01
			SET liuz_teacheraccount01.lz_tno01=NEW.lz_tno01
			WHERE liuz_teacheraccount01.lz_tno01=OLD.lz_tno01;
			RETURN NEW;
		END IF;
		
	ELSIF TG_OP = 'DELETE' THEN
		DELETE FROM liuz_teacheraccount01
		WHERE liuz_teacheraccount01.lz_tno01=OLD.lz_tno01;
		
		UPDATE liuz_class01
		SET liuz_class01.lz_tno01 =NULL
		WHERE liuz_class01.lz_tno01 =OLD.lz_tno01;
		return OLD;
	END IF;
	RETURN NEW;
END
$$ LANGUAGE PLPGSQL;


CREATE TRIGGER tri_teacher_insert
AFTER INSERT  ON liuz_teacher01
FOR EACH ROW
EXECUTE PROCEDURE tri_teacher1(); 

CREATE TRIGGER tri_teacher_update
AFTER UPDATE ON liuz_teacher01
FOR EACH ROW
EXECUTE PROCEDURE tri_teacher1(); 

CREATE TRIGGER tri_teacher_delete
AFTER DELETE ON liuz_teacher01
FOR EACH ROW
EXECUTE PROCEDURE tri_teacher1(); 

--当向成绩表插入的学生成绩大于等于60分时，自动增加学生已修学分总数
CREATE OR REPLACE FUNCTION tri_fenshu_insert()
RETURNS TRIGGER AS $$ DECLARE
BEGIN
	IF NEW.lz_Score01>=60 THEN
    UPDATE liuz_student01 
		SET lz_credit_sum01=lz_credit_sum01+
			(SELECT lz_Cocredit01 FROM liuz_course01
			 WHERE  liuz_course01.lz_Cono01=NEW.lz_Cono01)
    WHERE liuz_student01.lz_Sno01=NEW.lz_Sno01;	
  END IF;
  return old;
END
$$ LANGUAGE PLPGSQL;

CREATE TRIGGER tri_grade_insert
AFTER INSERT ON liuz_stu_grade01
FOR EACH ROW
EXECUTE PROCEDURE tri_fenshu_insert();

--当老师修改学生成绩时，学分可能需要删除或者增加
CREATE OR REPLACE FUNCTION tri_fenshu_update()
RETURNS TRIGGER AS $$
DECLARE
BEGIN
  IF (NEW.lz_Score01>=60 AND OLD.lz_Score01<60 )THEN
	  UPDATE liuz_student01 
		SET lz_credit_sum01=lz_credit_sum01+(
	    SELECT lz_Cocredit01 FROM liuz_course01
			WHERE  liuz_course01.lz_Cono01=NEW.lz_Cono01)
	  WHERE liuz_student01.lz_Sno01=NEW.lz_Sno01;	
	END IF;
	
	IF (NEW.lz_Score01<60 AND OLD.lz_Score01>=60 )THEN
		UPDATE liuz_student01 
		SET lz_credit_sum01=lz_credit_sum01-(
	    SELECT lz_Cocredit01 FROM liuz_course01
			WHERE  liuz_course01.lz_Cono01=NEW.lz_Cono01)
		WHERE liuz_student01.lz_Sno01=NEW.lz_Sno01;	
	END IF;
		 
	IF (NEW.lz_Score01>=60 AND OLD.lz_Score01 is NULL )THEN
	  UPDATE liuz_student01 
		SET lz_credit_sum01=lz_credit_sum01+(
	    SELECT lz_Cocredit01 FROM liuz_course01
			WHERE  liuz_course01.lz_Cono01=NEW.lz_Cono01)
	  WHERE liuz_student01.lz_Sno01=NEW.lz_Sno01;	
	END IF;
		 
	IF (NEW.lz_Score01 is NULL AND OLD.lz_Score01 >=60 )THEN
	  UPDATE liuz_student01 
		SET lz_credit_sum01=lz_credit_sum01-(
	    SELECT lz_Cocredit01 FROM liuz_course01
			WHERE  liuz_course01.lz_Cono01=NEW.lz_Cono01)
	  WHERE liuz_student01.lz_Sno01=NEW.lz_Sno01;	
	END IF;
  return old;
END
$$ LANGUAGE PLPGSQL;

CREATE TRIGGER tri_grade_update
AFTER UPDATE ON liuz_stu_grade01
FOR EACH ROW
EXECUTE PROCEDURE tri_fenshu_update();

--当学生学分增加时，检测是否修满学分，把其加入到毕业学生表中
CREATE OR REPLACE FUNCTION tri_xuefen_update()
RETURNS TRIGGER AS $$ DECLARE
BEGIN
	IF NEW.lz_credit_sum01>150 THEN
		INSERT INTO liuz_gradstudent01
		VALUES (NEW.lz_sno01,
END
$$ LANGUAGE PLPGSQL;

CREATE TRIGGER tri_xuefen_update
AFTER UPDATE ON liuz_student01
FOR EACH ROW
EXECUTE PROCEDURE tri_xuefen_update();


