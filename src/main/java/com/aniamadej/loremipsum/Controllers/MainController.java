package com.aniamadej.loremipsum.Controllers;
import com.aniamadej.loremipsum.Models.Entities.GeneratedTextDescriptionEntity;
import com.aniamadej.loremipsum.Models.Forms.LoremFormModel;
import com.aniamadej.loremipsum.Models.TextScheme;
import com.aniamadej.loremipsum.Repositories.GeneratedTextDescriptionRepository;
import com.aniamadej.loremipsum.Services.ContentCounterService;
import com.aniamadej.loremipsum.Services.TextBuilderService;
import com.aniamadej.loremipsum.Models.Dtos.StatisticsModel;
import com.aniamadej.loremipsum.Services.FormToTextSchemeMapper;
import org.modelmapper.ModelMapper;
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
        StatisticsModel statisticsTableModel = new StatisticsModel();

        if (generatedTextDescriptionRepository.getNumberOfWordsSum() != null) {
            statisticsTableModel.setNumberOfWords(generatedTextDescriptionRepository.getNumberOfWordsSum());
            statisticsTableModel.setNumberOfSentences(generatedTextDescriptionRepository.getNumberOfSentencesSum());
            statisticsTableModel.setNumberOfParagraphs(generatedTextDescriptionRepository.getNumberOfParagraphsSum());
        }

        model.addAttribute("statisticsTableModel", statisticsTableModel);
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
            TextScheme textScheme = formToTextSchemeMapper.mapp(loremFormModel);
            List<StringBuilder> text = textBuilderService.build(textScheme);

            model.addAttribute("paragraphs", text);

            GeneratedTextDescriptionEntity generatedTextDescription = new GeneratedTextDescriptionEntity();
            generatedTextDescription.setNumberOfWords(contentCounterService.getNumberOfWords());
            generatedTextDescription.setNumberOfSentences(contentCounterService.getNumberOfSentences());
            generatedTextDescription.setNumberOfParagraphs(contentCounterService.getNumberOfParagraphs());
            generatedTextDescriptionRepository.save(generatedTextDescription);

            StatisticsModel textStatistics = new StatisticsModel();
            ModelMapper statisticsToDto = new ModelMapper();
            statisticsToDto.map(generatedTextDescription, textStatistics);

            model.addAttribute("textStatistics", textStatistics);
        }

        model.addAttribute("statisticsTableModel", new StatisticsModel(generatedTextDescriptionRepository.getNumberOfWordsSum(), generatedTextDescriptionRepository.getNumberOfSentencesSum(), generatedTextDescriptionRepository.getNumberOfParagraphsSum()));
        return "index";
    }
}
