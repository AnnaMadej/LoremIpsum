package com.aniamadej.loremipsum.Services;

import com.aniamadej.loremipsum.Models.Forms.LoremFormModel;
import com.aniamadej.loremipsum.Models.TextScheme;
import com.aniamadej.loremipsum.Models.Words;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.security.SecureRandom;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ParagraphBuilderServiceTest {

    @MockBean
    TextSchemeService textSchemeService;

    @MockBean
    SentenceBuilderService sentenceBuilderService;

    @MockBean
    TextContentCounterService textContentCounterService;

    @Autowired
    ParagraphBuilderService paragraphBuilderService;

    @Test
    public void shouldReturnCertainNumberOfSentences() {

        SecureRandom rand = new SecureRandom();
        int numberOfSentencesToGenerate = rand.nextInt(100 - 1 + 1) + 1;
        TextScheme textScheme = new TextScheme(Words.LOREM_IPSUM, 1, numberOfSentencesToGenerate, numberOfSentencesToGenerate, 3, 3);
        Mockito.when(textSchemeService.getTextScheme()).thenReturn(textScheme);
        Mockito.when(sentenceBuilderService.get()).thenReturn(new StringBuilder("Ala ma kota! "));
        StringBuilder paragraph = paragraphBuilderService.get();
        int numberOfSentences = paragraph.toString().split("[.?!] ").length;
        Assert.assertEquals(numberOfSentencesToGenerate, numberOfSentences);

    }

    @Test
    public void shouldAddNumberOfSentencesToTextCounter(){

        SecureRandom rand = new SecureRandom();
        int numberOfSentencesToGenerate = rand.nextInt(100 - 1 + 1) + 1;
        TextScheme textScheme = new TextScheme(Words.LOREM_IPSUM, 1, numberOfSentencesToGenerate, numberOfSentencesToGenerate, 0, 0);
        Mockito.when(textSchemeService.getTextScheme()).thenReturn(textScheme);
        Mockito.when(sentenceBuilderService.get()).thenReturn(new StringBuilder("Ala ma kota! "));
        Mockito.when(textContentCounterService.getNumberOfSentences()).thenReturn(3);

        paragraphBuilderService.get();
        Mockito.verify(textContentCounterService, Mockito.times(1)).setNumberOfSentences(3+numberOfSentencesToGenerate);

    }


}