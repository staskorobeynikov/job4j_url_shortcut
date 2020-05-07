create table sites(
    id serial primary key,
    name varchar(50) not null,
    login varchar(50) not null,
    password varchar(255) not null
);

create table urls(
    id serial primary key,
    address varchar(250) not null,
    code varchar(20) not null,
    count integer not null,
    site_id integer not null references sites(id)
);

