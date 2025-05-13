CREATE TABLE kanji (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    kanji_character VARCHAR(10) NOT NULL,
    meaning VARCHAR(255) NOT NULL,
    reading VARCHAR(255) NOT NULL,
    example_sentence VARCHAR(255) NOT NULL
);
