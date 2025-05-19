package com.example.KanjiBot.bot;

import com.example.KanjiBot.domain.KanjiChannel;
import com.example.KanjiBot.service.KanjiChannelService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class MessageListener extends ListenerAdapter {

    private final KanjiChannelService kanjiChannelService;
    private final DiscordMsgSender discordMsgSender;

    public MessageListener(KanjiChannelService kanjiChannelService, DiscordMsgSender discordMsgSender) {
        this.kanjiChannelService = kanjiChannelService;
        this.discordMsgSender = discordMsgSender;
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
                discordMsgSender.sendMessage(event.getChannel().getId(), "`!등록 HH:MM` 형식으로 입력해주세요!");
                return;
            }

            String sendTime = split[1];
            String channelId = event.getChannel().getId();
            String guildId = event.getGuild().getId();

            if (!sendTime.matches("^\\d{2}:\\d{2}$")) {
                discordMsgSender.sendMessage(event.getChannel().getId(), "시간 형식은 `HH:MM` 이어야 합니다. 예: `08:30`");
                return;
            }

            String[] timeParts = sendTime.split(":");
            int hour;
            int minute;

            try {
                hour = Integer.parseInt(timeParts[0]);
                minute = Integer.parseInt(timeParts[1]);
            } catch (NumberFormatException e) {
                discordMsgSender.sendMessage(event.getChannel().getId(), "시간 형식이 잘못되었습니다. 숫자만 입력해주세요.");
                return;
            }

            if (hour < 0 || hour > 23) {
                discordMsgSender.sendMessage(event.getChannel().getId(), "시간은 00~23 사이여야 합니다.");
                return;
            }

            if (!(minute == 0 || minute == 30)) {
                discordMsgSender.sendMessage(event.getChannel().getId(), "30분 단위로만 등록할 수 있습니다. (예: 08:00, 14:30)");
                return;
            }

            KanjiChannel kanjiChannel = new KanjiChannel(
                    0L,
                    channelId,
                    guildId,
                    sendTime
            );

            kanjiChannelService.createKanjiChannel(kanjiChannel);
            discordMsgSender.sendMessage(event.getChannel().getId(), "`" + sendTime + "`에 Kanji 알림을 등록했습니다!");
        } else if (content.equals("!해제")) {
            String channelId = event.getChannel().getId();
            KanjiChannel kanjiChannel = kanjiChannelService.getKanjiChannelByChannelId(channelId);

            if (kanjiChannel == null) {
                discordMsgSender.sendMessage(event.getChannel().getId(), "등록된 알림이 없습니다.");
                return;
            }

            kanjiChannelService.updateSendTime(channelId, null);
            discordMsgSender.sendMessage(event.getChannel().getId(), "Kanji 알림을 해제했습니다!");
        } else if (content.equals("!모드")) {
            String channelId = event.getChannel().getId();
            KanjiChannel kanjiChannel = kanjiChannelService.getKanjiChannelByChannelId(channelId);
            if (kanjiChannel == null) {
                discordMsgSender.sendMessage(event.getChannel().getId(), "등록된 알림이 없습니다.");
                return;
            }

            if (kanjiChannel.getSendMode().equals("random")) {
                kanjiChannelService.updateSendMode(channelId, "sequential");
                discordMsgSender.sendMessage(event.getChannel().getId(), "모드를 `순차적`으로 변경했습니다.");
            } else {
                kanjiChannelService.updateSendMode(channelId, "random");
                discordMsgSender.sendMessage(event.getChannel().getId(), "모드를 `랜덤`으로 변경했습니다.");
            }
        }
    }
}
