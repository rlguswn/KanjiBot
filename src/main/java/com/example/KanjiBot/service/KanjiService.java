package com.example.KanjiBot.service;

import com.example.KanjiBot.bot.DiscordMsgSender;
import com.example.KanjiBot.deepl.DeepLTranslate;
import com.example.KanjiBot.domain.Kanji;
import com.example.KanjiBot.domain.KanjiChannel;
import com.example.KanjiBot.mapper.KanjiMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class KanjiService {

    private final KanjiMapper kanjiMapper;
    private final KanjiChannelService kanjiChannelService;
    private final DiscordMsgSender discordMsgSender;
    private final DeepLTranslate deepLTranslate;

    public KanjiService(KanjiMapper kanjiMapper, KanjiChannelService kanjiChannelService, DiscordMsgSender discordMsgSender, DeepLTranslate deepLTranslate) {
        this.kanjiMapper = kanjiMapper;
        this.kanjiChannelService = kanjiChannelService;
        this.discordMsgSender = discordMsgSender;
        this.deepLTranslate = deepLTranslate;
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

    public void sendKanjiToAllChannel(String sendTime) throws IOException {
        List<KanjiChannel> kanjiChannels = kanjiChannelService.getKanjiChannelsBySendTime(sendTime);
        if (kanjiChannels.isEmpty()) {
            System.out.println("No channels found for send time: " + sendTime);
            return;
        }
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

            String meaning;
            if (!kanji.getMeaning().matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
                meaning = deepLTranslate.translate(kanji.getMeaning(), "EN", "KO");
                kanjiMapper.updateKanjiMeaning(kanji.getId(), meaning);
            } else {
                meaning = kanji.getMeaning();
            }

            discordMsgSender.sendMessage(channel.getChannelId(), "Kanji 알림이 도착했습니다!");
            discordMsgSender.sendMessage(channel.getChannelId(),
                    "```" +
                            "📖 Today's Kanji\n" +
                            "━━━━━━━━━━━━━━━━━━━━\n" +
                            "Kanji   : " + kanji.getKanjiCharacter() + "\n" +
                            "Meaning : " + meaning + "\n" +
                            "Reading : " + kanji.getReading() + "\n" +
                            "```"
            );

            kanjiChannelService.increaseCountNumber(channel.getChannelId());
        }
    }
}
