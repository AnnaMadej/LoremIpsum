package com.aniamadej.loremipsum.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


@Service
@Qualifier("TEXT_BUILDER")
@Scope(scopeName = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TextBuilderService implements Supplier<List<StringBuilder>> {

    List<StringBuilder> text = new ArrayList<>();

    private TextContentCounterService textContentCounterService;
    private Supplier<StringBuilder> paragraphBuilder;
    private TextSchemeService textSchemeService;

    @Autowired
    public TextBuilderService(TextContentCounterService textContentCounterService,
                              @Qualifier("PARAGRAPH_BUILDER") Supplier<StringBuilder> paragraphBuilder,
                              TextSchemeService textSchemeService) {
        this.textContentCounterService = textContentCounterService;
        this.paragraphBuilder = paragraphBuilder;
        this.textSchemeService = textSchemeService;
    }

    public List<StringBuilder> get(){
        StringBuilder paragraph;
        paragraph = paragraphBuilder.get();
        text.add(paragraph);
        addStartingPhrase();

        for (int i = 1; i <  textSchemeService.getTextScheme().getTotalParagraphs(); i++){
            paragraph = paragraphBuilder.get();
            text.add(paragraph);
        }

        textContentCounterService.setNumberOfParagraphs(textContentCounterService.getNumberOfParagraphs()+textSchemeService.getTextScheme().getTotalParagraphs());
        return text;
    }

    private void addStartingPhrase(){
        String phrase = textSchemeService.getTextScheme().getWordsType().getStartingPhrase();
        StringBuilder firstParagraph =  text.get(0);
        firstParagraph.setCharAt(0, Character.toLowerCase( firstParagraph.charAt(0)));
        firstParagraph.insert(0, phrase + " ");
        textContentCounterService.setNumberOfWords(textContentCounterService.getNumberOfWords()+phrase.split("\\w+").length);
    }





}
