create table S (sno char(2),sname varchar(10),status int(11),city varchar(10));
create table P (pno char(2),pname varchar(10),color char(1),weight int(11));
create table J (jno char(2),jname varchar(10),city varchar(10));
create table SPJ (sno char(2),pno char(2),jno char(2),qty int(11));
S(SNO,SNAME,STATUS,CITY)
P(PNO,PNAME,COLOR,WEIGHT)
J(JNO,JNAME,CITY)
SPJ(SNO,PNO,JNO,QTY)
use demo;

#代码开始

#1. 查询工程项目J2使用的各种零件的名称及其数量（按零件名称升序排列）。
select p.pname,spj.qty from p,spj where spj.jno='J2' and p.pno=spj.pno order by p.pname;

#2. 查询上海厂商供应的所有零件号码（去除重复，按零件号码升序排列）。

select distinct spj.pno from s,spj where s.city='上海' and s.sno=spj.sno order by spj.pno;

#3. 查询使用上海产的零件的工程名称（按工程名称升序排列,去除重复数据）。
select distinct j.jname from s,j,spj where s.city='上海' and j.jno=spj.jno and spj.sno=s.sno order by j.jname;

create table M (sno char(2),pno char(2),jno char(2),qty int(11));

#代码结束