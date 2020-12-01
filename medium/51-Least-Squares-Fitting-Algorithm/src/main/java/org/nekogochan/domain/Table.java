package org.nekogochan.domain;

/**
 * Вспомогательный класс для построения таблицы x,y Значений на основе отрезка и количества его делений
 */
public class Table {

    // массив x значений
    private double[] xValues;
    // массив y значений
    private double[] yValues;

    // размер таблицы
    private int size;
    // переменная для итерации
    private int i = 0;

    /**
     * Установка размера таблицы, сброс переменной итерации, переопределение массивов x,y
     * @param size
     */
    public void setSize(int size) {
        i = 0;
        this.size = size;
        xValues = new double[size];
        yValues = new double[size];
    }

    /**
     * Добавление в таблицу пары значений x,y
     * @param x
     * @param y
     */
    public void addValue(double x, double y) {
        if (i == size) throw new IllegalStateException("i == size");
        xValues[i] = x;
        yValues[i] = y;
        i++;
    }

    /**
     * Возвращает массив значений x
     * @return xValues
     */
    public double[] getXValues() {
        return xValues;
    }

    /**
     * Возвращает массив значений y
     * @return yValues
     */
    public double[] getYValues() {
        return yValues;
    }
}
