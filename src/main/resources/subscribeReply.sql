
--用于设置用户关注时的信息回复！
--为了减少重复代码  回复信息使用关键字回复的内容！
--关注回复一般只有一条信息

--回复对应的关键字
create table subscribereply(
	sid int primary key auto_increment,
	keywords varchar(10)
)

select * from subscribereply
delete from subscribereply where sid=1
insert into subscribereply(keywords) values()