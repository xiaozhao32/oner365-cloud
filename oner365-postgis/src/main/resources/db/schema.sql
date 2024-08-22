CREATE TABLE t_position (
    id varchar(64) primary key,
    position_name varchar(32),
    postgis_type varchar(8) not null,
    position_point geometry(Point,4326),
    position_polygon geometry(Polygon,4326),
    create_time timestamp(6),
  	update_time timestamp(6)
)