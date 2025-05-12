CREATE TABLE kanji_channel (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    channel_id BIGINT NOT NULL,
    countNumber BIGINT NOT NULL,
    guild_id BIGINT NOT NULL,
    send_time VARCHAR(8) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
);