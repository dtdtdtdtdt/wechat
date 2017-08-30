create database wechat;
drop table admin;
--管理员表
create table admin(
    aid int primary key auto_increment,
    aname varchar(50),
    apwd  varchar(50),
    atime datetime,
    role varchar(50)
);
alter table admin add role varchar(50);
update admin set role='root' where aname='x'
insert into admin(aname,apwd,atime,role) values('a','a',now(),'root');
select * from admin where role='root';

select * from admin where aname = 'a'#' and apwd = '11'
--菜单表
create table menu(
	mid int primary key auto_increment,
	menu varchar(50),
	mtitle varchar(50)
);
drop table menu;
insert into menu(menu,mtitle) values('safeTree','安全权限');
insert into menu(menu,mtitle) values('userTree','粉丝管理');
insert into menu(menu,mtitle) values('messageTree','消息管理');
insert into menu(menu,mtitle) values('sourceTree','素材管理');
insert into menu(menu,mtitle) values('menuTree','菜单管理');
insert into menu(menu,mtitle) values('smallTree','微程序');

select * from menu where mid=1
insert into role(role,menu,mtitle) values('',);
insert into role(role,menu,mtitle) select * from menu where mid=1;
--角色表
create table role(
    rid int primary key auto_increment,
    role varchar(50) ,
    menu  varchar(50),
    mtitle varchar(50)
);
drop table role;
select rid,role,group_concat(mtitle) as mtitle from role group by role order by rid;
select rid,role,group_concat(mtitle) as mtitle from role where role='admin';
insert into role(role,menu,mtitle) values('root','safeTree','安全权限');
insert into role(role,menu,mtitle) values('root','userTree','粉丝管理');
insert into role(role,menu,mtitle) values('root','messageTree','消息管理');
insert into role(role,menu,mtitle) values('root','sourceTree','素材管理');
insert into role(role,menu,mtitle) values('root','menuTree','菜单管理');
insert into role(role,menu,mtitle) values('root','smallTree','微程序');

insert into role(role,menu,mtitle) values('admin','userTree','粉丝管理');
insert into role(role,menu,mtitle) values('admin','messageTree','消息管理');
insert into role(role,menu,mtitle) values('admin','sourceTree','素材管理');
insert into role(role,menu,mtitle) values('admin','menuTree','菜单管理');
insert into role(role,menu,mtitle) values('admin','smallTree','微程序');

--用户信息表
create table users(
	uid int primary key auto_increment,
	total int,
	openid varchar(50),
    subscribe int, 
    subscribe_time int,  
    nickname varchar(20),
    sex int,
    country varchar(20),
    province varchar(20),
    city varchar(20),
    language varchar(20),
    headimgurl varchar(500),
	remark varchar(20),
	groupid int,
	tagid_list varchar(50)
)
drop table user
select * from users
insert into user(total,openid,subscribe,subscribe_time,nickname,
sex,country,province,city,language,headimgurl,remark,groupid,tagid_list) 
values(#{total},#{openid},#{subscribe},#{subscribe_time},#{nickname},#{sex},
#{country},#{province},#{city},#{language},#{headimgurl},#{remark},#{groupid},#{tagid_list})