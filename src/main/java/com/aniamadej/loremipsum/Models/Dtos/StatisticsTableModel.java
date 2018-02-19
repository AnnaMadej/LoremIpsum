package com.aniamadej.loremipsum.Models.Dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatisticsTableModel {
 Integer sumOfWords;
 Integer sumOfSentences;
 Integer sumOfParagraphs;

    public StatisticsTableModel(Integer sumOfWords, Integer sumOfSentences, Integer sumOfParagraphs) {
            this.sumOfWords = sumOfWords;
            this.sumOfSentences = sumOfSentences;
            this.sumOfParagraphs = sumOfParagraphs;
    }
}
