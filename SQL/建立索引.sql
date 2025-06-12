--对成绩表按属性score建立降序索引
CREATE INDEX lz_index_score ON liuz_stu_grade01(lz_score01 DESC);

--对成绩表按属性学生姓名建立降序索引
CREATE INDEX lz_index_score ON liuz_stu_grade01(lz_score01 DESC);

--对成绩表按属性教师编号Tno建立索引
CREATE INDEX lz_index_gradeTno ON liuz_stu_grade01(lz_tno01);

--对学生表按属性学生姓名Sname建立索引
CREATE INDEX lz_index_stuName ON liuz_student01(lz_sname01);

--对课程表按属性开设学年和开设学期两个属性建立复合降序索引
CREATE INDEX lz_index_couTime ON liuz_course01(lz_coyear01 DESC,lz_coterm01 DESC);

--为毕业学生表中以属性学生编号创建唯一索引
CREATE UNIQUE INDEX lz_index_gradSno ON liuz_gradstudent01(lz_grad_Sno01 ASC);