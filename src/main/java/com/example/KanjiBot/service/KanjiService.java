package com.example.KanjiBot.service;

import com.example.KanjiBot.bot.DiscordMsgSender;
import com.example.KanjiBot.domain.Kanji;
import com.example.KanjiBot.domain.KanjiChannel;
import com.example.KanjiBot.mapper.KanjiMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KanjiService {

    private final KanjiMapper kanjiMapper;
    private final KanjiChannelService kanjiChannelService;
    private final DiscordMsgSender discordMsgSender;

    public KanjiService(KanjiMapper kanjiMapper, KanjiChannelService kanjiChannelService, DiscordMsgSender discordMsgSender) {
        this.kanjiMapper = kanjiMapper;
        this.kanjiChannelService = kanjiChannelService;
        this.discordMsgSender = discordMsgSender;
    }

    public void createKanji(Kanji kanji) {
        kanjiMapper.insertKanji(kanji);
    }

    public List<Kanji> getAllKanji() {
        return kanjiMapper.findAll();
    }

    public Kanji getKanjiById(Long id) {
        return kanjiMapper.findById(id);
    }

    public Kanji getKanjiByRandom() {
        Long maxId = kanjiMapper.findMaxId();
        Long randomId = (long) (Math.random() * maxId);
        return kanjiMapper.findById(randomId);
    }

    public void sendKanjiToAllChannel(String sendTime) {
        List<KanjiChannel> kanjiChannels = kanjiChannelService.getKanjiChannelsBySendTime(sendTime);
        System.out.println("Sending kanji to channels at: " + sendTime);
        for (KanjiChannel channel : kanjiChannels) {
            Kanji kanji = getKanjiByRandom();
            System.out.println("Sending kanji to channel: " + channel.getChannelId());
            discordMsgSender.sendMessage(channel.getChannelId(), kanji.getCharacter() + " - " + kanji.getMeaning());
        }
    }
}
