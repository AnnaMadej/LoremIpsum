package com.aniamadej.loremipsum.Repositories;
import com.aniamadej.loremipsum.Models.Entities.GeneratedTextDescriptionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneratedTextDescriptionRepository extends CrudRepository<GeneratedTextDescriptionEntity, Integer>{
    @Query("SELECT SUM(numberOfWords) from GeneratedTextDescriptionEntity ")
    Integer getNumberOfWordsSum();

    @Query("SELECT SUM(numberOfSentences) from GeneratedTextDescriptionEntity ")
    Integer getNumberOfSentencesSum();

    @Query("SELECT SUM(numberOfParagraphs) from GeneratedTextDescriptionEntity ")
    Integer getNumberOfParagraphsSum();

    List<GeneratedTextDescriptionEntity> findAll();
}
