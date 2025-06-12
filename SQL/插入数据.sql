INSERT INTO liuz_college01
VALUES('xueyuan01','计算机',0);

INSERT INTO liuz_college01
VALUES('xueyuan02','机械',0);

INSERT INTO liuz_college01
VALUES('xueyuan03','信息',0);

INSERT INTO liuz_college01
VALUES('xueyuan04','人文',0);

--插入专业
INSERT INTO liuz_major01(lz_mno01,lz_mname01,lz_cno01)
VALUES('jisuanji01','计科','xueyuan01');

INSERT INTO liuz_major01(lz_mno01,lz_mname01,lz_cno01)
VALUES('jisuanji02','软工','xueyuan01');

INSERT INTO liuz_major01(lz_mno01,lz_mname01,lz_cno01)
VALUES('jisuanji03','数媒','xueyuan01');

INSERT INTO liuz_major01(lz_mno01,lz_mname01,lz_cno01)
VALUES('jixie01','车辆','xueyuan02');

INSERT INTO liuz_major01(lz_mno01,lz_mname01,lz_cno01)
VALUES('jixie02','机器人','xueyuan02');

INSERT INTO liuz_major01(lz_mno01,lz_mname01,lz_cno01)
VALUES('xinxi01','通信','xueyuan03');
INSERT INTO liuz_major01(lz_mno01,lz_mname01,lz_cno01)
VALUES('xinxi02','自动化','xueyuan03');

--新建教师角色表
INSERT INTO liuz_role01(lz_rno01,lz_rname01)
VALUES('1','普通教师');
INSERT INTO liuz_role01(lz_rno01,lz_rname01)
VALUES('2','院级管理员');
INSERT INTO liuz_role01(lz_rno01,lz_rname01)
VALUES('3','校级管理员');
INSERT INTO liuz_role01(lz_rno01,lz_rname01)
VALUES('4','系统管理员');

--新建教师
INSERT INTO liuz_teacher01(lz_tno01,lz_tname01,lz_tsex01,lz_tage01,lz_title01,lz_tphone01,lz_cno01,lz_rno01)
VALUES('t01','刘老师','男',37,'教授','123456','xueyuan01','2');

INSERT INTO liuz_teacher01(lz_tno01,lz_tname01,lz_tsex01,lz_tage01,lz_title01,lz_tphone01,lz_cno01,lz_rno01)
VALUES('t02','宋老师','男',37,'教授','123456','xueyuan02','3');

INSERT INTO liuz_teacher01(lz_tno01,lz_tname01,lz_tsex01,lz_tage01,lz_title01,lz_tphone01,lz_cno01,lz_rno01)
VALUES('t03','沈老师','男',37,'教授','123456','xueyuan03','1');

INSERT INTO liuz_teacher01(lz_tno01,lz_tname01,lz_tsex01,lz_tage01,lz_title01,lz_tphone01,lz_cno01,lz_rno01)
VALUES('t04','袁老师','男',37,'教授','123456','xueyuan01','4');


--新建班级
INSERT INTO liuz_class01(lz_clno01,lz_clname01,lz_tno01,lz_mno01)
VALUES('jike01','计科1班','t01','jisuanji01');
INSERT INTO liuz_class01(lz_clno01,lz_clname01,lz_tno01,lz_mno01)
VALUES('cheliang01','车辆1班','t02','jixie01');
INSERT INTO liuz_class01(lz_clno01,lz_clname01,lz_tno01,lz_mno01)
VALUES('tongxin01','通信1班','t03','xinxi01');

--新建课程
INSERT INTO liuz_course01
VALUES('jisuanji01', '数据结构','考试',3,'2023',1,'xueyuan01');
INSERT INTO liuz_course01
VALUES('jisuanji02', '算法分析','考试',3,'2023',1,'xueyuan01');
INSERT INTO liuz_course01
VALUES('jisuanji03', '数字电路','考试',4,'2023',2,'xueyuan01');
INSERT INTO liuz_course01
VALUES('jisuanji04', '课程4','考试',2,'2023',2,'xueyuan01');
INSERT INTO liuz_course01
VALUES('jisuanji05', '课程5','考试',2,'2023',1,'xueyuan01');
INSERT INTO liuz_course01
VALUES('jisuanji06', '课程6','考试',2,'2023',2,'xueyuan01');
INSERT INTO liuz_course01
VALUES('jisuanji07', '课程7','考试',2,'2023',2,'xueyuan01');
--插入学生
INSERT INTO liuz_student01
VALUES('jike02','宋嘉涵','男',21,'北京',22.5,'jike01');
INSERT INTO liuz_student01
VALUES('jike03','章琪','男',22,'西安',21,'jike01');
INSERT INTO liuz_student01
VALUES('jike04','袁铭凯','男',23,'南京',15,'jike01');
INSERT INTO liuz_student01
VALUES('cheliang01','朱科睿','男',21,'北京',22.5,'cheliang01');

--插入教师授课表
INSERT INTO liuz_teach_course01
VALUES('jisuanji01','t01','周一3-4','广知301');
INSERT INTO liuz_teach_course01
VALUES('jisuanji02','t01','周二1-2','健行501');
INSERT INTO liuz_teach_course01
VALUES('jisuanji03','t02','周三1-2','健行501');
INSERT INTO liuz_teach_course01
VALUES('jisuanji03','t01','周三6-7','健行501');
INSERT INTO liuz_teach_course01
VALUES('jisuanji02','t02','周五8-9','健行501');
INSERT INTO liuz_teach_course01
VALUES('jisuanji04','t01','周一8-9','健行501');
INSERT INTO liuz_teach_course01
VALUES('jisuanji05','t01','周二8-9','健行501');
INSERT INTO liuz_teach_course01
VALUES('jisuanji06','t01','周三8-9','健行501');
INSERT INTO liuz_teach_course01
VALUES('jisuanji07','t01','周四8-9','健行501');
--插入成绩表
INSERT INTO liuz_stu_grade01
VALUES('jike01','jisuanji01',NULL,NULL,'t01');
INSERT INTO liuz_stu_grade01
VALUES('jike01','jisuanji02',NULL,NULL,'t01');
INSERT INTO liuz_stu_grade01
VALUES('jike01','jisuanji03',NULL,NULL,'t01');
INSERT INTO liuz_stu_grade01
VALUES('jike01','jisuanji04',NULL,NULL,'t01');
INSERT INTO liuz_stu_grade01
VALUES('jike01','jisuanji05',NULL,NULL,'t01');
