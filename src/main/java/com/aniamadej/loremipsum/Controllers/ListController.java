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
        List<GeneratedTextDescriptionEntity> textsEntities = generatedTextDescriptionRepository.findAll();
        ModelMapper textsToDto = new ModelMapper();
        StatisticsModel statisticsModel = new StatisticsModel();
        List<StatisticsModel> texts = new ArrayList<>();

        for (GeneratedTextDescriptionEntity textEntity : textsEntities){
            textsToDto.map(textEntity, statisticsModel);
            texts.add(statisticsModel);
        }
        System.out.println(texts.get(0).getAdded());
        model.addAttribute("texts", texts);
        return "list";
    }
}
