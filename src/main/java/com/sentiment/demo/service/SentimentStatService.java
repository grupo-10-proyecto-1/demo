package com.sentiment.demo.service;

import com.sentiment.demo.dto.Prevision;
import com.sentiment.demo.entity.SentimentStat;
import com.sentiment.demo.repository.SentimentStatRepository;
import org.springframework.stereotype.Service;


@Service
public class SentimentStatService {

    private final SentimentStatRepository sentimentStatRepository;

    public SentimentStatService(SentimentStatRepository sentimentStatRepository) {
        this.sentimentStatRepository = sentimentStatRepository;
    }

    public SentimentStat guardar(String text, Prevision prevision, Double probabilidad) {
        SentimentStat sentimentStat = new SentimentStat(text,prevision, probabilidad);
        return sentimentStatRepository.save(sentimentStat);
    }

}
