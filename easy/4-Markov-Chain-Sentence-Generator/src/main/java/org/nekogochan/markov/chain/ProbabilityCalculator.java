package org.nekogochan.markov.chain;

import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ProbabilityCalculator {

    private HashMap<String, HashMap<String, Double>> probabilities;

    private String[][] words;

    public HashMap<String, HashMap<String, Double>> getProbabilities(String text) {
        probabilities = new HashMap<>();
        setWords(text);
        setProbabilities();
        distribute();
        return probabilities;
    }

    private void setWords(String text) {
        text = text.strip()
                .replace('\n', ' ')
                .replaceAll("[\\p{Punct}&&[^.]]", "")
                .replaceAll("[\s]+", " ")
                .replaceAll("[.]+", ".")
                ;

        String[] sentences = text.split("\\.");

        words = new String[sentences.length][];
        for (int i = 0; i < sentences.length; i++) {
            words[i] = sentences[i].trim().split(" ");
            for (int j = 0; j < words[i].length; j++) {
                words[i][j] = words[i][j].toLowerCase();
            }
        }
    }

    private void setProbabilities() {
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words[i].length; j++) {
                appendWord(i, j);
            }
        }
        normalizeProbabilities();
    }

    private void appendWord(int i, int j) {
        if (j == 0) {
            appendToBeginning(i, j);
        } else if (j == words[i].length - 1) {
            appendToMiddle(i, j);
            appendToEnd(i, j);
        } else {
            appendToMiddle(i, j);
        }
    }

    // '.' -> this
    private void appendToBeginning(int i, int j) {
        appendBound(".", words[i][j]);
    }

    // this -> '.'
    private void appendToEnd(int i, int j) {
        appendBound(words[i][j], ".");
    }

    // prev -> this
    private void appendToMiddle(int i, int j) {
        appendBound(words[i][j - 1], words[i][j]);
    }

    private void appendBound(String first, String second) {
        probabilities.putIfAbsent(first, new HashMap<>());
        appendProbability(first, second);
    }

    private void appendProbability(String first, String second) {
        HashMap<String, Double> map = probabilities.get(first);
        map.putIfAbsent(second, 0.0);
        map.merge(second, 1.0, Double::sum);
    }

    private void normalizeProbabilities() {
        for (var probabilitiesEntry : probabilities.entrySet()) {
            MathUtils.normalizeProbabilities(probabilitiesEntry.getValue());
        }
    }

    private void distribute() {
        for (var entry : probabilities.entrySet()) {
            Map<String, Double> map = entry.getValue();
            AtomicReference<Double> current = new AtomicReference<>(0.0);
            for (var key : map.keySet()) {
                map.merge(key, current.get(), (old, extra) -> {
                    current.set(old + extra);
                    return current.get();
                });
            }
        }
    }
}
