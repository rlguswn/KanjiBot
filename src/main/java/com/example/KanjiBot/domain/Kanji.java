package com.example.KanjiBot.domain;

public class Kanji {

    private Long id;
    private String kanjiCharacter;
    private String meaning;
    private String reading;
    private String exampleSentence;

    protected Kanji() {
    }

    public Kanji(String kanjiCharacter, String meaning, String reading, String exampleSentence) {
        this.kanjiCharacter = kanjiCharacter;
        this.meaning = meaning;
        this.reading = reading;
        this.exampleSentence = exampleSentence;
    }

    public Long getId() {
        return id;
    }

    public String getKanjiCharacter() {
        return kanjiCharacter;
    }

    public void setKanjiCharacter(String kanjiCharacter) {
        this.kanjiCharacter = kanjiCharacter;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public String getExampleSentence() {
        return exampleSentence;
    }

    public void setExampleSentence(String exampleSentence) {
        this.exampleSentence = exampleSentence;
    }
}
