package com.aniamadej.loremipsum.Models.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="text")
public class GeneratedTextDescriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int textId;
    private int numberOfWords;
    private int numberOfSentences;
    private int numberOfParagraphs;

    @Temporal(TemporalType.TIMESTAMP)
    private Date added = new Date();
}
