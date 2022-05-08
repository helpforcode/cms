-- auto-generated definition
create table article
(
    id           int auto_increment
        primary key,
    category_id  int          default 0    not null,
    title        varchar(100) default ''   not null,
    images       json                      null,
    link         varchar(200)              null,
    content      text                      null,
    display      bit          default b'1' not null,
    published_at timestamp                 not null
);

-- auto-generated definition
create table category
(
    id      int auto_increment
        primary key,
    name    varchar(20) default ''   not null,
    code    varchar(20) default ''   not null,
    display bit         default b'1' not null
);

-- auto-generated definition
create table daily_word
(
    id           int auto_increment
        primary key,
    words        varchar(50) default ''   not null,
    word_primary int                      not null,
    published_at varchar(20) default ''   not null,
    status       bit         default b'0' not null
);

-- auto-generated definition
create table menu
(
    id        int auto_increment
        primary key,
    name      varchar(20) default '' not null,
    parent_id int         default 0  not null,
    level     int         default 0  not null,
    code      varchar(20) default '' not null
);

-- auto-generated definition
create table user
(
    id       int auto_increment
        primary key,
    username varchar(20) default '' not null,
    password varchar(64) default '' not null,
    role     varchar(20) default '' not null
);

-- auto-generated definition
create table word
(
    id   int auto_increment
        primary key,
    word varchar(10) default '' not null
);

create table image
(
    id int auto_increment,
    name varchar(50) default '' not null,
    path varchar(200) default '' not null,
    url varchar(200) default '' not null,
    size bigint default 0 not null,
    created timestamp default current_timestamp not null,
    constraint image_pk
        primary key (id)
);

create table tag
(
    id int auto_increment,
    name varchar(50) default '' not null,
    constraint tag_pk
        primary key (id)
);

create table img_tag
(
    id int auto_increment,
    tag_id int default 0 not null,
    img_id int default 0 not null,
    constraint tag_pk
        primary key (id)
);

alter table daily_word
    add day varchar(20) default '' not null;
create unique index daily_word_day_uindex
    on daily_word (day);

alter table daily_word
    add code int default 0 not null;

# update daily_word set code = id;

create table info
(
    id int auto_increment,
    code int default 0 not null,
    title varchar(20) default '' not null,
    cate_id int default 0 not null,
    remark varchar(30) default '' not null,
    visible bit default 0 not null,
    state tinyint(1) default 0 not null,
    update_time timestamp default current_timestamp on update current_timestamp not null,
    create_time timestamp default current_timestamp not null,
    constraint info_pk
        primary key (id)
);
create unique index info_code_cate_id_uindex
	on info (code, cate_id);


create table info_cate
(
    id int auto_increment,
    name varchar(50) default '' not null,
    od int default 0 not null,
    create_time timestamp default current_timestamp not null,
    constraint info_cate_pk
        primary key (id)
);



