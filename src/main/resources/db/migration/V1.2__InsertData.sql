insert into employee (id,name) values ('0a04cf91-22f0-4490-abf8-3c32890e8a97','Pier Bezukhov');
insert into employee (id,name) values ('19cd9852-6db3-447a-88bb-177f99f41e3d','Lolita');
insert into employee (id,name) values ('361cc4d5-b8d6-4e43-9603-aa38e72d4d46','Geyl Vinand');
insert into employee (id,name) values (uuid_generate_v4(),'Martin Iden');
insert into employee (id,name) values (uuid_generate_v4(),'Eldjernon');
insert into employee (id,name) values (uuid_generate_v4(),'Alexei Vronsky');

insert into task (id,header,description,deadline,employee_id,status) values (uuid_generate_v4(),'header1','desc1','2022-01-21','0a04cf91-22f0-4490-abf8-3c32890e8a97','NEW');
insert into task (id,header,description,deadline,employee_id,status) values (uuid_generate_v4(),'header2','desc2','2022-02-21','0a04cf91-22f0-4490-abf8-3c32890e8a97','NEW');
insert into task (id,header,description,deadline,employee_id,status) values (uuid_generate_v4(),'header3','desc3','2022-03-21','0a04cf91-22f0-4490-abf8-3c32890e8a97','NEW');
insert into task (id,header,description,deadline,employee_id,status) values (uuid_generate_v4(),'header4','desc4','2022-04-21','0a04cf91-22f0-4490-abf8-3c32890e8a97','IN_PROGRESS');
insert into task (id,header,description,deadline,employee_id,status) values (uuid_generate_v4(),'header5','desc5','2022-05-21','0a04cf91-22f0-4490-abf8-3c32890e8a97','IN_PROGRESS');
insert into task (id,header,description,deadline,employee_id,status) values (uuid_generate_v4(),'header5','desc6','2022-05-21','0a04cf91-22f0-4490-abf8-3c32890e8a97','DONE');

insert into task (id,header,description,deadline,employee_id,status) values (uuid_generate_v4(),'header1','desc1','2022-01-21','19cd9852-6db3-447a-88bb-177f99f41e3d','NEW');
insert into task (id,header,description,deadline,employee_id,status) values (uuid_generate_v4(),'header2','desc2','2022-02-21','19cd9852-6db3-447a-88bb-177f99f41e3d','NEW');
insert into task (id,header,description,deadline,employee_id,status) values (uuid_generate_v4(),'header3','desc3','2022-04-21','19cd9852-6db3-447a-88bb-177f99f41e3d','DONE');
insert into task (id,header,description,deadline,employee_id,status) values (uuid_generate_v4(),'header4','desc4','2022-05-21','19cd9852-6db3-447a-88bb-177f99f41e3d','DONE');
insert into task (id,header,description,deadline,employee_id,status) values (uuid_generate_v4(),'header5','desc5','2022-05-21','19cd9852-6db3-447a-88bb-177f99f41e3d','DONE');

insert into task (id,header,description,deadline,employee_id,status) values (uuid_generate_v4(),'header1','desc1','2022-05-21','361cc4d5-b8d6-4e43-9603-aa38e72d4d46','IN_PROGRESS');
insert into task (id,header,description,deadline,employee_id,status) values (uuid_generate_v4(),'header2','desc2','2022-08-21','361cc4d5-b8d6-4e43-9603-aa38e72d4d46','IN_PROGRESS');
insert into task (id,header,description,deadline,employee_id,status) values (uuid_generate_v4(),'header3','desc3','2022-09-21','361cc4d5-b8d6-4e43-9603-aa38e72d4d46','IN_PROGRESS');
