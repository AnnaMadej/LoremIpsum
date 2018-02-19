package com.aniamadej.loremipsum.Models.Dtos;

import com.aniamadej.loremipsum.Models.Words;
import lombok.Getter;
import lombok.Setter;

public class TextSchemeModel {

    @Getter
    Words wordsType;

    @Getter
    int totalParagraphs;

    @Getter
    int minParSize;

    @Getter
    int maxParSize;

    @Getter
    int minSenSize;

    @Getter
    @Setter
    int maxSenSize;

    public TextSchemeModel(Words wordsType, int totalParagraphs, int minParSize, int maxParSize, int minSenSize, int maxSenSize) {
        this.wordsType = wordsType;
        this.totalParagraphs = totalParagraphs;
        this.minParSize = minParSize;
        this.maxParSize = maxParSize;
        this.minSenSize = minSenSize;
        this.maxSenSize = maxSenSize;
    }
}
