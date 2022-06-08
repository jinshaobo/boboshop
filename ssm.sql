drop table if exists product_info;
drop table if exists product_type;

###管理员表
drop table if exists admin;
create table admin(
	a_id int auto_increment primary key,
	a_name varchar(20),
	a_pass varchar(200)
);
insert into admin(a_name,a_pass) values('admin','40bd001563085fc35165329ea1ff5c5ecbdbbeef');
select * from admin;



###商品类型表
drop table if exists product_type;
create table product_type(
	type_id int auto_increment primary key,
	type_name varchar(20)
);
insert into product_type(type_name) values('手机');
insert into product_type(type_name) values('电脑');
insert into product_type(type_name) values('电视');
select * from product_type;

###商品表
drop table if exists product_info;
create table product_info(
	p_id int auto_increment primary key,
	p_name varchar(20),
	p_content varchar(200),
	p_price int,
	p_image varchar(200),
	p_number int,
	type_id int,
	p_date date,
	foreign key(type_id) references product_type(type_id)
);
insert into product_info(p_name,p_content,p_price,p_image,p_number,type_id,p_date)
values('手机1','就是个手机1罢了',1000,'shouji1.jpg',100,1,'2022-06-01');
insert into product_info(p_name,p_content,p_price,p_image,p_number,type_id,p_date)
values('手机2','就是个手机2罢了',1000,'shouji2.jpg',100,1,'2022-06-01');
insert into product_info(p_name,p_content,p_price,p_image,p_number,type_id,p_date)
values('电脑1','就是个电脑1罢了',2000,'diannao1.jpg',200,2,'2022-06-02');
insert into product_info(p_name,p_content,p_price,p_image,p_number,type_id,p_date)
values('电脑2','就是个电脑2罢了',2000,'diannao2.jpg',200,2,'2022-06-02');
insert into product_info(p_name,p_content,p_price,p_image,p_number,type_id,p_date)
values('电视1','就是个电视1罢了',3000,'dianshi1.jpg',300,3,'2022-06-03');
insert into product_info(p_name,p_content,p_price,p_image,p_number,type_id,p_date)
values('电视2','就是个电视2罢了',3000,'dianshi2.jpg',300,3,'2022-06-03');
select * from product_info;


