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

        if (textsEntities.size()>0) {
            StatisticsModel statisticsModel;
            for (GeneratedTextDescriptionEntity textEntity : textsEntities) {
                statisticsModel = new StatisticsModel();
                textsToDto.map(textEntity, statisticsModel);
                texts.add(statisticsModel);
            }
        }
        model.addAttribute("texts", texts);

        StatisticsModel statisticsTableModel = new StatisticsModel();

        if (generatedTextDescriptionRepository.getNumberOfWordsSum() != null) {
            statisticsTableModel.setNumberOfWords(generatedTextDescriptionRepository.getNumberOfWordsSum());
            statisticsTableModel.setNumberOfSentences(generatedTextDescriptionRepository.getNumberOfSentencesSum());
            statisticsTableModel.setNumberOfParagraphs(generatedTextDescriptionRepository.getNumberOfParagraphsSum());
        }

        model.addAttribute("statisticsTableModel", statisticsTableModel);



        return "list";
    }
}
