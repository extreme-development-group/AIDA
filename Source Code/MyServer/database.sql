create database oicq;
use oicq;

create table aida_user
(
    user_id int(10) auto_increment not null,
    user_password varchar(32) not null,
    user_avatar text not null,
    user_nickname varchar(10) not null,
    user_signature varchar(20),
    user_sex char(1) default '男' not null,
    user_age int(3),
    user_birthday varchar(12),
    user_hometown varchar(20),
    user_company varchar(20),
    user_address varchar(20),
    user_email varchar(30) unique not null,
    user_registertime datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    primary key(user_id)
)ENGINE=InnoDB AUTO_INCREMENT = 10000 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--insert into dw_user (user_name,user_password,user_email) values('im0qianqian',md5('123456'),'qian')

create table aida_group
(
    group_id int(10) auto_increment not null,
    group_name varchar(20) not null,
    group_master int(10) not null,
    group_avatar text not null,
    group_registertime datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    foreign key(group_master) references aida_user(user_id) on delete cascade on update cascade,
    primary key(group_id)
)ENGINE=InnoDB AUTO_INCREMENT = 10000 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--insert into dw_group (group_name,group_master) VALUES ('路上看到就发了束带结发',10001)--

create table aida_useruser
(
    user_id int(10),
    friend_id int(10),
    index(user_id),
    index(friend_id),
    foreign key(user_id) references aida_user(user_id) on delete cascade on update cascade,
    foreign key(friend_id) references aida_user(user_id) on delete cascade on update cascade,
    primary key(user_id,friend_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--insert into dw_useruser values(10001,10002)



create table aida_groupuser
(
    user_id int(10),
    group_id int(10),
    index(user_id),
    index(group_id),
    foreign key(user_id) references aida_user(user_id) on delete cascade on update cascade,
    foreign key(group_id) references aida_group(group_id) on delete cascade on update cascade,
    primary key(user_id,group_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--insert into dw_usergroup values(10002,10001)


create table dw_userchat
(
    uchat_id int(10) auto_increment not null,
    uchat_fromid int(10) not null,
    uchat_toid int(10) not null,
    uchat_message text not null,
    uchat_datetime datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    foreign key(uchat_fromid) references dw_user(user_id) on delete cascade on update cascade,
    foreign key(uchat_toid) references dw_user(user_id) on delete cascade on update cascade,
    primary key(uchat_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--insert into dw_userchat (uchat_fromid,uchat_toid,uchat_message) values (10002,10003,'asdfasdfasdf')

create table dw_groupchat
(
    gchat_id int(10) auto_increment not null,
    gchat_uid int(10) not null,
    gchat_gid int(10) not null,
    gchat_message text not null,
    gchat_datetime datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    primary key(gchat_id),
    foreign key(gchat_uid) references dw_user(user_id) on delete cascade on update cascade,
    foreign key(gchat_gid) references dw_group(group_id) on delete cascade on update cascade
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--insert into dw_groupchat (gchat_uid,gchat_gid,gchat_message) values (10002,10002,'aaaaa')

create view view_usergroup
as
(select dw_group.group_id,dw_group.group_name,dw_group.group_avatar,dw_group.group_trades,dw_usergroup.user_id from dw_group,dw_usergroup where dw_group.group_id = dw_usergroup.group_id)

create view view_useruser
as
(select dw_useruser.myself,dw_useruser.myfriend,dw_user.user_name,dw_user.user_avatar,dw_user.user_trades from dw_user,dw_useruser where dw_user.user_id = dw_useruser.myfriend order by dw_useruser.myself)