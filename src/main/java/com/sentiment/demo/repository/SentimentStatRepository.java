package com.sentiment.demo.repository;

import com.sentiment.demo.entity.SentimentStat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface SentimentStatRepository extends JpaRepository<SentimentStat, Long> {
    List<SentimentStat> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
