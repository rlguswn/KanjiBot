package com.example.KanjiBot.bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.stereotype.Component;

@Component
public class DiscordMsgSender {

    private final JDA jda;

    public DiscordMsgSender(JDA jda) {
        this.jda = jda;
    }

    public void sendMessage(String kanjiChannelId, String message) {
        TextChannel textChannel = jda.getTextChannelById(kanjiChannelId);
        textChannel.sendMessage(message).queue();
    }
}
