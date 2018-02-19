package com.aniamadej.loremipsum.Services;

import com.aniamadej.loremipsum.Models.Forms.LoremFormModel;
import com.aniamadej.loremipsum.Models.Size;
import com.aniamadej.loremipsum.Models.Dtos.TextSchemeModel;
import com.aniamadej.loremipsum.Models.Words;
import org.springframework.stereotype.Service;

@Service
public class FormToTextSchemeMapper {

    public TextSchemeModel mapp(LoremFormModel loremFormModel){

        TextSchemeModel textSchemeModel = new TextSchemeModel(
                        Words.getTextFromName(loremFormModel.getContent()),
                        loremFormModel.getNumberOf(),
                        Size.getSizeFromName(loremFormModel.getParLength()).getMin(),
                        Size.getSizeFromName(loremFormModel.getParLength()).getMax(),
                        Size.getSizeFromName(loremFormModel.getSenLength()).getMin(),
                        Size.getSizeFromName(loremFormModel.getSenLength()).getMax());
        return textSchemeModel;
    }
}
