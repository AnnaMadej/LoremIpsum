package com.aniamadej.loremipsum.Models.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="text")
public class GeneratedTextDescriptionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int textId;
    private int numberOfWords;
    private int numberOfSentences;
    private int numberOfParagraphs;
}
