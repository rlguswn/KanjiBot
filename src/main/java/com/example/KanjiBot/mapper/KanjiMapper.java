package com.example.KanjiBot.mapper;

import com.example.KanjiBot.domain.Kanji;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface KanjiMapper {

    @Insert("INSERT INTO kanji (kanji_character, meaning, reading, example_sentence)" +
            "VALUES (#{character}, #{meaning}, #{reading}, #{exampleSentence})")
    void insertKanji(Kanji kanji);

    @Select("SELECT * FROM kanji")
    List<Kanji> findAll();

    @Select("SELECT * FROM kanji WHERE id = #{id}")
    Kanji findById(@Param("id") Long id);

    @Select("SELECT MAX(id) FROM kanji")
    Long findMaxId();
}
