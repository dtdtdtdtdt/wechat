create database wechat;

--管理员表
create table admin(
    aid int primary key auto_increment,
    aname varchar(50),
    apwd  varchar(50)
);
insert into admin(aname,apwd) values('a','a');
select * from admin where aname='a' and apwd='a'