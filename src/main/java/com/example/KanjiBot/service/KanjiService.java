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
        if (maxId == null || maxId == 0) {
            return null;
        }

        Long randomId = (long) (Math.random() * maxId);
        return kanjiMapper.findById(randomId);
    }

    public void sendKanjiToAllChannel(String sendTime) {
        List<KanjiChannel> kanjiChannels = kanjiChannelService.getKanjiChannelsBySendTime(sendTime);
        if (kanjiChannels.isEmpty()) {
            System.out.println("No channels found for send time: " + sendTime);
            return;
        }
        System.out.println("Sending kanji to channels at: " + sendTime);
        System.out.println("Found " + kanjiChannels.size() + " channels for send time: " + sendTime);
        for (KanjiChannel channel : kanjiChannels) {
            Kanji kanji;
            if (channel.getSendMode().equals("random")) {
                kanji = getKanjiByRandom();
            } else {
                Long maxId = kanjiMapper.findMaxId();
                Long countNumber = channel.getCountNumber();
                Long index = countNumber % maxId + 1;

                kanji = getKanjiById(index);
            }

            if (kanji == null) {
                System.out.println("No kanji found for random selection.");
                return;
            }

            System.out.println("Sending kanji to channel: " + channel.getChannelId());
            discordMsgSender.sendMessage(channel.getChannelId(), "Kanji 알림이 도착했습니다!");
            discordMsgSender.sendMessage(channel.getChannelId(),
                    "```" +
                            "📖 Today's Kanji\n" +
                            "━━━━━━━━━━━━━━━━━━━━\n" +
                            "Kanji   : " + kanji.getKanjiCharacter() + "\n" +
                            "Meaning : " + kanji.getMeaning() + "\n" +
                            "Reading : " + kanji.getReading() + "\n" +
                            "```"
            );

            kanjiChannelService.increaseCountNumber(channel.getChannelId());
        }
    }
}
