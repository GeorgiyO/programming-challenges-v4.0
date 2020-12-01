package org.nekogochan.domain;

import static org.nekogochan.Main.floatToFormat;

/**
 * Вспомогательный класс для упрощения вывода строк содержащих массив double чисел
 */
public class Row {
    // название строки
    private String name;
    // массив double значений, одинарный или двойной
    private Object array;
    // дополнительная строка для вывода
    private String extraData = null;

    // конструктор для инициализации значений без доп. строки
    public Row(String name, Object array) {
        this.name = name;
        this.array = array;
    }

    // конструктор для инициализации значений с доп. строкой
    public Row(String name, Object array, String extraData) {
        this.name = name;
        this.array = array;
        this.extraData = extraData;
    }

    /**
     * Переопредленный метод toString() для вывода
     * @return представление названия, массива, доп строки (если присутствует) в виде одной строки
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(name + ":");
        int extraLength = 4 - res.length();
        while (extraLength > 0) {
            res.append(" ");
            extraLength--;
        }
        res.append(nDFloatArrayToString(array));
        if (extraData != null) res.append(" | ").append(extraData);
        return res.toString();
    }

    /**
     * Возвращает одинарный или двойной массив в виде строки
     * @param arr - одинарный или двойной массив double значений
     * @return - массив в виде строки
     */
    private static String nDFloatArrayToString(Object arr) {
        if (arr.getClass() == double[].class) {
            return arrayFloatToString((double[]) arr);
        } else if (arr.getClass() == double[][].class) {
            return doubleArrayFloatToString((double[][]) arr);
        } else {
            throw new IllegalArgumentException("not a one-two dimensional array");
        }
    }

    /**
     * Возвращает представление одинарного массива в виде строки
     * @param arr - double[] массив
     * @return - массив в виде строки
     */
    private static String arrayFloatToString(double[] arr) {
        StringBuilder str = new StringBuilder();
        for (double d : arr) {
            str.append("\t").append(floatToFormat(d));
        }
        return str.toString();
    }

    /**
     * Возвращает представление двойного массива в виде строки
     * @param arr - двойной массив double значений
     * @return - массив в виде строки
     */
    private static String doubleArrayFloatToString(double[][] arr) {
        StringBuilder str = new StringBuilder();
        for (double[] d : arr) {
            str.append("\t").append(floatToFormat(d[0]));
        }
        return str.toString();
    }
}
