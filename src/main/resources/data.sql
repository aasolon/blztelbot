INSERT INTO CIV_PLAYER VALUES (
    '1da7fb86-292c-4f50-a31e-a0461c4f4ea9',
    1,
    'civ_player_1',
    'telegram_user_1',
    4567,
    1,
    true
),

(
    '1da7fb86-292c-4f50-a31e-a0461c4f4ea8',
    1,
    'civ_player_2',
    'telegram_user_2',
    4568,
    2,
    true
),

(
    '1da7fb86-292c-4f50-a31e-a0461c4f4ea7',
    1,
    'civ_player_3',
    'telegram_user_3',
    4569,
    3,
    true
);

INSERT INTO CIV_TURN_INFO VALUES
    (
        '12a7fb86-292c-4f50-a31e-a0461c4f4ea7',
        1,
        'Game Name',
        1,
        'civ_player_2',
        DATEADD('DAY', -3, CURRENT_TIMESTAMP())
    ),

    (
        '13a7fb86-292c-4f50-a31e-a0461c4f4ea7',
        1,
        'Game Name',
        1,
        'civ_player_3',
        DATEADD('DAY', -2, CURRENT_TIMESTAMP())
    ),

    (
        '11a7fb86-292c-4f50-a31e-a0461c4f4ea7',
        1,
        'Game Name',
        2,
        'civ_player_1',
        DATEADD('DAY', -1, CURRENT_TIMESTAMP())
    ),

    (
        '1117fb86-292c-4f50-a31e-a0461c4f4ea7',
        1,
        'Game Name',
        2,
        'civ_player_2',
        CURRENT_TIMESTAMP()
    )

;
