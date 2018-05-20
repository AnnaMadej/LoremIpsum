package com.aniamadej.loremipsum.Services;

import com.aniamadej.loremipsum.Models.Dtos.GeneratedTextStatsDto;
import com.aniamadej.loremipsum.Models.Dtos.GeneratedTextStatsWithDateDto;
import com.aniamadej.loremipsum.Models.Entities.GeneratedTextDescriptionEntity;
import com.aniamadej.loremipsum.Models.Repositories.GeneratedTextDescriptionRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class StatisticsServiceTest {

    @MockBean
    GeneratedTextDescriptionRepository generatedTextDescriptionRepository;

    @MockBean
    TextContentCounterService textContentCounterService;

    @Autowired
    StatisticsService statisticsService;

    @Test
    public void shouldSaveCorrectTextStatisticsOneTime(){

        int numberOfParagraphs = 1;
        int numberOfWords = 10;
        int numberOfSentences = 5;

        Mockito.when(textContentCounterService.getNumberOfParagraphs())
                .thenReturn(numberOfParagraphs);
        Mockito.when(textContentCounterService.getNumberOfWords())
                .thenReturn(numberOfWords);
        Mockito.when(textContentCounterService.getNumberOfSentences())
                .thenReturn(numberOfSentences);

        statisticsService.saveSingleGeneratedTextStats();

        ArgumentCaptor<GeneratedTextDescriptionEntity> argEntity
                = ArgumentCaptor
                .forClass(GeneratedTextDescriptionEntity.class);

        Mockito.verify(generatedTextDescriptionRepository,
                        Mockito.times(1))
                .save(argEntity.capture());

        Assert.assertEquals(numberOfWords, argEntity.getValue()
                                            .getNumberOfWords());
        Assert.assertEquals(numberOfSentences, argEntity.getValue()
                                                .getNumberOfSentences());
        Assert.assertEquals(numberOfParagraphs, argEntity.getValue()
                                                .getNumberOfParagraphs());

    }

    @Test
    public void shouldReturnCorrectGenTextStatsDto(){
        int numberOfParagraphs = 1;
        int numberOfWords = 10;
        int numberOfSentences = 5;

        Mockito.when(textContentCounterService.getNumberOfParagraphs())
                .thenReturn(numberOfParagraphs);
        Mockito.when(textContentCounterService.getNumberOfWords())
                .thenReturn(numberOfWords);
        Mockito.when(textContentCounterService.getNumberOfSentences())
                .thenReturn(numberOfSentences);

        GeneratedTextStatsDto generatedTextStatsDto
                = statisticsService.saveSingleGeneratedTextStats();

        Assert.assertEquals(numberOfParagraphs, generatedTextStatsDto
                                                .getNumberOfParagraphs());

        Assert.assertEquals(numberOfSentences, generatedTextStatsDto
                .getNumberOfSentences());

        Assert.assertEquals(numberOfWords, generatedTextStatsDto
                .getNumberOfWords());
    }

    @Test
    public void shouldReturnCorrectListOfGeneratedTextStatsWithDateDto(){
        List<GeneratedTextDescriptionEntity> textsStatsEntities = new ArrayList<>();
        GeneratedTextDescriptionEntity generatedTextDescriptionEntity1
                = new GeneratedTextDescriptionEntity(1,1,1,1,new Date());
        GeneratedTextDescriptionEntity generatedTextDescriptionEntity2
                = new GeneratedTextDescriptionEntity(2,2,2,2,new Date());
        GeneratedTextDescriptionEntity generatedTextDescriptionEntity3
                = new GeneratedTextDescriptionEntity(3,3,3,3,new Date());

        textsStatsEntities.add(generatedTextDescriptionEntity1);
        textsStatsEntities.add(generatedTextDescriptionEntity2);
        textsStatsEntities.add(generatedTextDescriptionEntity3);

        Mockito.when(generatedTextDescriptionRepository.findTop50ByOrderByAddedDesc())
                .thenReturn(textsStatsEntities);

        List<GeneratedTextStatsWithDateDto> generatedTextStatsWithDateDtos
                = statisticsService.getListOfGeneratedTexStats();

        Assert.assertEquals(textsStatsEntities.size(),generatedTextStatsWithDateDtos.size());

        textsStatsEntities.forEach(GeneratedTextDescriptionEntity ->
                Assert.assertTrue(generatedTextStatsWithDateDtos.stream()
                        .anyMatch(GeneratedTextStatsDto ->
                 GeneratedTextStatsDto.getNumberOfWords()
                         == GeneratedTextDescriptionEntity.getNumberOfWords()
                 &&
                 GeneratedTextStatsDto.getNumberOfSentences()
                         == GeneratedTextDescriptionEntity.getNumberOfSentences()
                 &&
                 GeneratedTextStatsDto.getNumberOfParagraphs()
                         == GeneratedTextDescriptionEntity.getNumberOfParagraphs()
                         &&
                         GeneratedTextStatsDto.getAdded()
                                 == GeneratedTextDescriptionEntity.getAdded()

                        )));
    }


}