package com.aniamadej.loremipsum.Services;
import com.aniamadej.loremipsum.Models.TextScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;


@Service
public class TextBuilderService {

    @Autowired
    ContentCounterService contentCounterService;

    public List<StringBuilder> buildText(TextScheme textScheme){
        List<StringBuilder> text = new ArrayList<>();
        StringBuilder paragraph;
        paragraph = buildParagraph(textScheme);
        text.add(paragraph);
        addStartingPhrase(paragraph, textScheme.getWordsType().getStartingPhrase());

        for (int i = 1; i <  textScheme.getTotalParagraphs(); i++){
            paragraph = buildParagraph(textScheme);
            text.add(paragraph);
        }
        contentCounterService.incNumberOfParagraphs(textScheme.getTotalParagraphs());
        return text;
    }

    public StringBuilder buildParagraph(TextScheme textScheme){

        StringBuilder paragraph = new StringBuilder();
        SecureRandom rand = new SecureRandom();
        int numbeOfSentences = rand.nextInt(textScheme.getMaxParSize())+ textScheme.getMinParSize();
        for (int i = 0; i<numbeOfSentences; i++){
            paragraph.append(buildSentence(textScheme));
        }
        contentCounterService.incNumberOfSentences(numbeOfSentences);
        return paragraph;
    }

    public void addStartingPhrase(StringBuilder paragraph, String phrase){
        paragraph.setCharAt(0, Character.toLowerCase( paragraph.charAt(0)));
        paragraph.insert(0, phrase + " ");
        contentCounterService.incNumberOfWords(phrase.split("\\w+").length);
    }

    public StringBuilder buildSentence(TextScheme textScheme){
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

        contentCounterService.incNumberOfWords(numberOfWords);
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
