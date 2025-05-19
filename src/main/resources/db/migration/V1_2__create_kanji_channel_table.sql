CREATE TABLE kanji_channel (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    channel_id BIGINT NOT NULL,
    count_number BIGINT NOT NULL,
    guild_id BIGINT NOT NULL,
    send_time VARCHAR(8),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);