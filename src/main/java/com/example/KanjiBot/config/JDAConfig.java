package com.example.KanjiBot.config;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JDAConfig {

    private final DiscordConfig discordConfig;

    public JDAConfig(DiscordConfig discordConfig) {
        this.discordConfig = discordConfig;
    }

    @Bean
    public JDA jda() throws Exception {
        return JDABuilder.createDefault(discordConfig.getToken())
                .build()
                .awaitReady();
    }
}
