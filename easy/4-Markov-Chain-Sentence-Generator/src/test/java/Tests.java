import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.nekogochan.markov.chain.MathUtils;
import org.nekogochan.markov.chain.ProbabilityCalculator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Tests {

    String simpleText = "one two one three.";

    Map<String, HashMap<String, Double>> markovChain = new HashMap<>();

    ProbabilityCalculator probabilityCalculator = new ProbabilityCalculator();

    {
        HashMap<String, Double> map = new HashMap<>();
        markovChain.put("one", map);
        map.put("two", 0.5);
        map.put("three", 1.0);

        map = new HashMap<>();
        markovChain.put("two", map);
        map.put("one", 1.0);

        map = new HashMap<>();
        markovChain.put("three", map);
        map.put(".", 1.0);

        map = new HashMap<>();
        markovChain.put(".", map);
        map.put("one", 1.0);
    }

    @Test
    public void wordsSplitting() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        String text = "Bla, bla bla. Bla bla bla.";
        String[][] exceptedWords = {
                {"bla", "bla", "bla"},
                {"bla", "bla", "bla"},
        };

        Method setWords = probabilityCalculator.getClass().getDeclaredMethod("setWords", String.class);
        setWords.setAccessible(true);
        setWords.invoke(probabilityCalculator, text);

        Field field = probabilityCalculator.getClass().getDeclaredField("words");
        field.setAccessible(true);
        String[][] words = (String[][]) field.get(probabilityCalculator);

        assertEquals(Arrays.deepToString(exceptedWords), Arrays.deepToString(words));
    }

    @Test
    public void markovChainBuilding() {
        assertEquals(markovChain, probabilityCalculator.getProbabilities(simpleText));
    }

    @Test
    public void mapsMerging() {
        Map<String, Double> first = new HashMap<>();
        Map<String, Double> second = new HashMap<>();

        first.put("a", 0.5);
        first.put("b", 0.5);

        second.put("a", 0.2);
        second.put("b", 0.4);
        second.put("c", 0.4);

        Map<String, Double> result = MathUtils.mergeProbabilityMaps(first, second);

        assertEquals(1.0 / 3.0, result.get("a"));
        assertEquals(2.0 / 3.0, result.get("b"));
    }
}
