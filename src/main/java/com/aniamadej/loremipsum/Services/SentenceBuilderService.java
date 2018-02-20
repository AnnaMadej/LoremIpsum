package com.aniamadej.loremipsum.Services;

import com.aniamadej.loremipsum.Models.TextScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@Qualifier("SENTENCE_BUILDER")
public class SentenceBuilderService implements LoremBuilder<StringBuilder>{

    private TextContentCounter textContentCounter;

    @Autowired
    public SentenceBuilderService(TextContentCounter textContentCounter) {
        this.textContentCounter = textContentCounter;
    }

    @Override
    public StringBuilder build(TextScheme textScheme) {
        StringBuilder sentence = new StringBuilder();
        SecureRandom rand = new SecureRandom();
        int numberOfWords = rand.nextInt(textScheme.getMaxSenSize())+ textScheme.getMinSenSize();

        sentence.append(textScheme.getWordsType().getRandomWord());
        sentence.setCharAt(0, Character.toUpperCase( sentence.charAt(0)));


        for (int i = 1; i<numberOfWords; i++){
            sentence.append(drawComma());
            sentence.append(textScheme.getWordsType().getRandomWord());
        }
        sentence.append(drawPunctationMark());
        sentence.append(" ");

        textContentCounter.incNumberOfWords(numberOfWords);
        return sentence;
    }

    public char drawPunctationMark(){
        SecureRandom rand = new SecureRandom();
        int randomValue = rand.nextInt(10);

        if (randomValue<6) return '.';
        else if(randomValue>=6 && randomValue<8) return '!';
        else return '?';
    }

    public String drawComma(){
        SecureRandom rand = new SecureRandom();
        int randomValue = rand.nextInt(10);
        if (randomValue==0) return ", ";
        else return " ";
    }
}
