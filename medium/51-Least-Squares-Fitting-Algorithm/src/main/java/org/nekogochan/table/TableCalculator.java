package org.nekogochan.table;

import org.nekogochan.domain.Table;
import org.nekogochan.function.Function;

/**
 * Доп. сущность для построения таблицы x,y значений
 */
public class TableCalculator {

    // таблица значений
    private Table table = new Table();
    // функция для расчета
    private Function foo;

    /**
     * Установка функции для таблицы
     * @param foo - наслудемый от Function объект
     */
    public void setFoo(Function foo) {
        this.foo = foo;
    }

    /**
     * Построение таблицы
     * @param min - нижняя граница отрезка
     * @param max - верхняя граница отрезка
     * @param size - количество равноудаленных друг от друга точек на отрезке
     * @param functionParameters - параметры функции
     */
    public void configureTable(double min, double max, int size, double[] functionParameters) {
        table.setSize(size);
        double step = (max - min) / (size - 1);
        double current = min;

        for (int i = 0; i < size; i++) {
            table.addValue(current, foo.getValue(new double[]{current}, functionParameters));
            current += step;
        }
    }

    /**
     * Возвращает значения x таблицы
     * @return table.xValues
     */
    public double[] getXValues() {
        return table.getXValues();
    }

    /**
     * Возвращает значения y таблицы
     * @return table.yValues
     */
    public double[] getYValues() {
        return table.getYValues();
    }
}
