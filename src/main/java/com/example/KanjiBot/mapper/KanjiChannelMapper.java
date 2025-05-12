package com.example.KanjiBot.mapper;

import com.example.KanjiBot.domain.KanjiChannel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface KanjiChannelMapper {

    @Insert("INSERT INTO kanji_channel (count, channel_id, guild_id, send_time, created_at) " +
            "VALUES (#{count}, #{channelId}, #{guildId}, #{sendTime}, #{createdAt})")
    void insertKanjiChannel(@Param("count") Long count,
                            @Param("channelId") String channelId,
                            @Param("guildId") String guildId,
                            @Param("sendTime") String sendTime,
                            @Param("createdAt") String createdAt
    );

    @Select("SELECT * FROM kanji_channel")
    List<KanjiChannel> findAll();

    @Select("SELECT * FROM kanji_channel WHERE id = #{id}")
    KanjiChannel findById(@Param("id") Long id);

    @Select("SELECT * FROM kanji_channel WHERE channel_id = #{channelId}")
    KanjiChannel findByChannelId(@Param("channelId") String channelId);

    @Select("SELECT * FROM kanji_channel WHERE send_time = #{sendTime}")
    List<KanjiChannel> findBySendTime(@Param("sendTime") String sendTime);
}
