package com.sentiment.demo.service;

import com.sentiment.demo.dto.HistoryResponseDTO;
import com.sentiment.demo.repository.SentimentStatRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    private final SentimentStatRepository sentimentStatRepository;

    public HistoryService(SentimentStatRepository sentimentStatRepository) {
        this.sentimentStatRepository = sentimentStatRepository;
    }

    public List<HistoryResponseDTO> getStatHistory(int limite) {
        var filas = sentimentStatRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0,limite));
        return filas.stream().map(s -> new HistoryResponseDTO(
                s.getText(),
                s.getPrevision(),
                s.getProbabilidad(),
                s.getCreatedAt()
        )).toList();
    }

}
