package com.aniamadej.loremipsum.Services;
import com.aniamadej.loremipsum.Models.Dtos.GeneratedTextStatsDto;
import com.aniamadej.loremipsum.Models.Entities.GeneratedTextDescriptionEntity;
import com.aniamadej.loremipsum.Repositories.GeneratedTextDescriptionRepository;
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

        GeneratedTextStatsDto generatedTextsStatsDto = new GeneratedTextStatsDto();
        if (generatedTextDescriptionRepository.getNumberOfWordsSum() != null) {
            generatedTextsStatsDto.setNumberOfWords(generatedTextDescriptionRepository.getNumberOfWordsSum());
            generatedTextsStatsDto.setNumberOfSentences(generatedTextDescriptionRepository.getNumberOfSentencesSum());
            generatedTextsStatsDto.setNumberOfParagraphs(generatedTextDescriptionRepository.getNumberOfParagraphsSum());
        }
        return generatedTextsStatsDto;
    }

    public GeneratedTextStatsDto getSingleGeneratedTextStats(){
        GeneratedTextDescriptionEntity generatedTextDescription = new GeneratedTextDescriptionEntity();
        generatedTextDescription.setNumberOfWords(textContentCounterService.getNumberOfWords());
        generatedTextDescription.setNumberOfSentences(textContentCounterService.getNumberOfSentences());
        generatedTextDescription.setNumberOfParagraphs(textContentCounterService.getNumberOfParagraphs());
        generatedTextDescriptionRepository.save(generatedTextDescription);

        GeneratedTextStatsDto textStatistics = new GeneratedTextStatsDto();
        modelMapper.map(generatedTextDescription, textStatistics);
        return textStatistics;
    }

    public List<GeneratedTextStatsDto> getListOfGeneratedTexStats(){
        List<GeneratedTextDescriptionEntity> textsEntities = generatedTextDescriptionRepository.findTop50ByOrderByAddedDesc();
        List<GeneratedTextStatsDto> texts = new ArrayList<>();

        if (textsEntities.size()>0) {
            GeneratedTextStatsDto generatedTextsDto;
            for (GeneratedTextDescriptionEntity textEntity : textsEntities) {
                generatedTextsDto = new GeneratedTextStatsDto();
                modelMapper.map(textEntity, generatedTextsDto);
                texts.add(generatedTextsDto);
            }
        }

        return texts;
    }


}
