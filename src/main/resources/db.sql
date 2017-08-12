create database wechat;

--管理员表
create table admin(
    aid int primary key auto_increment,
    aname varchar(50),
    apwd  varchar(50)
);
insert into admin(aname,apwd) values('a','a');
select * from admin where aname='a' and apwd='a';

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