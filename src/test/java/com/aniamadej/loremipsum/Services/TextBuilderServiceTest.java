package com.aniamadej.loremipsum.Services;

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

import java.security.SecureRandom;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TextBuilderServiceTest {

    TextScheme textScheme = new TextScheme
            (Words.LOREM_IPSUM, 1,1,1,1,1);

    @Autowired
    TextBuilderService textBuilderService;

    @MockBean
    ParagraphBuilderService paragraphBuilderService;

    @MockBean
    TextSchemeService textSchemeService;

    @Test
    public void shouldReturnCertainAmountOfParagraphs(){
        SecureRandom rand = new SecureRandom();
        int numberOfParagraphsToGenerate = rand.nextInt(100 - 1 + 1) + 1;
        textScheme.setTotalParagraphs(numberOfParagraphsToGenerate);

        Mockito.when(textSchemeService.getTextScheme()).thenReturn(textScheme);

        Mockito.when(paragraphBuilderService.get())
                .thenReturn(new StringBuilder("First paragraph! "))
                .thenReturn(new StringBuilder("next paragraphs!"));

        List<StringBuilder> paragraphs = textBuilderService.get();
        Assert.assertEquals(numberOfParagraphsToGenerate, paragraphs.size());
    }

    @Test
    public void shouldAddStartingPhrase(){
        TextScheme textScheme = new TextScheme(Words.LOREM_IPSUM, 1, 1, 1, 1, 1);

        Mockito.when(textSchemeService.getTextScheme()).thenReturn(textScheme);
        Mockito.when(paragraphBuilderService.get())
                .thenReturn(new StringBuilder("First paragraph! "))
                .thenReturn(new StringBuilder("next paragraphs!"));

        List<StringBuilder> paragraphs = textBuilderService.get();
        Assert.assertTrue(paragraphs.get(0).toString()
                                           .startsWith(textScheme.getWordsType()
                                           .getStartingPhrase()));
    }

}
