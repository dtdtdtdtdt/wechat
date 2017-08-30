

--微信点餐数据表
--菜名
--正常价格
--打折后的价格
--菜品详情    一大段文章唉  想想如何排版？以及图片显示的问题！
--首页商品封面图
--详情页 两张图片
--库存  
--备用字段
drop table wxshop
create table wxshop(
	fid int primary key auto_increment,
	name varchar(50),
	normprice numeric(8,2),
	realprice numeric(8,2),
	detail text,
	cover varchar(1000),
	detaila varchar(1000),
	detailb varchar(1000),
	stock  int ,
	exp varchar(100)
)
select * from wxshop

insert into wxshop(name,normprice,realprice,detail,cover,detaila,detailb,stock) 
values('燕麦牛奶',48.0,38.0,'燕麦牛奶','../../../wxShopPhoto/p.png','./../../wxShopPhoto/detail/p.png','./../../wxShopPhoto/detail/p.png',99)

insert into wxshop(name,normprice,realprice,detail,cover,detaila,detailb,stock) 
values('夏日西瓜汁',199.0,98.0,'夏日西瓜汁','../../../wxShopPhoto/p1.png','./../../wxShopPhoto/detail/p1.png','./../../wxShopPhoto/detail/p1.png',100)

insert into wxshop(name,normprice,realprice,detail,cover,detaila,detailb,stock) 
values('蜜汁鸡块',298.0,198.0,'蜜汁鸡块','../../../wxShopPhoto/p2.png','./../../wxShopPhoto/detail/p2.png','./../../wxShopPhoto/detail/p2.png',98)

insert into wxshop(name,normprice,realprice,detail,cover,detaila,detailb,stock) 
values('清爽雪糕',99.0,59.0,'清爽雪糕','../../../wxShopPhoto/p3.png','./../../wxShopPhoto/detail/p3.png','./../../wxShopPhoto/detail/p3.png',1024)



--微信点餐用户
--openid 微信用户
--用户名
--电话
--地址
drop table wxshopuser
create table wxshopuser(
	userid int  primary key auto_increment,
	openid varchar(50),
	name varchar(10),
	tel varchar(20),
	address varchar(50)
);
insert into wxshopuser(openid,name,tel,address) values('oeFt8wYmSWPGqc8BJKxBQDU_px7U','沪旦铭','17600004096','上海市浦东新区')
select * from wxshopuser where openid = 'oeFt8wYmSWPGqc8BJKxBQDU_px7U'
update wxshopuser set name='文先生',tel='17600004096',address='上海市嘉定区' where openid = 'oeFt8wYmSWPGqc8BJKxBQDU_px7U'
--订单表
--订单号
--用户id
--送餐地址
--电话
--订餐时间
--订单编号
--订单付款状态   未付款  已付款 默认为0 位付款
--是否确认收货状态  0未确认  1 确实  默认为 0
drop table wxshoporder
select * from wxshoporder
create table wxshoporder(
	roid varchar(200)  primary key ,
	userid int,
	ordertime date,  		
	paystatus int default '1',
	confirmstatus int default '0'
);

insert into wxshoporder(roid,userid,ordertime,confirmstatus)
	values('150916517682',1,now(),0);

update wxshoporder set confirmstatus=1 where roid = '150916517682'


--订单表的下单人号与用户表中的客户编号有主外键关系. 
alter table wxshoporder
	add constraint fk_resorder
	     foreign key(userid) references wxshopuser(userid);

--订单详情表与用户和商品关联  这样就可以查到是谁买的，买了什么哦
--订单id
--商品id
--购买的的价格 
--购买的数量
drop table wxshoporderitem
create table wxshoporderitem(
	wsid int  primary key auto_increment,
	roid  varchar(200),
	fid   int,
	dealprice numeric(8,2),
	num  int
);
insert into wxshoporderitem(roid,fid,dealprice,num) values('150916517682',1,48.0,2)




--订单和订单详情一起查  还需要商品名唉

select a.userid as userid,name,cover,dealprice,ordertime,confirmstatus,a.num as num,a.roid as roid from 
(select userid,fid,wxshoporder.roid as roid,dealprice,num,ordertime,paystatus,confirmstatus
	from wxshoporder,wxshoporderitem where wxshoporder.roid=wxshoporderitem.roid) as a
	left join wxshop
on wxshop.fid = a.fid  where userid='1'





	
	


--关联起来  
--详情表 和 订单表
alter table wxshoporderitem 
   add constraint fk_resorderitem_roid
      foreign key(roid) references wxshoporder( roid);
     
--详情表和 商品表
 alter table wxshoporderitem
   add constraint fk_tbl_res_fid
      foreign key( fid ) references wxshop( fid);
     







