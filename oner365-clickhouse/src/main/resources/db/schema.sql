CREATE TABLE t_order (
  id String,
  create_time DateTime,
  order_id Int32,
  status Int32,
  user_id Int32
) engine = MergeTree() PRIMARY KEY id;
INSERT INTO t_order (id, create_time, order_id, status, user_id) VALUES ('1', '2024-12-31 00:00:00', 1, 1, 1);
INSERT INTO t_order (id, create_time, order_id, status, user_id) VALUES ('2', '2024-12-31 00:00:00', 2, 1, 2);
