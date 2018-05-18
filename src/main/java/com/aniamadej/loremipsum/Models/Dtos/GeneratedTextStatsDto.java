package com.aniamadej.loremipsum.Models.Dtos;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneratedTextStatsDto {

    private Long numberOfWords;
    private Long numberOfSentences;
    private Long numberOfParagraphs;

}
