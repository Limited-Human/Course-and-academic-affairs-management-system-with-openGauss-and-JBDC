--建立学院表--
create table liuz_College01(
	lz_Cno01 varchar(10) primary key unique,
	lz_Cname01 varchar(15) not null,
	lz_count01 decimal(5,0)default 0
);

--建立专业表
create table liuz_Major01(
	lz_Mno01 varchar(10) primary key,
	lz_Mname01 varchar(15)not null,
	lz_maj_count01 decimal(5,0) default 0,
	lz_Cno01 varchar(10),
	constraint col_major foreign key(lz_Cno01)references liuz_College01(lz_Cno01)
);

--建立教师角色表
CREATE TABLE liuz_Role01
(
lz_Rno01 VARCHAR(10) PRIMARY KEY,
lz_Rname01 VARCHAR(20)
);

--建立教师表
DROP TABLE IF EXISTS liuz_Teacher01;
CREATE TABLE liuz_Teacher01
(
	lz_Tno01 VARCHAR(20) PRIMARY KEY ,
	lz_Tname01 VARCHAR(15) NOT NULL,
	lz_Tsex01 VARCHAR(10) NOT NULL CHECK(lz_Tsex01 in ('男','女')),
	lz_Tage01 DECIMAL(2,0),
	lz_Title01 VARCHAR(10),
	lz_Tphone01 VARCHAR(20),
	lz_Cno01 VARCHAR(10),
	lz_Rno01 VARCHAR(10),
	CONSTRAINT Teacher_Role FOREIGN KEY(lz_Rno01) 
	REFERENCES liuz_Role01(lz_Rno01),
	CONSTRAINT college_Teacher FOREIGN KEY(lz_Cno01) 
	REFERENCES liuz_college01(lz_Cno01)
);

--建立教师账户表
DROP TABLE IF EXISTS liuz_TeacherAccount01;
CREATE TABLE liuz_TeacherAccount01
(
	lz_Tno01 VARCHAR(20) PRIMARY KEY NOT NULL,
	lz_tea_password01 VARCHAR(20) DEFAULT 'zjut654321',
	CONSTRAINT TeaAccount_Teacher FOREIGN KEY(lz_Tno01) 
	REFERENCES liuz_TeacherAccount01(lz_Tno01)
);

--建立班级表
DROP TABLE IF EXISTS liuz_Class01;
create table liuz_Class01(
	lz_Clno01 varchar(10) PRIMARY KEY,
	lz_Clname01 varchar(50) NOT NULL,
	lz_Tno01 varchar(20),
	lz_stuCount01 decimal(5,0)DEFAULT 0,
	lz_Mno01 varchar(10),
	Constraint Major_Class foreign key(lz_Mno01)references liuz_major01(lz_Mno01),
	Constraint Teacher_Class foreign key(lz_Tno01)references liuz_Teacher01(lz_Tno01)
);
--建立学生信息表
DROP TABLE IF EXISTS  liuz_Student01;
CREATE TABLE liuz_Student01
(
	lz_Sno01 VARCHAR(20) PRIMARY KEY NOT NULL,
	lz_Sname01 VARCHAR(15) NOT NULL,
	lz_Ssex01 VARCHAR(10) NOT NULL CHECK(lz_Ssex01 in ('男','女')),
	lz_Sage01 DECIMAL(2,0),
	lz_Saddress01 VARCHAR(50),
	lz_credit_sum01 DECIMAL(5,1),
	lz_Clno01 VARCHAR(10),
	CONSTRAINT class_Student FOREIGN KEY(lz_Clno01) REFERENCES liuz_class01(lz_Clno01)
);

--建立学生账户表
DROP TABLE IF EXISTS  liuz_StudentAccount01;
create table liuz_StudentAccount01
(
	lz_Sno01 varchar(20) primary key,
	lz_stu_password01 varchar(20) DEFAULT 'zjut123456',
	CONSTRAINT Student_StuAccount FOREIGN KEY(lz_Sno01) REFERENCES liuz_Student01(lz_Sno01)
);

--建立课程表
create table liuz_Course01(
	lz_Cono01 varchar(10)primary key,
	lz_Coname01 varchar(20)not null,
	lz_Coway01 varchar(10)not null check(lz_Coway01 in ('考查','考试')),
	lz_Cocredit01 decimal(5,1),
	lz_Coyear01 varchar(5) not null,
	lz_Coterm01 decimal(1,0) not null,
	lz_Cno01 varchar(10),
	constraint college_Course foreign key(lz_Cno01) references liuz_College01(lz_Cno01)
);

--建立毕业学生表
CREATE TABLE liuz_GradStudent01
(
	lz_grad_Sno01 VARCHAR(20) PRIMARY KEY NOT NULL,
	lz_Mno01 VARCHAR(10),
	lz_Cno01 VARCHAR(10),
	lz_Sname01 VARCHAR(20) NOT NULL,
	lz_Ssex01 VARCHAR(10) NOT NULL CHECK(lz_Ssex01 in ('男','女')),
	lz_grad_year01 DECIMAL(5,0),

	CONSTRAINT major_gradStudent FOREIGN KEY(lz_Mno01) 
	REFERENCES liuz_major01(lz_Mno01),
	CONSTRAINT college_gardStudent FOREIGN KEY(lz_Cno01) 
	REFERENCES liuz_college01(lz_Cno01)
);

--建立授课表
CREATE TABLE liuz_Teach_Course01
(
	lz_Cono01 VARCHAR(10) ,
	lz_Tno01 VARCHAR(20),
	lz_Teach_time01 VARCHAR(20),
	lz_Teach_place01 VARCHAR(20),
	CONSTRAINT course_teachCourse FOREIGN KEY(lz_Cono01) 
	REFERENCES liuz_course01(lz_Cono01),
	CONSTRAINT teacher_teachCourse FOREIGN KEY(lz_Tno01) 
	REFERENCES liuz_teacher01(lz_Tno01),
	CONSTRAINT priKey PRIMARY KEY(lz_Cono01,lz_Tno01)
);

--建立学生成绩表
CREATE TABLE liuz_Stu_Grade01
(
	lz_Sno01 VARCHAR(20),
	lz_Cono01 VARCHAR(10) ,
	lz_Score01 DECIMAL(5,0),
	lz_eva_score01 DECIMAL(5,0),
	lz_Tno01 VARCHAR(20),
	CONSTRAINT student_stuGrade FOREIGN KEY(lz_Sno01) 
	REFERENCES liuz_student01(lz_Sno01),
	CONSTRAINT course_stuGrade FOREIGN KEY(lz_Cono01) 
	REFERENCES liuz_course01(lz_Cono01),
	CONSTRAINT teacher_stuGrade FOREIGN KEY(lz_Tno01) 
	REFERENCES liuz_teacher01(lz_Tno01),
	CONSTRAINT priKey2 PRIMARY KEY(lz_Sno01,lz_Cono01,lz_Tno01)
);
