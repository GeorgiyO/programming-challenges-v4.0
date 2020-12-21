package org.nekogochan.markov.chain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ShitGenerator {

    private ProbabilityCalculator calculator = new ProbabilityCalculator();
    private Map<String, HashMap<String, Double>> markovChain;
    private Random random = new Random();

    private String current;
    private StringBuilder post;

    private boolean endOfSentence;

    public void setMarkovChain(String text) {
        markovChain = calculator.getProbabilities(text);
        LoggerFactory.getLogger(this.getClass()).info(markovChain.toString());
    }

    public String createPost(int sentences) {
        post = new StringBuilder();
        fillPost(sentences);
        return post.toString();
    }

    private void fillPost(int sentences) {
        setFirstWord();
        for (int i = 0; i < sentences; i++) {
            appendSentence();
        }
    }

    private void setFirstWord() {
        var map = markovChain.get(".");
        int i = random.nextInt(map.size());
        current = new ArrayList<>(map.keySet()).get(i);
    }

    private void appendSentence() {
        createSentenceBeginning();
        while (!endOfSentence) {
            createMiddleWord();
        }
    }

    private void createSentenceBeginning() {
        var dotMap = markovChain.get(".");
        var currentWordMap = markovChain.get(current);
        var merged = MathUtils.mergeProbabilityMaps(dotMap, currentWordMap);
        if (merged.size() == 0) merged = dotMap;

        var word = getRandWord(merged);
        current = word;
        try {
            appendToPost(word.substring(0, 1).toUpperCase() + word.substring(1));
        } catch (Exception e) {
            e.printStackTrace();
            LoggerFactory.getLogger(this.getClass() + ".createSentenceBeginning").info(current + " " + word + "\n" + merged);
        }
        endOfSentence = false;
    }

    private void createMiddleWord() {
        var map = markovChain.get(current);
        var word = map == null ? "." : getRandWord(map);
        if (word.equals(".")) {
            endOfSentence = true;
            post.append(word); // без пробела после слова и привязки current = word
        } else {
            appendToPost(word);
            current = word;
        }
    }

    private String getRandWord(Map<String, Double> source) {
        double d = random.nextDouble();
        for (var e : source.entrySet()) {
            if (e.getValue() > d) {
                return e.getKey();
            }
        }
        return (String) source.keySet().toArray()[0];
    }

    private void appendToPost(String word) {
        post.append(' ').append(word);
    }
}
