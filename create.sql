create sequence hibernate_sequence start with 1 increment by 1;
create table exercise
(
    id           bigint not null,
    description  varchar(255),
    name         varchar(255),
    unilateral   boolean,
    load_type_id bigint not null,
    primary key (id)
);
create table exercise_muscle_groups
(
    exercise_null    bigint not null,
    muscle_groups_id bigint not null
);
create table load_type
(
    id bigint not null,
    primary key (id)
);
create table muscle_group
(
    id          bigint not null,
    description varchar(255),
    name        varchar(255),
    primary key (id)
);
create table muscle_sub_group
(
    id              bigint not null,
    description     varchar(255),
    name            varchar(255),
    muscle_group_id bigint,
    primary key (id)
);
alter table exercise
    add constraint FKok2j2gvabvmfsf5e28nt8qoxk foreign key (load_type_id) references load_type;
alter table exercise_muscle_groups
    add constraint FKhbvrt8al2gkivln4ksavue0sq foreign key (muscle_groups_id) references muscle_group;
alter table exercise_muscle_groups
    add constraint FK3ryvg5qesb5qrjt6ehoc7oltb foreign key (exercise_null) references exercise;
alter table muscle_sub_group
    add constraint FK9ckutfpue3mlujseoyu9yj9yp foreign key (muscle_group_id) references muscle_group;
