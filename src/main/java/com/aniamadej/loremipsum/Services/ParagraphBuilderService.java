package com.aniamadej.loremipsum.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.function.Supplier;

@Service
@Qualifier("PARAGRAPH_BUILDER")
public class ParagraphBuilderService implements Supplier<StringBuilder> {

    private TextContentCounterService textContentCounterService;
    private Supplier<StringBuilder> sentenceBuilder;
    private TextSchemeService textSchemeService;

    @Autowired
    public ParagraphBuilderService(TextContentCounterService textContentCounterService,
                                   @Qualifier("SENTENCE_BUILDER") Supplier<StringBuilder> sentenceBuilder,
                                   TextSchemeService textSchemeService) {
        this.textContentCounterService = textContentCounterService;
        this.sentenceBuilder = sentenceBuilder;
        this.textSchemeService = textSchemeService;
    }

    @Override
    public StringBuilder get() {
        StringBuilder paragraph = new StringBuilder();
        SecureRandom rand = new SecureRandom();

        int numbeOfSentences = rand.nextInt(
                (textSchemeService.getTextScheme().getMaxParSize() - textSchemeService.getTextScheme().getMinParSize()) + 1) + textSchemeService.getTextScheme().getMinParSize();

        for (int i = 0; i<numbeOfSentences; i++){
            paragraph.append(sentenceBuilder.get());
        }

        textContentCounterService.setNumberOfSentences(textContentCounterService.getNumberOfSentences()+numbeOfSentences);
        return paragraph;
    }




}
