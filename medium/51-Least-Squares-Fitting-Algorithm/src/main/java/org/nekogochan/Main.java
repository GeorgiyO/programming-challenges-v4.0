package org.nekogochan;

import org.nekogochan.domain.Row;
import org.nekogochan.function.Function;
import org.nekogochan.function.NDegreeFunction;
import org.nekogochan.handler.LinearRegression;
import org.nekogochan.handler.Regression;
import org.nekogochan.table.TableCalculator;

import java.util.Arrays;
import java.util.Locale;

public class Main {

    // формат чисел с плавающей точкой
    public static final String floatFormat = "%.2f";

    // значения A,B отрезка [A, B]
    private static final double A = 0;
    private static final double B = 3;
    // количество значений в таблцие x,y
    private static final int SIZE = 8;

    // функция для построения таблицы
    static Function foo = new Function() {
        @Override
        public double getValue(double[] xValues, double[] parameters) {
            double x = xValues[0];
            return Math.log(2 * x + 1) + 2 * Math.sin(3 * x);
        }

        @Override
        public int getNParameters() {
            return 2;
        }

        @Override
        public String toString() {
            return "y = Math.sin(a * x - b)";
        }
    };

    // функции используемые в регрессии
    static NDegreeFunction linear = new NDegreeFunction("linear") {
        @Override
        public double getValue(double[] xValues, double[] parameters) {
            double a = parameters[0];
            double b = parameters[1];
            double x = xValues[0];
            return x*a + b;
        }

        @Override
        public int getNParameters() {
            return 2;
        }
    };
    static NDegreeFunction parabolic = new NDegreeFunction("parabolic") {
        @Override
        public double getValue(double[] xValues, double[] parameters) {
            double a = parameters[0];
            double b = parameters[1];
            double c = parameters[2];
            double x = xValues[0];
            return Math.pow(x, 2)*a + x*b + c;
        }

        @Override
        public int getNParameters() {
            return 3;
        }
    };
    static NDegreeFunction cubic = new NDegreeFunction("cubic") {
        @Override
        public double getValue(double[] xValues, double[] parameters) {
            double a = parameters[0];
            double b = parameters[1];
            double c = parameters[2];
            double d = parameters[3];
            double x = xValues[0];
            return Math.pow(x, 3)*a + Math.pow(x, 2)*b + x*c + d;
        }

        @Override
        public int getNParameters() {
            return 4;
        }
    };

    static NDegreeFunction[] functions = new NDegreeFunction[] {
            linear, parabolic, cubic
    };

    // таблица значений x,y
    static double[][] x;
    static double[] y;
    // параметры функций, их значения f(x), неявки, сумма неявок
    static double[][] functionsParameters = new double[functions.length][];
    static double[][] functionsRes = new double[functions.length][SIZE];
    static double[][] functionsErrors = new double[functions.length][SIZE];
    static double[] functionsErrorsSum = new double[functions.length];

    // сущность, отвечающая за нахождение аргументов функций по методу наименьших квадратов
    static Regression regression = new LinearRegression();
    // сущность, отвечающая за построение таблицы x,y
    static TableCalculator tableCalculator = new TableCalculator();

    // инициализация x и y массивов
    static {
        tableCalculator.setFoo(foo);

        tableCalculator.configureTable(A, B, SIZE, new double[]{});

        double[] xValues = tableCalculator.getXValues();

        x = new double[SIZE][1];

        for (int i = 0; i < xValues.length; i++) {
            x[i][0] = xValues[i];
        }

        y = tableCalculator.getYValues();

        Locale.setDefault(Locale.US);
    }

    // точка входа в программу
    public static void main(String[] args) {
        regression.setData(x, y);
        for (int i = 0; i < functions.length; i++) {
            regression.setFunction(functions[i]);
            regression.fitData();
            functionsParameters[i] = regression.getParameters();
            setRes(i);
            functionsErrors[i] = regression.getErrors();
            functionsErrorsSum[i] = Arrays.stream(functionsErrors[i]).sum();
        }
        printResult();
    }

    /**
     * Установка значения functionsRes[i]
     * @param i - значение i для массива functions[]
     */
    private static void setRes(int i) {
        TableCalculator tableCalculator = new TableCalculator();
        tableCalculator.setFoo(functions[i]);
        tableCalculator.configureTable(A, B, SIZE, functionsParameters[i]);
        functionsRes[i] = tableCalculator.getYValues();
    }

    /**
     * Вывод результата
     */
    private static void printResult() {

        System.out.println("               __                              .__                   \n" +
                "  ____   ____ |  | ______   ____   ____   ____ |  |__ _____    ____  \n" +
                " /    \\_/ __ \\|  |/ /  _ \\ / ___\\ /  _ \\_/ ___\\|  |  \\\\__  \\  /    \\ \n" +
                "|   |  \\  ___/|    <  <_> ) /_/  >  <_> )  \\___|   Y  \\/ __ \\|   |  \\\n" +
                "|___|  /\\___  >__|_ \\____/\\___  / \\____/ \\___  >___|  (____  /___|  /\n" +
                "     \\/     \\/     \\/    /_____/             \\/     \\/     \\/     \\/ ");
        System.out.println(" - - - - - - - - - - - - - - - - ");

        System.out.println("a= " + A + "; b=" + B + "; n=" + SIZE);
        System.out.println("foo: " + foo.toString());
        for (int i = 0; i < functions.length; i++) {
            System.out.println("f" + i + ": " + functions[i].toString(functionsParameters[i]));
        };
        System.out.println(" - - - - - - - - - - - - - - - - ");

        Row[] rows = {
                new Row("x", x),
                new Row("y", y),
                new Row("f1", functionsRes[0]),
                new Row("f2", functionsRes[1]),
                new Row("f3", functionsRes[2]),
                new Row("err1", functionsErrors[0], "sum: " + floatToFormat(functionsErrorsSum[0])),
                new Row("err2", functionsErrors[1], "sum: " + floatToFormat(functionsErrorsSum[1])),
                new Row("err3", functionsErrors[2], "sum: " + floatToFormat(functionsErrorsSum[2]))
        };
        for (Row row : rows) {
            System.out.println(row);
        }
    }

    /**
     * Перевод числа с плавающей точкой в строку с использованием форматированного вывода
     * @param f - число с плавающей точкой
     * @return представление числа в виде строки
     */
    public static String floatToFormat(double f) {
        return String.format(floatFormat, f);
    }
}
