package com.aniamadej.loremipsum.Models.Dtos;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GeneratedTextStatsDto {

    private int numberOfWords;
    private int numberOfSentences;
    private int numberOfParagraphs;

    public GeneratedTextStatsDto(Long numberOfWords, Long numberOfSentences, Long numberOfParagraphs) {
        this.numberOfWords = numberOfWords.intValue();
        this.numberOfSentences = numberOfSentences.intValue();
        this.numberOfParagraphs = numberOfParagraphs.intValue();
    }
}
