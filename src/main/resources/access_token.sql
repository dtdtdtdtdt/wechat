--最后更新时间
--token
drop table accesstoken
--获取access_token
create table accesstoken(
	aid int primary key auto_increment,
	lastModifytime datetime,	
	access_token varchar(200)
)
select * from accesstoken


insert into accesstoken(lastModifytime,access_token) 
values( now(),'JZg2xpzOe9IqyI7oGw31wq3N0ZJnwEgPOxnEk87dntoV2kaNDSo-tAiV4gCfFLq41ygOCQK31ySJjNtBzHhWr_a5QxhSKwZn3nNpOv6oHUuoxXip9X3gZQmZ43e3AP2ZDEBaAFAWXU' );


update accesstoken set lastModifytime = now(),access_token='JZg2xpzOe9IqyI7oGw31wq3N0ZJnwEgPOxnEk87dntoV2kaNDSo-tAiV4gCfFLq41ygOCQK31ySJjNtBzHhWr_a5QxhSKwZn3nNpOv6oHUuoxXip9X3gZQmZ43e3AP2ZDEBaAFAWXU' where aid=1;

















