package com.aniamadej.loremipsum.Services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@Scope(scopeName = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TextContentCounterService {

private int numberOfParagraphs;
private  int numberOfWords;
private  int numberOfSentences;

}
