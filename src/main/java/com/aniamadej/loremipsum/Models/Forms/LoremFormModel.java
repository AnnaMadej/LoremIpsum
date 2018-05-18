package com.aniamadej.loremipsum.Models.Forms;

import lombok.*;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class LoremFormModel {
    @NumberFormat
    @NotNull
    Integer numberOf;
    String content;
    String parLength;
    String senLength;

    public LoremFormModel() {
        this.numberOf = null;
        this.content = "lorem";
        this.parLength = "mixedParSize";
        this.senLength = "mixedSenSize";
    }

}
