package com.aniamadej.loremipsum.Services;

import com.aniamadej.loremipsum.Models.TextScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@Qualifier("PARAGRAPH_BUILDER")
public class ParagraphBuilderService implements LoremBuilder<StringBuilder>{

    private ContentCounterService contentCounterService;
    private SentenceBuilderService sentenceBuilderService;

    @Autowired
    public ParagraphBuilderService(ContentCounterService contentCounterService, SentenceBuilderService sentenceBuilderService) {
        this.contentCounterService = contentCounterService;
        this.sentenceBuilderService = sentenceBuilderService;
    }

    @Override
    public StringBuilder build(TextScheme textScheme) {
        StringBuilder paragraph = new StringBuilder();
        SecureRandom rand = new SecureRandom();
        int numbeOfSentences = rand.nextInt(textScheme.getMaxParSize())+ textScheme.getMinParSize();
        for (int i = 0; i<numbeOfSentences; i++){
            paragraph.append(sentenceBuilderService.build(textScheme));
        }
        contentCounterService.incNumberOfSentences(numbeOfSentences);
        return paragraph;
    }

    public void addStartingPhrase(StringBuilder paragraph, String phrase){
        paragraph.setCharAt(0, Character.toLowerCase( paragraph.charAt(0)));
        paragraph.insert(0, phrase + " ");
        contentCounterService.incNumberOfWords(phrase.split("\\w+").length);
    }

    public void makeFirstParagraph(StringBuilder paragraph, TextScheme textScheme){
        addStartingPhrase(paragraph, textScheme.getWordsType().getStartingPhrase());
    }
}
