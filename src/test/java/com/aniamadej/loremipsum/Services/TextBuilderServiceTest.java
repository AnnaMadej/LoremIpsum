package com.aniamadej.loremipsum.Services;

import com.aniamadej.loremipsum.Models.TextScheme;
import com.aniamadej.loremipsum.Models.Words;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.SecureRandom;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TextBuilderServiceTest {

    TextScheme textScheme = new TextScheme
            (Words.LOREM_IPSUM, 1,1,1,1,1);

    SecureRandom rand = new SecureRandom();


    @Autowired
    TextBuilderService textBuilderService;

    @MockBean
    ParagraphBuilderService paragraphBuilderService;

    @MockBean
    TextSchemeService textSchemeService;

    @MockBean
    TextContentCounterService textContentCounterService;

    @Before
    public void buildParagraphs(){
        Mockito.when(paragraphBuilderService.get())
                .thenReturn(new StringBuilder("First paragraph! "))
                .thenReturn(new StringBuilder("next paragraphs."));
    }

    @Test
    public void shouldReturnCertainAmountOfParagraphs(){
        int numberOfParagraphsToGenerate = setNumberOfParagraphsToGenerate();

        Mockito.when(textSchemeService.getTextScheme()).thenReturn(textScheme);

        List<StringBuilder> paragraphs = textBuilderService.get();
        Assert.assertEquals(numberOfParagraphsToGenerate, paragraphs.size());
    }

    @Test
    public void shouldAddStartingPhrase(){

        Mockito.when(textSchemeService.getTextScheme()).thenReturn(textScheme);
        List<StringBuilder> paragraphs = textBuilderService.get();
        Assert.assertTrue(paragraphs.get(0).toString()
                                           .startsWith(textScheme.getWordsType()
                                           .getStartingPhrase()));
    }

    @Test
    public void shouldAddNumberOfWordsFromStartingPhraseToCounter(){

        Mockito.when(textSchemeService.getTextScheme()).thenReturn(textScheme);
        int numberOfWordsInTextCounter = 3;
        Mockito.when(textContentCounterService.getNumberOfWords())
                .thenReturn(numberOfWordsInTextCounter).thenCallRealMethod();
        textBuilderService.get();

        Mockito.verify(textContentCounterService,
                Mockito.times(1))
                .setNumberOfWords(textScheme.getWordsType()
                        .getStartingPhrase()
                        .split(" ")
                                        .length+numberOfWordsInTextCounter);

    }

    @Test
    public void shouldAddNumberOfParagraphsToTextCounter(){
        int numberOfParagraphsToGenerate = setNumberOfParagraphsToGenerate();

        Mockito.when(textSchemeService.getTextScheme()).thenReturn(textScheme);

        int numberOfParagraphsInTextCounter = 3;
        Mockito.when(textContentCounterService.getNumberOfParagraphs())
                .thenReturn(numberOfParagraphsInTextCounter).thenCallRealMethod();
        textBuilderService.get();

        Mockito.verify(textContentCounterService,
                Mockito.times(1))
                .setNumberOfParagraphs(numberOfParagraphsToGenerate
                                        +numberOfParagraphsInTextCounter);
    }

    private int setNumberOfParagraphsToGenerate() {
        int numberOfParagraphsToGenerate = rand.nextInt(100 - 1 + 1) + 1;
        textScheme.setTotalParagraphs(numberOfParagraphsToGenerate);
        return numberOfParagraphsToGenerate;
    }
}
