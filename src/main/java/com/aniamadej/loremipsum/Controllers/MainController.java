package com.aniamadej.loremipsum.Controllers;
import com.aniamadej.loremipsum.Models.Entities.GeneratedTextDescriptionEntity;
import com.aniamadej.loremipsum.Models.Forms.LoremFormModel;
import com.aniamadej.loremipsum.Models.Dtos.TextSchemeModel;
import com.aniamadej.loremipsum.Repositories.GeneratedTextDescriptionRepository;
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

    private GeneratedTextDescriptionRepository generatedTextDescriptionRepository;
    private TextBuilderService textBuilderService;
    private FormToTextSchemeMapper formToTextSchemeMapper;
    private ContentCounterService contentCounterService;

    @Autowired
    public MainController(GeneratedTextDescriptionRepository generatedTextDescriptionRepository, TextBuilderService textBuilderService, FormToTextSchemeMapper formToTextSchemeMapper, ContentCounterService contentCounterService) {
        this.generatedTextDescriptionRepository = generatedTextDescriptionRepository;
        this.textBuilderService = textBuilderService;
        this.formToTextSchemeMapper = formToTextSchemeMapper;
        this.contentCounterService = contentCounterService;
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("loremFormModel", new LoremFormModel());
        Integer numberOfWordsSum = generatedTextDescriptionRepository.getNumberOfWordsSum();
        Integer numberOfSentencesSum = generatedTextDescriptionRepository.getNumberOfSentencesSum();
        Integer numberOfParagraphsSum = generatedTextDescriptionRepository.getNumberOfParagraphsSum();
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


            GeneratedTextDescriptionEntity generatedTextDescription = new GeneratedTextDescriptionEntity();
            generatedTextDescription.setNumberOfWords(contentCounterService.getNumberOfWords());
            generatedTextDescription.setNumberOfSentences(contentCounterService.getNumberOfSentences());
            generatedTextDescription.setNumberOfParagraphs(contentCounterService.getNumberOfParagraphs());
            model.addAttribute("generatedTextModel", generatedTextDescription);
            generatedTextDescriptionRepository.save(generatedTextDescription);
        }

        model.addAttribute("statisticsTableModel", new StatisticsTableModel(generatedTextDescriptionRepository.getNumberOfWordsSum(), generatedTextDescriptionRepository.getNumberOfSentencesSum(), generatedTextDescriptionRepository.getNumberOfParagraphsSum()));
        return "index";
    }
}
