package com.aniamadej.loremipsum.Controllers;
import com.aniamadej.loremipsum.Models.Entities.GeneratedTextDescriptionEntity;
import com.aniamadej.loremipsum.Models.Forms.LoremFormModel;
import com.aniamadej.loremipsum.Models.TextScheme;
import com.aniamadej.loremipsum.Repositories.GeneratedTextDescriptionRepository;
import com.aniamadej.loremipsum.Services.TextContentCounter;
import com.aniamadej.loremipsum.Services.LoremBuilder;
import com.aniamadej.loremipsum.Models.Dtos.StatisticsModel;
import com.aniamadej.loremipsum.Services.LoremFormMapper;
import org.modelmapper.ModelMapper;
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

@Controller
public class MainController {

    private GeneratedTextDescriptionRepository generatedTextDescriptionRepository;
    private LoremBuilder<List<StringBuilder>> loremBuilder;
    private LoremFormMapper<TextScheme> loremFormMapper;
    private TextContentCounter textContentCounter;

    @Autowired
    public MainController(GeneratedTextDescriptionRepository generatedTextDescriptionRepository,
                          @Qualifier("TEXT_BUILDER")LoremBuilder loremBuilder,
                          LoremFormMapper loremFormMapper,
                          TextContentCounter textContentCounter) {
        this.generatedTextDescriptionRepository = generatedTextDescriptionRepository;
        this.loremBuilder = loremBuilder;
        this.loremFormMapper = loremFormMapper;
        this.textContentCounter = textContentCounter;
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
            TextScheme textScheme = loremFormMapper.mapp(loremFormModel);
            List<StringBuilder> text = loremBuilder.build(textScheme);

            model.addAttribute("paragraphs", text);

            GeneratedTextDescriptionEntity generatedTextDescription = new GeneratedTextDescriptionEntity();
            generatedTextDescription.setNumberOfWords(textContentCounter.getNumberOfWords());
            generatedTextDescription.setNumberOfSentences(textContentCounter.getNumberOfSentences());
            generatedTextDescription.setNumberOfParagraphs(textContentCounter.getNumberOfParagraphs());
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
