use default;

drop table student;

create table student(
    id int, 
    name string
)
row format delimited fields terminated by '\t';

load data local inpath '/opt/module/datas/student.txt' into table student;

insert overwrite local directory '/opt/module/datas/student_result'
row format delimited fields terminated by '\t'
select * from student;