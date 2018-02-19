package com.aniamadej.loremipsum.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
    int min;
    @Getter
    int max;

    public static Size getSizeFromName(String name){
        for (Size size : Size.values()) {
            if (size.name.equals(name)) {
                return size;
            }
        }
        return MIX_SEN_SIZE;
    }
}

