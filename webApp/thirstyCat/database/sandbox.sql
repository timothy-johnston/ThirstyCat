SELECT * FROM drinks;

select * FROM drinks where start_time > date('2019-09-12 23:09:55');

INSERT INTO drinks (id, created_by, end_time, start_time) VALUES
(300 ,'tdog', '2019-07-30 02:55:54', '2019-07-30 02:50:54');

UPDATE drinks
SET create_date = '2019-09-12 00:00:00'
where id = 1341;

INSERT INTO drinks (id, created_by, end_time, start_time) VALUES(1261,'pi', '2019-07-30 02:55:54', '2019-07-30 02:50:54');

INSERT INTO drinks (id, created_by, end_time, start_time) VALUES(1374,'pi', '2019-09-12 01:52:54', '2019-09-12 01:50:54');
INSERT INTO drinks (id, created_by, end_time, start_time) VALUES(1375,'pi', '2019-09-12 03:52:54', '2019-09-12 03:50:54');
INSERT INTO drinks (id, created_by, end_time, start_time) VALUES(1376,'pi', '2019-09-12 06:52:54', '2019-09-12 06:50:54');
INSERT INTO drinks (id, created_by, end_time, start_time) VALUES(1377,'pi', '2019-09-12 07:52:54', '2019-09-12 07:50:54');
INSERT INTO drinks (id, created_by, end_time, start_time) VALUES(1378,'pi', '2019-09-12 09:52:54', '2019-09-12 09:50:54');
INSERT INTO drinks (id, created_by, end_time, start_time) VALUES(1379,'pi', '2019-09-12 12:52:54', '2019-09-12 12:50:54');
INSERT INTO drinks (id, created_by, end_time, start_time) VALUES(1380,'pi', '2019-09-12 13:52:54', '2019-09-12 13:50:54');
INSERT INTO drinks (id, created_by, end_time, start_time) VALUES(1381,'pi', '2019-09-12 15:52:54', '2019-09-12 15:50:54');

