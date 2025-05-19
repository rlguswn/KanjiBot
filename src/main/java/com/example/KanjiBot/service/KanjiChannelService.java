package com.example.KanjiBot.service;

import com.example.KanjiBot.domain.KanjiChannel;
import com.example.KanjiBot.mapper.KanjiChannelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KanjiChannelService {

    private final KanjiChannelMapper kanjiChannelMapper;

    public KanjiChannelService(KanjiChannelMapper kanjiChannelMapper) {
        this.kanjiChannelMapper = kanjiChannelMapper;
    }

    public void createKanjiChannel(KanjiChannel kanjiChannel) {
        if (kanjiChannelMapper.findByChannelId(kanjiChannel.getChannelId()) != null) {
            kanjiChannelMapper.updateSendTime(kanjiChannel.getChannelId(), kanjiChannel.getSendTime());
            return;
        }
        kanjiChannelMapper.insertKanjiChannel(kanjiChannel);
    }

    public List<KanjiChannel> getAllKanjiChannels() {
        return kanjiChannelMapper.findAll();
    }

    public KanjiChannel getKanjiChannelById(Long id) {
        return kanjiChannelMapper.findById(id);
    }

    public KanjiChannel getKanjiChannelByChannelId(String channelId) {
        return kanjiChannelMapper.findByChannelId(channelId);
    }

    public List<KanjiChannel> getKanjiChannelsBySendTime(String sendTime) {
        return kanjiChannelMapper.findBySendTime(sendTime);
    }

    public void updateSendTime(String channelId, String sendTime) {
        kanjiChannelMapper.updateSendTime(channelId, sendTime);
    }

    public void increaseCountNumber(String channelId) {
        KanjiChannel kanjiChannel = kanjiChannelMapper.findByChannelId(channelId);
        if (kanjiChannel == null) {
            return;
        }
        Long countNumber = kanjiChannel.getCountNumber();

        kanjiChannelMapper.updateCountNumber(channelId, countNumber+1);
    }

    public void updateSendMode(String channelId, String sendMode) {
        kanjiChannelMapper.updateSendMode(channelId, sendMode);
    }
}
