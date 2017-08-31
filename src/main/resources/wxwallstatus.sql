

--微信墙状态控制
--0 默认关闭微信墙功能
drop table wxwallstatus
create table wxwallstatus(
	wid int primary key auto_increment,
	status int default '0'
)

insert into wxwallstatus(status) values(1)

select * from wxwallstatus

update wxwallstatus set status= where wid = 1