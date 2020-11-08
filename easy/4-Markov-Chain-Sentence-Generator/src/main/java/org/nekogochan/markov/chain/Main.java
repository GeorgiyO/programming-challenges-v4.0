package org.nekogochan.markov.chain;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        ProbabilityCalculator calculator = new ProbabilityCalculator();
        ShitGenerator generator = new ShitGenerator();

        String text = "В центре стопы слона есть жировая подушка, которая каждый раз, когда слон опускает ногу, «расплющивается», увеличивая площадь опоры. На верхней челюсти у слонов есть бивни — видоизменённые резцы, растущие в течение всей жизни животного. Обычно слоны либо имеют два бивня, либо не имеют их вообще (у африканских слонов бивни имеют и самцы и самки, у индийских — только самцы). С помощью бивней они обдирают кору с деревьев и рыхлят землю в поисках соли, а также наносят раны хищникам.";

        Map<String, HashMap<String, Double>> markovChain = calculator.getProbabilities(text);
        System.out.println(markovChain);

        generator.setMarkovChain(text);

        System.out.println("\n\n\n");

        System.out.println(generator.getShitpost(4));
    }
}
