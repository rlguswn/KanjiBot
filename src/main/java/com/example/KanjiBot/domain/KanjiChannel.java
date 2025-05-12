package com.example.KanjiBot.domain;

import java.time.LocalDate;

public class KanjiChannel {

    private Long id;
    private String channelId;
    private Long countNumber;
    private String guildId;
    private String sendTime;
    private LocalDate createdAt;

    protected KanjiChannel() {
    }

    public KanjiChannel(Long countNumber, String channelId, String guildId, String sendTime) {
        this.countNumber = countNumber;
        this.channelId = channelId;
        this.guildId = guildId;
        this.sendTime = sendTime;
        this.createdAt = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Long getCountNumber() {
        return countNumber;
    }

    public void setCountNumber(Long countNumber) {
        this.countNumber = countNumber;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
