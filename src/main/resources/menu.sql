
--菜单表设计
--两张表  一张一级菜单 一张二级菜单

--一级菜单
--类型
--type名称
--key键
--url  

drop table firstmenu
--情况表数据
delete from firstmenu
create table firstmenu(
	fid int primary key auto_increment,
	name varchar(10),
	`type` varchar(25),
	`key` varchar(20),
	url varchar(200),
	event varchar(20)
)

--二级菜单
--类型
--type名称
--key键
--url 
--关联一级菜单id
drop table secondmenu
delete from secondmenu
create table secondmenu(
	sid int primary key auto_increment,
	name varchar(10),
	`type` varchar(25),
	`key` varchar(20),
	url varchar(200),
	fid int not null,
	event varchar(20)
)

--管理两个表
alter table secondmenu
    add constraint fs_menu
       foreign key(fid) references firstmenu(fid);

select * from firstmenu where fid=9
select * from secondmenu where fid=6
select count(*) from secondmenu where fid=6

select count(*) from 
(select fid,count(*)as `count` from secondmenu group by fid)
a

select fid,count(*)as `count` from secondmenu group by fid



delete from firstmenu where fid=4



--插入click类型
insert into firstmenu(`type`,name,`key`,url) values('click','签到','11','')
--view
insert into firstmenu(`type`,name,`key`,url) values('view','view菜单','','http://campus.163.com/#/home')
--含有二级菜单的一级菜单  类型插入sub_button方便知道是否有子菜单
insert into firstmenu(`type`,name,`key`,url) values('','菜单','','')

--子菜单的创建
insert into secondmenu(`type`,name,`key`,url,fid) values('scancode_push','点击扫描','12','',3)
insert into secondmenu(`type`,name,`key`,url,fid) values('pic_sysphoto','系统拍照','13','',3)
insert into secondmenu(`type`,name,`key`,url,fid) values('pic_weixin','微信相册','14','',3)
insert into secondmenu(`type`,name,`key`,url,fid) values('location_select','发送位置','15','',3)



select f.fid,f.name,s.name,s.type,s.url from firstmenu f 
left join secondmenu s on f.fid=s.fid








