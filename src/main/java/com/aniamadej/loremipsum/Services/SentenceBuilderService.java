package com.aniamadej.loremipsum.Services;

import com.aniamadej.loremipsum.Models.TextScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.function.Supplier;

@Service
@Qualifier("SENTENCE_BUILDER")
public class SentenceBuilderService implements Supplier<StringBuilder> {

    private TextContentCounterService textContentCounterService;
    private TextSchemeService textSchemeService;

    @Autowired
    public SentenceBuilderService(TextContentCounterService textContentCounterService,
                                  TextSchemeService textSchemeService) {
        this.textContentCounterService = textContentCounterService;
        this.textSchemeService = textSchemeService;
    }

    @Override
    public StringBuilder get() {
        StringBuilder sentence = new StringBuilder();
        SecureRandom rand = new SecureRandom();
        int numberOfWords = rand.nextInt(
                (textSchemeService
                        .getTextScheme()
                        .getMaxSenSize() - textSchemeService.getTextScheme()
                                            .getMinSenSize()) + 1)
                                            + textSchemeService.getTextScheme()
                                              .getMinSenSize();

        sentence.append(textSchemeService.getTextScheme().getWordsType().getRandomWord());
        sentence.setCharAt(0, Character.toUpperCase( sentence.charAt(0)));

        for (int i = 1; i<numberOfWords; i++){
            sentence.append(drawComma());
            sentence.append(textSchemeService.getTextScheme().getWordsType().getRandomWord());
        }
        sentence.append(drawPunctationMark());
        sentence.append(" ");

        textContentCounterService.setNumberOfWords(textContentCounterService
                                                   .getNumberOfWords()+numberOfWords);
        return sentence;
    }

    private static char drawPunctationMark(){
        SecureRandom rand = new SecureRandom();
        int randomValue = rand.nextInt(10);

        if (randomValue<6) return '.';
        else if(randomValue>=6 && randomValue<8) return '!';
        else return '?';
    }

    private static String drawComma(){
        SecureRandom rand = new SecureRandom();
        int randomValue = rand.nextInt(10);
        if (randomValue==0) return ", ";
        else return " ";
    }
}
