--备份人  默认是系统
--文件名
--文件路径
--备份时间    精确到秒
--备份类型
--保留日期  --剩余时间30天
drop table datebackuprecord
create table datebackuprecord(
	did int primary key auto_increment,
	operator varchar(10) default 'system',
	filename varchar(100),
	filepath varchar(300),
	times datetime,
	`type` varchar(20),
	deadline int default '30'
)
select * from datebackuprecord where deadline=0

insert into datebackuprecord(operator,filename,filepath,times,`type`) values('my','备份','c://',now(),'结构')

update datebackuprecord set deadline = deadline-1

delete from datebackuprecord where filepath = 'C:\apache-tomcat-7.0.77\webapps\backupFile\2017\8\2017-08-25-15-41-51-s.sql'


select * from datebackuprecord order by times desc limit 2,10

