package com.aniamadej.loremipsum.Services;
import com.aniamadej.loremipsum.Models.Forms.LoremFormModel;
import com.aniamadej.loremipsum.Models.TextScheme;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@Scope(scopeName = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TextSchemeService {

    @Autowired
    private ModelMapper modelMapper;

    @Getter
    @Setter
    private TextScheme textScheme;

    public void setTextScheme(LoremFormModel loremFormModel){
        this.textScheme = modelMapper.map(loremFormModel, TextScheme.class);
    }

    public void setTextScheme(TextScheme textScheme){
        this.textScheme = textScheme;
    }


}
