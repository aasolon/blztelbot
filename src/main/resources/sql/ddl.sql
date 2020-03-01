create table CIV_GAMEINFO(
    ID serial primary key,
    NAME varchar(1000) not null
);

create table CIV_PLAYERINFO(
    ID serial primary key,
    NAME varchar(1000) not null
);

create table CIV_GAMEPLAYER(
    ID serial primary key,
    GAME_ID integer not null references CIV_GAMEINFO,
    PLAYER_ID integer not null references CIV_PLAYERINFO,
    TURN_ORDER integer not null,
    constraint UQ_CIV_GAMEUSER unique (GAME_ID, PLAYER_ID),
    constraint UQ_CIV_GAMEUSER_TURN_ORDER unique (GAME_ID, TURN_ORDER)
);

create table CIV_TURNINFO(
    ID serial primary key,
    GAME_ID integer not null references CIV_GAMEINFO,
    PLAYER_ID integer not null references CIV_PLAYERINFO,
    TURN_NUMBER integer not null,
    START_DATE timestamp not null,
    END_DATE timestamp,
    constraint UQ_CIV_TURNINFO unique (GAME_ID, PLAYER_ID, TURN_NUMBER)
);

create table CIV_TURNINFO_RAW(
    ID serial primary key,
    GAME_NAME varchar(1000) not null,
    PLAYER_NAME varchar(1000) not null,
    TURN_NUMBER integer not null,
    START_DATE timestamp not null,
    END_DATE timestamp,
    constraint UQ_CIV_TURNINFO_RAW unique (GAME_NAME, PLAYER_NAME, TURN_NUMBER)
);