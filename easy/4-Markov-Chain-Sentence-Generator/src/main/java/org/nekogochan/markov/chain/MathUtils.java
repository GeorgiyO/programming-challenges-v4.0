package org.nekogochan.markov.chain;

import java.util.HashMap;
import java.util.Map;

public class MathUtils {

    public static Map<String, Double> mergeProbabilityMaps(Map<String, Double> a, Map<String, Double> b) {
        if (b == null || b.isEmpty()) return a;
        if (a == null || a.isEmpty()) return b;
        Map<String, Double> res = new HashMap<>();
        for (var aEntry : a.entrySet()) {
            for (var bEntry : b.entrySet()) {
                var aw = aEntry.getKey();
                var bw = bEntry.getKey();
                var ap = aEntry.getValue();
                var bp = bEntry.getValue();
                if (aw.equals(bw)) {
                    res.put(aw, ap * bp);
                }
            }
        }
        normalizeProbabilities(res);
        return res;
    }

    public static void normalizeProbabilities(Map<String, Double> map) {
        double sum = 0.0;
        for (var e : map.entrySet()) {
            sum += e.getValue();
        }
        for (var k : map.keySet()) {
            map.merge(k, sum, (old, extra) -> old / extra);
        }
    }
}
