package com.example.KanjiBot.mapper;

import com.example.KanjiBot.domain.KanjiChannel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface KanjiChannelMapper {

    @Insert("INSERT INTO kanji_channel (count_number, channel_id, guild_id, send_time, created_at) " +
            "VALUES (#{countNumber}, #{channelId}, #{guildId}, #{sendTime}, #{createdAt})")
    void insertKanjiChannel(KanjiChannel kanjiChannel);

    @Select("SELECT * FROM kanji_channel")
    List<KanjiChannel> findAll();

    @Select("SELECT * FROM kanji_channel WHERE id = #{id}")
    KanjiChannel findById(@Param("id") Long id);

    @Select("SELECT * FROM kanji_channel WHERE channel_id = #{channelId}")
    KanjiChannel findByChannelId(@Param("channelId") String channelId);

    @Select("SELECT * FROM kanji_channel WHERE send_time = #{sendTime}")
    List<KanjiChannel> findBySendTime(@Param("sendTime") String sendTime);

    @Update("UPDATE kanji_channel SET send_time = #{sendTime} WHERE channel_id = #{channelId}")
    void updateSendTime(@Param("channelId") String channelId, @Param("sendTime") String sendTime);

    @Update("UPDATE kanji_channel SET count_number = #{countNumber} WHERE channel_id = #{channelId}")
    void updateCountNumber(@Param("channelId") String channelId, @Param("countNumber") int countNumber);
}
