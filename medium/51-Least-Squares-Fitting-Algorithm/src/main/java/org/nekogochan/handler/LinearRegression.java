package org.nekogochan.handler;

import Jama.Matrix;
import org.nekogochan.function.Function;

import java.util.Arrays;

/**
 * Сущность отвечающая за линейную регрессию через метод наименьших квадратов
 * https://en.wikipedia.org/wiki/Linear_regression
 */
public class LinearRegression implements Regression {

    // временные переменные
    private double[] working;

    // значения x, y, параметры a функции foo
    private double[][] x;
    private double[] y;
    private double[] a;

    // интерполируемая функция
    private Function foo;

    // неявки
    private double[] errors;
    // производные
    private double[][] derivatives;
    // бета и альфа матрицы
    private double[] beta;
    private double[][] alpha;

    /**
     * Здесь и далее
     * @Override - означает что метод переопределен у интерфейса/абстрактного класса
     * описание см. в классе org.nekogochan.handler.Regression
     */
    @Override
    public void setData(double[][] x, double[] y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setFunction(Function foo) {
        this.foo = foo;
        a = new double[foo.getNParameters()];
        working = new double[foo.getNParameters()];
        for (int i = 0; i < foo.getNParameters(); i++) {
            a[i] = 0;
        }
    }

    @Override
    public double[] getParameters() {
        return a;
    }

    @Override
    public void fitData() {
        init();
        iterateValues();
    }

    /**
     * Инициализация значений неявок и производных
     */
    private void init() {
        errors = new double[y.length];
        derivatives = new double[y.length][a.length];
    }

    /**
     * Расчет аргументов функции
     */
    private void iterateValues() {
        getErrors();
        calculateDerivatives();

        createBetaMatrix();
        createAlphaMatrix();

        Matrix alphaMatrix = new Matrix(alpha);
        Matrix betaMatrix = new Matrix(beta, beta.length);

        Matrix out = alphaMatrix.solve(betaMatrix);

        double[][] deltaA = out.getArray();

        for (int i = 0; i < a.length; i++)
            a[i] += deltaA[i][0];
    }

    @Override
    public double[] getErrors() {
        double[] res = new double[y.length];
        for (int i = 0; i < y.length; i++) {
            double v = foo.getValue(x[i], a);
            errors[i] = y[i] - v;
            res[i] = Math.pow(errors[i], 2);
        }
        return res;
    }

    /**
     * Расчет производных
     */
    private void calculateDerivatives() {
        for (int j = 0; j < a.length; j++){
            for (int i = 0; i < y.length; i++){
                derivatives[i][j] = calculateDerivative(j, x[i]);
            }
        }
    }

    /**
     * Расчет производной для заданных значений
     * @param k - индекс параметра a[]
     * @param x - значение x для расчетра производной
     * @return производная
     */
    private double calculateDerivative(int k, double[] x) {
        for (int i = 0; i < foo.getNParameters(); i++){
            working[i] = i==k?1:0;
        }
        return foo.getValue(x, working);
    }

    /**
     * Инициализация бета-матрицы
     */
    private void createBetaMatrix() {
        beta = new double[a.length];
        for (int i = 0; i < beta.length; i++) {
            for (int j = 0; j < x.length; j++) {
                beta[i] += errors[j] * derivatives[j][i];
            }
        }
    }

    /**
     * Инициализация альфа-матрицы
     */
    private void createAlphaMatrix() {
        alpha = new double[a.length][a.length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length; j++) {
                for (int k = 0; k < x.length; k++) {
                    alpha[j][i] += derivatives[k][i] * derivatives[k][j];
                }
            }
        }
    }

    @Override
    public double[] getUncertainty() {
        Matrix aMatrix = new Matrix(alpha);
        Matrix b = aMatrix.inverse();

        double[] residuals = new double[a.length];
        double error = Arrays.stream(getErrors()).sum() / y.length;

        for (int i = 0; i < a.length; i++){
            residuals[i] = Math.sqrt(b.get(i, i) * error);
        }
        return residuals;
    }
}
