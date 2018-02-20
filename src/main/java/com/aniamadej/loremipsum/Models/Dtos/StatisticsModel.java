package com.aniamadej.loremipsum.Models.Dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
public class StatisticsModel {
    private int textId;
    private int numberOfWords;
    private int numberOfSentences;
    private int numberOfParagraphs;

    @Temporal(TemporalType.TIMESTAMP)
    private Date added = new Date();

    public StatisticsModel(int numberOfWords, int numberOfSentences, int numberOfParagraphs) {
            this.numberOfWords = numberOfWords;
            this.numberOfSentences = numberOfSentences;
            this.numberOfParagraphs = numberOfParagraphs;
    }
}
