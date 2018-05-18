package com.aniamadej.loremipsum.Services;

import com.aniamadej.loremipsum.Models.Forms.LoremFormModel;
import com.aniamadej.loremipsum.Models.TextScheme;
import com.aniamadej.loremipsum.Models.Words;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
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
public class SentenceBuilderServiceTest {

    @Autowired
    SentenceBuilderService sentenceBuilderService;

    @MockBean
    TextSchemeService textSchemeService;

    @Test
    public void shouldReturnCertainNumberOfWords() {

        SecureRandom rand = new SecureRandom();
        int numberOfWordsToGenerate = rand.nextInt(100);
        TextScheme textScheme = new TextScheme(Words.LOREM_IPSUM, 1, 1, 1, numberOfWordsToGenerate, numberOfWordsToGenerate);
        Mockito.when(textSchemeService.getTextScheme()).thenReturn(textScheme);
        StringBuilder sentence = sentenceBuilderService.get();
        int numberOfWords = sentence.toString().split(" ").length;
        Assert.assertEquals(numberOfWordsToGenerate, numberOfWords);

    }

    @Test
    public void shouldEndWithPunctationMarkAndSpace() {
        TextScheme textScheme = new TextScheme(Words.LOREM_IPSUM, 1, 1, 1, 1, 1);
        Mockito.when(textSchemeService.getTextScheme()).thenReturn(textScheme);
        StringBuilder sentence = sentenceBuilderService.get();
        int numberOfWords = sentence.toString().split(" ").length;
        char lastChar= sentence.charAt(sentence.length()-2);
        Assert.assertTrue(lastChar=='.'||lastChar=='!'||lastChar=='?');
        char endingSpace= sentence.charAt(sentence.length()-1);
        Assert.assertTrue(endingSpace==' ');

    }
}