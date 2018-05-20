package com.aniamadej.loremipsum.Services;

import com.aniamadej.loremipsum.Models.TextScheme;
import com.aniamadej.loremipsum.Models.Words;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.security.SecureRandom;



@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SentenceBuilderServiceTest {

    TextScheme textScheme = new TextScheme(Words.LOREM_IPSUM, 1, 1, 1, 10, 50);

    @Autowired
    SentenceBuilderService sentenceBuilderService;

    @MockBean
    TextSchemeService textSchemeService;

    @MockBean
    TextContentCounterService textContentCounterService;


    @Test
    public void shouldReturnCertainNumberOfWords() {

        int numberOfWordsToGenerate = setNumberOfWordsToGenerate();
        Mockito.when(textSchemeService.getTextScheme()).thenReturn(textScheme);
        StringBuilder sentence = sentenceBuilderService.get();
        int numberOfWords = sentence.toString().split(" ").length;
        Assert.assertEquals(numberOfWordsToGenerate, numberOfWords);

    }

    @Test
    public void shouldStartWithUpperCaseAndEndWithPunctationMarkAndSpace() {
        Mockito.when(textSchemeService.getTextScheme()).thenReturn(textScheme);
        StringBuilder sentence = sentenceBuilderService.get();
        String ending = sentence.toString().substring(sentence.length()-2);
        Assert.assertTrue(ending.matches("^[.!?] $"));
        Assert.assertTrue(Character.isUpperCase(sentence.charAt(0)));
    }

    @Test
    public void shouldntHaveTwoPunctationMarksNextToEachOther(){
        Mockito.when(textSchemeService.getTextScheme()).thenReturn(textScheme);
        StringBuilder sentence = sentenceBuilderService.get();
        Assert.assertFalse(sentence.toString().matches("^.*[.,?!][.,?!].*$"));
    }

    @Test
    public void shouldAddNumberOfWordsToTextCounter(){

        int numberOfWordsToGenerate = setNumberOfWordsToGenerate();

        Mockito.when(textSchemeService.getTextScheme()).thenReturn(textScheme);
        Mockito.when(textContentCounterService.getNumberOfWords()).thenReturn(3).thenCallRealMethod();

        sentenceBuilderService.get();
        Mockito.verify(textContentCounterService, Mockito.times(1)).setNumberOfWords(3+numberOfWordsToGenerate);

    }

    private int setNumberOfWordsToGenerate() {
        SecureRandom rand = new SecureRandom();
        int numberOfWordsToGenerate = rand.nextInt(100 - 1 + 1) + 1;
        textScheme.setMinSenSize(numberOfWordsToGenerate);
        textScheme.setMaxSenSize(numberOfWordsToGenerate);
        return numberOfWordsToGenerate;
    }
}