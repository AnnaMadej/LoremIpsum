package com.aniamadej.loremipsum.Models.Dtos;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class GeneraratedTextStatsWithDateDto extends GeneratedTextStatsDto {
    private Date added = new Date();
}
