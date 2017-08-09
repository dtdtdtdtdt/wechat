
--签到表






drop table sign
--用户名
--连续签到次数
--总积分
--最后修改时间（签到）
--签到历史    用来记录签到天数对应的积分
--ext预留
create table sign(
	sid int primary key auto_increment,
	fromUserName varchar(50),   		
	signCount int default '0',  	
	integration int default '0',	
	lastModifytime datetime,		
	signHistory bigint default '0',	
	ext varchar(20)		
)

select * from sign

insert into sign(fromUserName,signCount,integration,lastModifytime,signHistory)
values('oeFt8wYmSWPGqc8BJKxBQDU_px7U',1,1,now(),1)

--查找
select s.fromUserName,s.signCount,s.integration,s.lastModifytime from sign as s 
where fromUserName = 'oeFt8wYmSWPGqc8BJKxBQDU_px7U'


--更新
update sign set signCount= 2 ,integration=2,
	  lastModifytime=now(),signHistory=2 
where fromUserName ='oeFt8wYmSWPGqc8BJKxBQDU_px7U'




















