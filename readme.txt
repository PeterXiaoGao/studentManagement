这是一个学生信息管理系统。

怎么使用：
	一、必备设备：
	1、源代码
	2、MySQL数据库
	3、jdk1.8~17.0
	4、sql文件脚本。


使用步骤：
	1、通过idea或者eclipse打开源代码。
	2、这里用idea打开来做介绍：
		在源代码src目录下resource包下有 jdbc.properties文件。
		打开能看到相关配置。如下：
		driver=com.mysql.cj.jdbc.Driver
		url=jdbc:mysql://localhost:3306/student
		user=root
		password=gao123456
		这是驱动和url和mysql账户mysql密码
这里能知道用的数据库名是student；下面开始怎么创建数据库。 (注意：sql文件存储的应该是utf8编码格式)

win+R 输入cmd ------>命令行窗口------>输入mysql -uroot -p密码----->

登录MySQL数据库成功后-------> 输入 drop database if exists student; ------> 
输入 create database student; --------------> use student;--------> set names utf8;
----------> source  C:/Users/Administrator/Desktop/studentmanagement.sql（后面跟你的sql脚本文件路径） 我的sql文件在桌面路径为： C:\Users\Administrator\Desktop\studentmanagement.sql(注意“\”替换为“/”);
--------->set names gbk;-------> select * from student; 出现下面的数据就是成功了：



			+----+-------------+----------+---------+-------------+-----------+
			| ID | STU_NO      | STU_NAME | STU_SEX | STU_COLLAGE | STU_CLASS |
			+----+-------------+----------+---------+-------------+-----------+
			|  1 | 20214005001 | 高小高   | 男      | 计信        | 21计信    |
			|  2 | 20214005002 | 李小李   | 男      | 物电        | 21自动化  |
			|  3 | 20214005003 | 王小王   | 男      | 物电        | 21自动化  |
			|  4 | 20214005004 | 赵小六   | 男      | 计信        | 21计科1   |
			|  5 | 20214005005 | 牛小飞   | 男      | 机电        | 21机器人  |
			|  6 | 20214005006 | 吴小远   | 男      | 机电        | 21机器人  |
			+----+-------------+----------+---------+-------------+-----------+
运行源代码  LoginGUI类 进行响应页面操作就行了。


根据自己喜欢的数据库名字 可以更改创建的数据库名和 resource包下配置文件的数据库名字进行操作， 注意：这两个名字要保持一致。