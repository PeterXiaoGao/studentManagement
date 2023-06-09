drop table if exists student;
drop table if exists user;
drop table if exists administrator;

create table student(
	ID int(11) not null auto_increment,
	STU_NO VARCHAR(255),
	STU_NAME VARCHAR(255),
	STU_SEX VARCHAR(255),
	STU_COLLAGE VARCHAR(255),
	STU_CLASS VARCHAR(255),
	primary key(ID)
);

create table user(
	ID int(11) not null auto_increment,
	USERNAME VARCHAR(255),
	PASSWORD VARCHAR(255),
	TELEPHONE VARCHAR(255),
	primary key(ID)
);

create table administrator(
	ID int(11) not null auto_increment,
	USERNAME VARCHAR(255),
	PASSWORD VARCHAR(255),
	TELEPHONE VARCHAR(255),
	primary key(ID)
);



INSERT INTO USER(USERNAME,PASSWORD,TELEPHONE) VALUES ('admin','1234','15138236798');
INSERT INTO USER(USERNAME,PASSWORD,TELEPHONE) VALUES ('itxiaogao','123456','15138236328');
INSERT INTO USER(USERNAME,PASSWORD,TELEPHONE) VALUES ('lisi','1111','17638135928');
INSERT INTO USER(USERNAME,PASSWORD,TELEPHONE) VALUES ('李金坡','2021400','17689320978');
commit;

INSERT INTO administrator(USERNAME,PASSWORD,TELEPHONE) VALUES ('itxiaofei','2021400','17689320978');
commit;



INSERT INTO STUDENT(STU_NO,STU_NAME,STU_SEX,STU_COLLAGE,STU_CLASS) VALUES ('20214005001','高小高','男','计信','21计信');
INSERT INTO STUDENT(STU_NO,STU_NAME,STU_SEX,STU_COLLAGE,STU_CLASS) VALUES ('20214005002','李小李','男','物电','21自动化');
INSERT INTO STUDENT(STU_NO,STU_NAME,STU_SEX,STU_COLLAGE,STU_CLASS) VALUES ('20214005003','王小王','男','物电','21自动化');
INSERT INTO STUDENT(STU_NO,STU_NAME,STU_SEX,STU_COLLAGE,STU_CLASS) VALUES ('20214005004','赵小六','男','计信','21计科1');
INSERT INTO STUDENT(STU_NO,STU_NAME,STU_SEX,STU_COLLAGE,STU_CLASS) VALUES ('20214005005','牛小飞','男','机电','21机器人');
INSERT INTO STUDENT(STU_NO,STU_NAME,STU_SEX,STU_COLLAGE,STU_CLASS) VALUES ('20214005006','吴小远','男','机电','21机器人');
commit;

