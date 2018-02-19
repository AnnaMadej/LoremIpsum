package com.aniamadej.loremipsum.Services;
import com.aniamadej.loremipsum.Models.Dtos.TextSchemeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;


@Service
public class TextBuilderService {

    @Autowired
    ContentCounterService contentCounterService;

    public List<StringBuilder> buildText(TextSchemeModel textSchemeModel){
        List<StringBuilder> text = new ArrayList<>();
        StringBuilder paragraph;
        paragraph = buildParagraph(textSchemeModel);
        text.add(paragraph);
        addStartingPhrase(paragraph, textSchemeModel.getWordsType().getStartingPhrase());

        for (int i = 1 ; i <  textSchemeModel.getTotalParagraphs(); i++){
            paragraph = buildParagraph(textSchemeModel);
            text.add(paragraph);
        }
        contentCounterService.incNumberOfParagraphs(textSchemeModel.getTotalParagraphs());
        return text;
    }

    public StringBuilder buildParagraph(TextSchemeModel textSchemeModel){

        StringBuilder paragraph = new StringBuilder();
        SecureRandom rand = new SecureRandom();
        int numbeOfSentences = rand.nextInt(textSchemeModel.getMaxParSize())+textSchemeModel.getMinParSize();
        for (int i = 0; i<numbeOfSentences; i++){
            paragraph.append(buildSentence(textSchemeModel));
        }
        contentCounterService.incNumberOfSentences(numbeOfSentences);
        return paragraph;
    }

    public void addStartingPhrase(StringBuilder paragraph, String phrase){
        paragraph.setCharAt(0, Character.toLowerCase( paragraph.charAt(0)));
        paragraph.insert(0, phrase + " ");
        contentCounterService.incNumberOfWords(phrase.split("\\w+").length);
    }

    public StringBuilder buildSentence(TextSchemeModel textSchemeModel){
        StringBuilder sentence = new StringBuilder();
        SecureRandom rand = new SecureRandom();
        int numberOfWords = rand.nextInt(textSchemeModel.getMaxSenSize())+textSchemeModel.getMinSenSize();

        sentence.append(textSchemeModel.getWordsType().getRandomWord());
        sentence.setCharAt(0, Character.toUpperCase( sentence.charAt(0)));


        for (int i = 1; i<numberOfWords; i++){
            sentence.append(drawComma());
            sentence.append(textSchemeModel.getWordsType().getRandomWord());
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
