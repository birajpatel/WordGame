package com.birin.wordgame.domain.words;

/**
 Created by Biraj on 9/13/16.
 */
public class Word {

    private final String englishWord;
    private final String spanishWord;
    private final boolean isCorrect;

    public Word(String englishWord, String spanishWord, boolean isCorrect) {
        this.englishWord = englishWord;
        this.spanishWord = spanishWord;
        this.isCorrect = isCorrect;
    }


    public String getEnglishWord() {
        return englishWord;
    }

    public String getSpanishWord() {
        return spanishWord;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    // Could use AutoValue ;)

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Word word = (Word) o;

        if (isCorrect != word.isCorrect) {
            return false;
        }
        if (!englishWord.equals(word.englishWord)) {
            return false;
        }
        return spanishWord.equals(word.spanishWord);

    }

    @Override
    public int hashCode() {
        int result = englishWord.hashCode();
        result = 31 * result + spanishWord.hashCode();
        result = 31 * result + (isCorrect ? 1 : 0);
        return result;
    }
}
