package org.nekogochan.function;

import org.nekogochan.domain.MathFunctions;

import static org.nekogochan.Main.floatFormat;

/**
 * Вспомогательный класс для определения функций типа a*x^n + b*x^(n-1) + ... + r*x + t = f(x)
 */
public abstract class NDegreeFunction extends Function {

    // имя функции
    private String name;

    // конструктор с именем
    public NDegreeFunction(String name) {
        this.name = name;
    }

    // то же, что и в Function
    public abstract double getValue(double[] xValues, double[] parameters);
    public abstract int getNParameters();

    /**
     * Представление в виде строки
     * @param parameters - параметры функции
     * @return - представление функции с заданными параметрами
     */
    public String toString(double[] parameters) {
        StringBuilder str = new StringBuilder(name);
        str.append(":");

        for (int i = 0; i < parameters.length; i++) {
            int degree = parameters.length - i - 1;
            double par = parameters[i];

            switch (degree) {
                case 0:
                    str.append(getStrPart(par, ""));
                    break;
                case 1:
                    str.append(getStrPart(par, "x"));
                    break;
                default:
                    str.append(getStrPart(par, "x^" + (parameters.length - i - 1)));
            }
        }

        return str.toString();
    }

    /**
     * Вспомогательный класс для построения строки toString
     * @param par - параметр из массива parameters
     * @param part - Доп. строка (добавляется в конец)
     * @return - String
     */
    private String getStrPart(double par, String part) {
        String sign = MathFunctions.getSign(par);
        String res = " ";
        if (!sign.equals("")) {
            if (!sign.equals("-")) {
                res += sign;
            }
            res += String.format(floatFormat, par);
            if (part != null) {
                res += part;
            }
        }
        return res;
    }
}
