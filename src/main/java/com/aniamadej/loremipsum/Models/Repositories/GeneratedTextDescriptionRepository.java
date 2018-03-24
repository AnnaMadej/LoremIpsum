package com.aniamadej.loremipsum.Models.Repositories;
import com.aniamadej.loremipsum.Models.Dtos.GeneratedTextStatsDto;
import com.aniamadej.loremipsum.Models.Entities.GeneratedTextDescriptionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneratedTextDescriptionRepository extends CrudRepository<GeneratedTextDescriptionEntity, Integer>{
    @Query ("SELECT new com.aniamadej.loremipsum.Models.Dtos.GeneratedTextStatsDto(sum(td.numberOfWords), sum(td.numberOfSentences), sum(td.numberOfParagraphs))from GeneratedTextDescriptionEntity td")
    GeneratedTextStatsDto getSumsOfGeneratedTexts();

    List<GeneratedTextDescriptionEntity> findTop50ByOrderByAddedDesc();
}
