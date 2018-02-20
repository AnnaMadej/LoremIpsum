package com.aniamadej.loremipsum.Services;

import com.aniamadej.loremipsum.Models.Forms.LoremFormModel;
import com.aniamadej.loremipsum.Models.TextScheme;

public interface LoremFormMapper<T> {
    T mapp(LoremFormModel loremFormModel);
}
