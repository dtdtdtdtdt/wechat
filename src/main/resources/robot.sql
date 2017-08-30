

--创建一张用于使用机器人的表  
--存入使用机器人的人信息

--用户信息
--状态  默认1 因为已使用 状态就设置为1 不使用机器人  0 则正在使用机器人中、
drop table robot
create table robot(
	rid int primary key auto_increment,
	fromUserName varchar(50),
	status int default '1'
)

--机器人状态判断  用于管理员是否启用机器人
--1 默认开启机器人功能
drop table robotstatus
create table robotstatus(
	rid int primary key auto_increment,
	status int default '1'
)

insert into robotstatus(status) values(1);
select * from robotstatus 
update robotstatus set status = 1





