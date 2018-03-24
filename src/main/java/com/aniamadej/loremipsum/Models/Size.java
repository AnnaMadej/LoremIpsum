package com.aniamadej.loremipsum.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor

public enum Size {
    MIX_PAR_SIZE("mixedParSize", 2, 100),
    S_PAR_SIZE("shortParSize", 2, 10),
    M_PAR_SIZE("mediumParSize", 11, 50),
    L_PAR_SIZE("longParSize", 50,100),

    MIX_SEN_SIZE("mixedSenSize", 2, 25),
    S_SEN_SIZE("shortSenSize", 2, 5),
    M_SEN_SIZE("mediumSenSize", 6, 10),
    L_SEN_SIZE("longSenSize", 11, 20);

    @Getter
    String name;
    @Getter
    Integer min;
    @Getter
    Integer max;

    public static Size getSizeFromName(String name){
        return Arrays.stream(Size.values()).filter(val-> val.name.equals(name)).findFirst().orElse(MIX_SEN_SIZE);
    }
}

