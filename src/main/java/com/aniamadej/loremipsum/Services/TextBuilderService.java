package com.aniamadej.loremipsum.Services;
import com.aniamadej.loremipsum.Models.TextScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;


@Service
@Qualifier("TEXT_BUILDER")
public class TextBuilderService implements LoremBuilder<List<StringBuilder>>{

    private ContentCounterService contentCounterService;
    private LoremBuilder<StringBuilder> loremBuilder;

    @Autowired
    public TextBuilderService(ContentCounterService contentCounterService,
                              @Qualifier("PARAGRAPH_BUILDER") LoremBuilder loremBuilder) {
        this.contentCounterService = contentCounterService;
        this.loremBuilder = loremBuilder;
    }

    public List<StringBuilder> build(TextScheme textScheme){
        List<StringBuilder> text = new ArrayList<>();
        StringBuilder paragraph;
        paragraph = loremBuilder.build(textScheme);
        addStartingPhrase(paragraph,textScheme);
        text.add(paragraph);

        for (int i = 1; i <  textScheme.getTotalParagraphs(); i++){
            paragraph = loremBuilder.build(textScheme);
            text.add(paragraph);
        }
        contentCounterService.incNumberOfParagraphs(textScheme.getTotalParagraphs());
        return text;
    }

    public void addStartingPhrase(StringBuilder paragraph, TextScheme textScheme){
        String phrase = textScheme.getWordsType().getStartingPhrase();
        paragraph.setCharAt(0, Character.toLowerCase( paragraph.charAt(0)));
        paragraph.insert(0, phrase + " ");
        contentCounterService.incNumberOfWords(phrase.split("\\w+").length);
    }





}
