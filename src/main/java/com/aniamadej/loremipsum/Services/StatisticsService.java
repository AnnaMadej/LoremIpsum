package com.aniamadej.loremipsum.Services;
import com.aniamadej.loremipsum.Models.Dtos.GeneratedTextStatsWithDateDto;
import com.aniamadej.loremipsum.Models.Dtos.GeneratedTextStatsDto;
import com.aniamadej.loremipsum.Models.Entities.GeneratedTextDescriptionEntity;
import com.aniamadej.loremipsum.Models.Repositories.GeneratedTextDescriptionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class StatisticsService {

    private GeneratedTextDescriptionRepository generatedTextDescriptionRepository;
    private TextContentCounterService textContentCounterService;
    private ModelMapper modelMapper;

    @Autowired
    public StatisticsService(GeneratedTextDescriptionRepository generatedTextDescriptionRepository,
                             TextContentCounterService textContentCounterService,
                             ModelMapper modelMapper) {
        this.generatedTextDescriptionRepository = generatedTextDescriptionRepository;
        this.textContentCounterService = textContentCounterService;
        this.modelMapper = modelMapper;
    }

    public GeneratedTextStatsDto getSumOfGeneratedTexts(){
        return generatedTextDescriptionRepository.getSumsOfGeneratedTexts();
    }

    public GeneratedTextStatsDto saveSingleGeneratedTextStats(){
        GeneratedTextDescriptionEntity generatedTextDescription = new GeneratedTextDescriptionEntity();
        generatedTextDescription.setNumberOfWords(textContentCounterService.getNumberOfWords());
        generatedTextDescription.setNumberOfSentences(textContentCounterService
                                                      .getNumberOfSentences());
        generatedTextDescription.setNumberOfParagraphs(textContentCounterService
                                                       .getNumberOfParagraphs());
        generatedTextDescriptionRepository.save(generatedTextDescription);

        GeneratedTextStatsDto textStatistics = new GeneratedTextStatsDto();
        modelMapper.map(generatedTextDescription, textStatistics);
        return textStatistics;
    }

    public List<GeneratedTextStatsWithDateDto> getListOfGeneratedTexStats(){
        List<GeneratedTextDescriptionEntity> textsEntities
                = generatedTextDescriptionRepository.findTop50ByOrderByAddedDesc();
        List<GeneratedTextStatsWithDateDto> textsStats = new ArrayList<>();

        if (textsEntities.size()>0) {
            GeneratedTextStatsWithDateDto generatedTextsDto;
            for (GeneratedTextDescriptionEntity textEntity : textsEntities) {
                generatedTextsDto = new GeneratedTextStatsWithDateDto();
                modelMapper.map(textEntity, generatedTextsDto);
                textsStats.add(generatedTextsDto);
            }
        }
        return textsStats;
    }



}
