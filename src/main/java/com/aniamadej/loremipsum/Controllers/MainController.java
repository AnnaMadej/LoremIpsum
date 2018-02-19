package com.aniamadej.loremipsum.Controllers;
import com.aniamadej.loremipsum.Models.Entities.GeneratedTextDescriptionModel;
import com.aniamadej.loremipsum.Models.Forms.LoremFormModel;
import com.aniamadej.loremipsum.Models.Dtos.TextSchemeModel;
import com.aniamadej.loremipsum.Repositories.HistoricalTextDescriptionRepository;
import com.aniamadej.loremipsum.Services.ContentCounterService;
import com.aniamadej.loremipsum.Services.TextBuilderService;
import com.aniamadej.loremipsum.Models.Dtos.StatisticsTableModel;
import com.aniamadej.loremipsum.Services.FormToTextSchemeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    HistoricalTextDescriptionRepository historicalTextDescriptionRepository;
    @Autowired
    TextBuilderService textBuilderService;

    @Autowired
    FormToTextSchemeMapper formToTextSchemeMapper;

    @Autowired
    ContentCounterService contentCounterService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("loremFormModel", new LoremFormModel());
        Integer numberOfWordsSum = historicalTextDescriptionRepository.getNumberOfWordsSum();
        Integer numberOfSentencesSum = historicalTextDescriptionRepository.getNumberOfSentencesSum();
        Integer numberOfParagraphsSum = historicalTextDescriptionRepository.getNumberOfParagraphsSum();
        model.addAttribute("statisticsTableModel", new StatisticsTableModel(numberOfWordsSum, numberOfSentencesSum, numberOfParagraphsSum));
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
            TextSchemeModel textSchemeModel = formToTextSchemeMapper.mapp(loremFormModel);
            List<StringBuilder> text = textBuilderService.buildText(textSchemeModel);

            model.addAttribute("paragraphs", text);


            GeneratedTextDescriptionModel generatedTextDescription = new GeneratedTextDescriptionModel();
            generatedTextDescription.setNumberOfWords(contentCounterService.getNumberOfWords());
            generatedTextDescription.setNumberOfSentences(contentCounterService.getNumberOfSentences());
            generatedTextDescription.setNumberOfParagraphs(contentCounterService.getNumberOfParagraphs());
            model.addAttribute("generatedTextModel", generatedTextDescription);
            historicalTextDescriptionRepository.save(generatedTextDescription);
        }

        model.addAttribute("statisticsTableModel", new StatisticsTableModel(historicalTextDescriptionRepository.getNumberOfWordsSum(), historicalTextDescriptionRepository.getNumberOfSentencesSum(), historicalTextDescriptionRepository.getNumberOfParagraphsSum()));
        return "index";
    }
}
