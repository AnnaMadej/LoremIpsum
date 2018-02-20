package com.aniamadej.loremipsum.Services;

import com.aniamadej.loremipsum.Models.TextScheme;

public interface LoremBuilder<T> {
    T build(TextScheme textScheme);
}
