package com.aniamadej.loremipsum.Controllers;

import com.aniamadej.loremipsum.Models.Dtos.StatisticsModel;
import com.aniamadej.loremipsum.Models.Entities.GeneratedTextDescriptionEntity;
import com.aniamadej.loremipsum.Repositories.GeneratedTextDescriptionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ListController {

    private GeneratedTextDescriptionRepository generatedTextDescriptionRepository;

    @Autowired
    public ListController(GeneratedTextDescriptionRepository generatedTextDescriptionRepository) {
        this.generatedTextDescriptionRepository = generatedTextDescriptionRepository;
    }

    @GetMapping ("/list")
    public String generateList(Model model){
        List<GeneratedTextDescriptionEntity> textsEntities = generatedTextDescriptionRepository.findTop50ByOrderByAddedDesc();
        ModelMapper textsToDto = new ModelMapper();
        List<StatisticsModel> texts = new ArrayList<>();
        StatisticsModel statisticsModel;
        for (GeneratedTextDescriptionEntity textEntity : textsEntities){
            statisticsModel = new StatisticsModel();
            textsToDto.map(textEntity, statisticsModel);
            texts.add(statisticsModel);
        }
        System.out.println(texts.get(0).getAdded());
        model.addAttribute("texts", texts);

        int numberOfWords = generatedTextDescriptionRepository.getNumberOfWordsSum();
        int numberOfSentences = generatedTextDescriptionRepository.getNumberOfSentencesSum();
        int numberOfParagraphs = generatedTextDescriptionRepository.getNumberOfParagraphsSum();
        model.addAttribute("statisticsTableModel", new StatisticsModel(numberOfWords, numberOfSentences, numberOfParagraphs));



        return "list";
    }
}
