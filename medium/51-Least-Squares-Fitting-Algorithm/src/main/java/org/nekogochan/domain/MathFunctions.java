package org.nekogochan.domain;

/**
 * Класс со статичными функция для вспомогательной работы
 */
public class MathFunctions {
    /**
     * Возвращает +, -, или пустую строку в зависимости от знака числа
     * @param number - число
     * @return - строка-знак
     */
    public static String getSign(Number number) {
        double signum = Math.signum(number.doubleValue());
        if (signum == 1.0) {
            return "+";
        } else if (signum == -1.0) {
            return "-";
        } else {
            return "";
        }
    }
}
