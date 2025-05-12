package com.example.KanjiBot.mapper;

import com.example.KanjiBot.domain.Kanji;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface KanjiMapper {

    @Insert("INSERT INTO kanji (character, meaning, reading, exampleSentence)" +
            "VALUES (#{character}, #{meaning}, #{reading}, #{exampleSentence})")
    void insertKanji(@Param("character") String character,
                     @Param("meaning") String meaning,
                     @Param("reading") String reading,
                     @Param("exampleSentence") String exampleSentence
    );

    @Select("SELECT * FROM kanji")
    List<Kanji> findAll();

    @Select("SELECT * FROM kanji WHERE id = #{id}")
    Kanji findById(@Param("id") Long id);
}
