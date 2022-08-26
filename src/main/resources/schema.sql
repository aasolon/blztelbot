CREATE TABLE FLAT (
    ID                      UUID            NOT NULL CONSTRAINT PK_FLAT PRIMARY KEY,
    VERSION                 BIGINT          NOT NULL DEFAULT 0,
    CREATE_DATETIME         TIMESTAMP       NOT NULL,
    ID_FROM_SITE            BIGINT          NOT NULL CONSTRAINT UK_FLAT_ID_FROM_SITE UNIQUE,
    URL                     VARCHAR(4000)   NOT NULL,

    CORPUS                  VARCHAR(255),
    FLOOR                   BIGINT,
    FLOOR_MAX               BIGINT,
    ROOMS_NUMBER            BIGINT,
    AREA                    NUMERIC(20,2),
    CHECK_IN                VARCHAR(255),
    ROOM_ON_THE_FLOOR       BIGINT,
    SECTION                 BIGINT,
    FLAT_TYPE               VARCHAR(255)
);

CREATE TABLE FLAT_STATUS (
    ID                  UUID            NOT NULL CONSTRAINT PK_FLAT_STATUS PRIMARY KEY,
    VERSION             BIGINT          NOT NULL DEFAULT 0,
    FLAT_ID             UUID            NOT NULL CONSTRAINT FK_FLAT_STATUS_FLAT_ID REFERENCES FLAT,
    CREATE_DATETIME     TIMESTAMP       NOT NULL,

    PRICE               BIGINT,
    RESERVE             BOOLEAN         NOT NULL DEFAULT FALSE,
    ACTIVE              BOOLEAN         NOT NULL DEFAULT FALSE,
    ERROR               VARCHAR(4000)
);