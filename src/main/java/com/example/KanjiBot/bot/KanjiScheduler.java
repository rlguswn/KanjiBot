package com.example.KanjiBot.bot;

import com.example.KanjiBot.service.KanjiService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class KanjiScheduler {

    private final KanjiService kanjiService;

    public KanjiScheduler(KanjiService kanjiService) {
        this.kanjiService = kanjiService;
    }

    @Scheduled(cron = "0 */30 * * * *")
    public void sendDailyKanji() throws IOException {
        LocalTime now = LocalTime.now();
        String sendTime = now.format(DateTimeFormatter.ofPattern("HH:mm"));
        kanjiService.sendKanjiToAllChannel(sendTime);
    }
}
