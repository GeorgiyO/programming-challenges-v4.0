package org.nekogochan.handler;

import org.nekogochan.function.Function;

/**
 * Интерфейс для описания класса, отвечающего за регрессию
 */
public interface Regression {

    /**
     * Установка значений x, y
     * @param x - двойной массив double
     * @param y - одинарный массив double
     */
    void setData(double[][] x, double[] y);

    /**
     * Установка функции
     * @param foo - наследуемый от Function класс
     */
    void setFunction(Function foo);

    /**
     * Возвращает величиную неявок/ошибок для каждого x
     * @return double[] errors
     */
    double[] getErrors();

    /**
     * Возвращает параметры функции foo
     * @return double[] parameters
     */
    double[] getParameters();

    /**
     * Возвращает неточность аргументов функции
     * @return double[] uncertainty
     */
    double[] getUncertainty();

    /**
     * Нахождение аргументов функции foo
     */
    void fitData();

}
