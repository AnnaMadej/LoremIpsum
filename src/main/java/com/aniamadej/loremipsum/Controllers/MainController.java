package com.aniamadej.loremipsum.Controllers;
import com.aniamadej.loremipsum.Models.Forms.LoremFormModel;
import com.aniamadej.loremipsum.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.function.Supplier;

@Controller
public class MainController {

    private Supplier<List<StringBuilder>> loremBuilder;
    private StatisticsService statisticsService;
    private TextSchemeService textSchemeService;

    @Autowired
    public MainController(@Qualifier("TEXT_BUILDER") Supplier<List<StringBuilder>> loremBuilder,
                          StatisticsService statisticsService,
                          TextSchemeService textSchemeService) {
        this.loremBuilder = loremBuilder;
        this.statisticsService = statisticsService;
        this.textSchemeService = textSchemeService;
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("loremFormModel", new LoremFormModel());
        model.addAttribute("statisticsTableModel", statisticsService.getSumOfGeneratedTexts());
        return "index";
    }

    @PostMapping("/")
    public String index(@ModelAttribute("loremFormModel") @Valid LoremFormModel loremFormModel, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("loremFormModel", new LoremFormModel());
            model.addAttribute("error", true );
        }
        else {
            model.addAttribute("error", false );
            textSchemeService.setTextScheme(loremFormModel);
            model.addAttribute("paragraphs", loremBuilder.get());
            model.addAttribute("textStatistics", statisticsService.getSingleGeneratedTextStats());
        }
        model.addAttribute("statisticsTableModel", statisticsService.getSumOfGeneratedTexts());
        return "index";
    }
}
