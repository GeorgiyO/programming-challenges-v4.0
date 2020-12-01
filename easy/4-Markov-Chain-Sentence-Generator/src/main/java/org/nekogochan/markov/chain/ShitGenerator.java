package org.nekogochan.markov.chain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ShitGenerator {

    private ProbabilityCalculator calculator = new ProbabilityCalculator();
    private Map<String, HashMap<String, Double>> markovChain;
    private Random random = new Random();

    private String currentWord;
    private StringBuilder shitpost;

    private boolean first = true;


    public void setMarkovChain(String text) {
        markovChain = calculator.getProbabilities(text);
    }

    public String getShitpost(int sentences) {
        shitpost = new StringBuilder();
        setFirstWord();
        buildShitpost(sentences);
        return shitpost.toString();
    }

    private void setFirstWord() {
        Map<String, Double> map = markovChain.get(".");

        int index = random.nextInt(map.size());
        currentWord = new ArrayList<String>(map.keySet()).get(index);
        appendToShitpost(currentWord);
        first = false;
    }

    private void buildShitpost(int sentences) {
        while (sentences > 0) {
            buildSentence();
            sentences--;
        }
    }

    private void buildSentence() {
        do {
            appendNewWord();
        } while (!currentWord.equals("."));
    }

    private void appendNewWord() {
        Map<String, Double> map = markovChain.get(currentWord);
        double d = random.nextDouble();
        for (var entry : map.entrySet()) {
            if (entry.getValue() > d) {
                currentWord = entry.getKey();
                if (currentWord == ".") {
                    shitpost.append('.');
                    first = true;
                } else {
                    shitpost.append(' ');
                    appendToShitpost(currentWord);
                }
                break;
            }
        }
    }

    private void appendToShitpost(String str) {
        if (first) {
            shitpost.append("\n");
            str = str.substring(0, 1).toUpperCase() + str.substring(1);
            first = false;
        }
        shitpost.append(str);
    }
}
