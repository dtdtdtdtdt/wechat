
--关键字回复设计表      不支持音乐回复！  
--关键字
--回复的内容
--媒体id  包括图片，语音，视频
--视频标题
--视频描述
--图片链接  用于图文回复
--Url点击图文跳转的链接
-- ktype 为回复的类型  是图片，文本，语音还是其他
drop table keyreply
create table keyreply(
	kid int primary key auto_increment,
	keywords varchar(100),
	content varchar(5000),
	mediaId varchar(100),
	title  varchar(30),
	description varchar(100),
	picUrl varchar(200),
	url  varchar(200),
	ktype int
)

insert into keyreply(keywords,content,mediaId,title,description,picurl,url) values('你好','嗯，你也好！') 

--
select * from keyreply order by ktype asc limit 5,10
select ktype,group_concat(keywords) as keywords from keyreply group by ktype


delete from keyreply 

select ktype,keywords,content,mediaId,title,description,picurl,url from keyreply group by ktype asc


