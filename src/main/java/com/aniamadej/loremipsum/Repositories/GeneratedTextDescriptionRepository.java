package com.aniamadej.loremipsum.Repositories;
import com.aniamadej.loremipsum.Models.Entities.GeneratedTextDescriptionModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneratedTextDescriptionRepository extends CrudRepository<GeneratedTextDescriptionModel, Integer>{
    @Query("SELECT SUM(numberOfWords) from GeneratedTextDescriptionModel ")
    Integer getNumberOfWordsSum();

    @Query("SELECT SUM(numberOfSentences) from GeneratedTextDescriptionModel ")
    Integer getNumberOfSentencesSum();

    @Query("SELECT SUM(numberOfParagraphs) from GeneratedTextDescriptionModel ")
    Integer getNumberOfParagraphsSum();
}
