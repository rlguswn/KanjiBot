package com.example.KanjiBot.mapper;

import com.example.KanjiBot.domain.Kanji;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface KanjiMapper {

    @Insert("INSERT INTO kanji (kanji_character, meaning, reading, example_sentence)" +
            "VALUES (#{kanjiCharacter}, #{meaning}, #{reading}, #{exampleSentence})")
    void insertKanji(Kanji kanji);

    @Select("SELECT * FROM kanji")
    List<Kanji> findAll();

    @Select("SELECT * FROM kanji WHERE id = #{id}")
    Kanji findById(@Param("id") Long id);

    @Select("SELECT MAX(id) FROM kanji")
    Long findMaxId();

    @Update("UPDATE kanji SET meaning = #{meaning} WHERE id = #{id}")
    void updateKanjiMeaning(@Param("id") Long id, @Param("meaning") String meaning);
}
