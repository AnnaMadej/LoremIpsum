package com.aniamadej.loremipsum.Models.Dtos;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneratedTextStatsDto {
    private int numberOfWords;
    private int numberOfSentences;
    private int numberOfParagraphs;

    @Temporal(TemporalType.TIMESTAMP)
    private Date added = new Date();

}
