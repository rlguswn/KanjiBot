package com.example.KanjiBot.bot;

import com.example.KanjiBot.domain.KanjiChannel;
import com.example.KanjiBot.service.KanjiChannelService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class MessageListener extends ListenerAdapter {

    private final KanjiChannelService kanjiChannelService;

    public MessageListener(KanjiChannelService kanjiChannelService) {
        this.kanjiChannelService = kanjiChannelService;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        String content = event.getMessage().getContentRaw();

        if (content.equals("!ping")) {
            event.getChannel().sendMessage("Pong!").queue();
        } else if (content.startsWith("!등록")) {
            String[] split = content.split(" ");
            if (split.length < 2) {
                event.getChannel().sendMessage("`!등록 HH:MM` 형식으로 입력해주세요!").queue();
                return;
            }

            String sendTime = split[1];
            String channelId = event.getChannel().getId();
            String guildId = event.getGuild().getId();

            if (!(sendTime.split(":")[1].equals("30") || sendTime.split(":")[1].equals("00"))) {
                event.getChannel().sendMessage("30분 단위로 등록이 가능합니다.").queue();
                return;
            }

            KanjiChannel kanjiChannel = new KanjiChannel(
                    0L,
                    channelId,
                    guildId,
                    sendTime
            );

            kanjiChannelService.createKanjiChannel(kanjiChannel);
            event.getChannel().sendMessage("`" + sendTime + "`에 Kanji 알림을 등록했습니다!").queue();
        }
    }
}
