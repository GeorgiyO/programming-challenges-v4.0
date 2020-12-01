package org.nekogochan.function;

/**
 * Абстрактный класс для определения анонимных классов мат. функций
 */
public abstract class Function {
    /**
     * Расчет значения f(x) на основе аргумента и параметров функции
     * @param xValues - аргументы 'x'
     * @param parameters - аргументы функции a, b, c...
     * @return значение f(x)
     */
    public abstract double getValue(double[] xValues, double[] parameters);

    /**
     * Возвращает количество параметров
     * @return parameters.length
     */
    public abstract int getNParameters();
}
