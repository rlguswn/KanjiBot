CREATE TABLE kanji_channel (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    channel_id BIGINT NOT NULL UNIQUE,
    count_number BIGINT NOT NULL,
    guild_id BIGINT NOT NULL,
    send_time VARCHAR(8),
    send_mode VARCHAR(16) DEFAULT 'random',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);