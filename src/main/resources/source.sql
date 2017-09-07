--素材表
drop table article;
create table article(
	id int primary key auto_increment,
	title varchar(100),		 
	thumb_media_id  varchar(100),	 	         
	author varchar(20),		 	 
	digest varchar(500),  	         	
	show_cover_pic int,   	 
	content varchar(20000),	         	 
	content_source_url varchar(50)
);

select * from article;
--insert into article(title,thumb_media_id,author,digest,show_cover_pic,content,content_source_url) 
--values('你好','xsdsfdg','大头','这是一个特别好的活动...',1,'欢迎参与','www.baidu.com');

------------------------------------------------------------------------------------
--微信返回的素材表
drop table news_item;
create table news_item(
	nid int primary key auto_increment,
	title varchar(100),	
	author varchar(20),	
	digest varchar(500),  	         	
	content varchar(20000),	
	content_source_url varchar(50),
	thumb_media_id  varchar(100),	 	         	 	 
	show_cover_pic int,   	 
	url varchar(200),
	thumb_url varchar(200)
);

select * from news_item;


------------------------------------------------------------------------------
--添加缩略图的表
drop table picture;
create table picture(
	pid int primary key auto_increment,
	pname varchar(50),
	thumb_media_id varchar(100),
	webUrl  varchar(50),
	pdate datetime
);
--alter table picture change column filepath webUrl varchar(50);
--insert into picture(pname,thumb_media_id,filepath,pdate) values('1.jpg','scdfgbnjhmbnnvcbfg','http://localhost:8080/wechat/images/1.jpg',now());
select * from picture;



------------------------------------------------------------------------------
drop table mpnews;
create table mpnews(
	mid int primary key auto_increment,
	media_id  varchar(100)
);
select * from mpnews;

