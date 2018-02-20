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
    private ParagraphBuilderService paragraphBuilderService;

    @Autowired
    public TextBuilderService(ContentCounterService contentCounterService,
                              ParagraphBuilderService paragraphBuilderService) {
        this.contentCounterService = contentCounterService;
        this.paragraphBuilderService = paragraphBuilderService;
    }

    public List<StringBuilder> build(TextScheme textScheme){
        List<StringBuilder> text = new ArrayList<>();
        StringBuilder paragraph;
        paragraph = paragraphBuilderService.build(textScheme);
        paragraphBuilderService.makeFirstParagraph(paragraph,textScheme);
        text.add(paragraph);

        for (int i = 1; i <  textScheme.getTotalParagraphs(); i++){
            paragraph = paragraphBuilderService.build(textScheme);
            text.add(paragraph);
        }
        contentCounterService.incNumberOfParagraphs(textScheme.getTotalParagraphs());
        return text;
    }




}
