package com.aniamadej.loremipsum.Models;

import com.aniamadej.loremipsum.Models.Words;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class TextScheme {

    Words wordsType;
    int totalParagraphs;
    int minParSize;
    int maxParSize;
    int minSenSize;
    int maxSenSize;

}
