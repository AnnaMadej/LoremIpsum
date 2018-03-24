package com.aniamadej.loremipsum.Controllers;

import com.aniamadej.loremipsum.Models.Dtos.GeneratedTextStatsDto;
import com.aniamadej.loremipsum.Models.Entities.GeneratedTextDescriptionEntity;
import com.aniamadej.loremipsum.Repositories.GeneratedTextDescriptionRepository;
import com.aniamadej.loremipsum.Services.StatisticsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ListController {

    private StatisticsService statisticsService;

    @Autowired
    public ListController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping ("/list")
    public String generateList(Model model){
        model.addAttribute("texts", statisticsService.getListOfGeneratedTexStats());
        model.addAttribute("statisticsTableModel", statisticsService.getSumOfGeneratedTexts());
        return "list";
    }
}
