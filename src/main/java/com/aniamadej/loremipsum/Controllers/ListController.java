package com.aniamadej.loremipsum.Controllers;

import com.aniamadej.loremipsum.Services.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
