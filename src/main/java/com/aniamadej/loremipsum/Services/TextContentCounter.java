package com.aniamadej.loremipsum.Services;

public interface TextContentCounter {
    void incNumberOfParagraphs(int value);
    void incNumberOfSentences(int value);
    void incNumberOfWords(int value);
    int getNumberOfParagraphs();
    int getNumberOfWords();
    int getNumberOfSentences();
}
