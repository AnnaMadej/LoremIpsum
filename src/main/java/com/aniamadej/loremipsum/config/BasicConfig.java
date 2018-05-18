package com.aniamadej.loremipsum.config;
import com.aniamadej.loremipsum.Models.Forms.LoremFormModel;
import com.aniamadej.loremipsum.Models.Size;
import com.aniamadej.loremipsum.Models.TextScheme;
import com.aniamadej.loremipsum.Models.Words;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.SourceGetter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasicConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        Converter<LoremFormModel, TextScheme> loremFormModelTextSchemeConverter = new AbstractConverter<LoremFormModel, TextScheme>() {
            protected TextScheme convert(LoremFormModel loremFormModel) {
                TextScheme ts = new TextScheme();
                if (loremFormModel != null){
                    ts.setMaxParSize(Size.getSizeFromName(loremFormModel.getParLength()).getMax());
                    ts.setMinParSize(Size.getSizeFromName(loremFormModel.getParLength()).getMin());
                    ts.setMaxSenSize(Size.getSizeFromName(loremFormModel.getSenLength()).getMax());
                    ts.setMinSenSize(Size.getSizeFromName(loremFormModel.getSenLength()).getMin());
                    ts.setTotalParagraphs(loremFormModel.getNumberOf());
                    ts.setWordsType(Words.getTextFromName(loremFormModel.getContent()));
                }
               return ts;
            }
        };

        modelMapper.addConverter(loremFormModelTextSchemeConverter);

        return modelMapper;
    }
}
