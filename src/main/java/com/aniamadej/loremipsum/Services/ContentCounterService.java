package com.aniamadej.loremipsum.Services;

import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(scopeName = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ContentCounterService {

@Getter
private int numberOfParagraphs;
@Getter
private  int numberOfWords;
@Getter
private  int numberOfSentences;

public void incNumberOfParagraphs(int value){
    this.numberOfParagraphs+=value;
}
public void incNumberOfSentences(int value){
    this.numberOfSentences+=value;
}

public void incNumberOfWords(int value){
    this.numberOfWords+=value;
}

}
