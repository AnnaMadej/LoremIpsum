package com.aniamadej.loremipsum.Controllers;

import com.aniamadej.loremipsum.Models.Entities.GeneratedTextDescriptionEntity;
import com.aniamadej.loremipsum.Repositories.GeneratedTextDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class ListController {

    private GeneratedTextDescriptionRepository generatedTextDescriptionRepository;

    @Autowired
    public ListController(GeneratedTextDescriptionRepository generatedTextDescriptionRepository) {
        this.generatedTextDescriptionRepository = generatedTextDescriptionRepository;
    }

    public String generateList(Model model){

        List<GeneratedTextDescriptionEntity> texts = generatedTextDescriptionRepository.findAll();

        model.addAttribute("texts", texts);

        return "list";
    }
}
