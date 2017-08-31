--微信墙使用用户信息
--用户id
--使用状态  默认为1 即使用微信墙
create table wxwalluser(
	wid int primary key auto_increment,
	fromUserName varchar(50),
	status int default '1'
)

select * from wxwalluser where fromUserName = 

update wxwalluser set status = where fromUserName = 

