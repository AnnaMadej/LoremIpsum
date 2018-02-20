package com.aniamadej.loremipsum.Services;

import com.aniamadej.loremipsum.Models.TextScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@Qualifier("PARAGRAPH_BUILDER")
public class ParagraphBuilderService implements LoremBuilder<StringBuilder>{

    private TextContentCounter textContentCounter;
    private  LoremBuilder<StringBuilder>  loremBuilder;

    @Autowired
    public ParagraphBuilderService(TextContentCounter textContentCounter,
                                   @Qualifier("SENTENCE_BUILDER") LoremBuilder loremBuilder) {
        this.textContentCounter = textContentCounter;
        this.loremBuilder = loremBuilder;
    }

    @Override
    public StringBuilder build(TextScheme textScheme) {
        StringBuilder paragraph = new StringBuilder();
        SecureRandom rand = new SecureRandom();
        int numbeOfSentences = rand.nextInt(textScheme.getMaxParSize())+ textScheme.getMinParSize();
        for (int i = 0; i<numbeOfSentences; i++){
            paragraph.append(loremBuilder.build(textScheme));
        }
        textContentCounter.incNumberOfSentences(numbeOfSentences);
        return paragraph;
    }




}
