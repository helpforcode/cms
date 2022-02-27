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
