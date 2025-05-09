package com.example.KanjiBot.bot;

import com.example.KanjiBot.config.DiscordConfig;
import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.stereotype.Component;

@Component
public class DiscordBotStarter {
    private final DiscordConfig discordConfig;
    private final MessageListener messageListener;

    public DiscordBotStarter(DiscordConfig discordConfig, MessageListener messageListener) {
        this.discordConfig = discordConfig;
        this.messageListener = messageListener;
    }

    @PostConstruct
    public void startBot() {
        JDABuilder.createDefault(discordConfig.getToken())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(messageListener)
                .setActivity(Activity.playing("Kanji Bot Start!"))
                .build();
    }
}
