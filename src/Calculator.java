import java.util.Scanner;

public class Calculator {

    private static final String ARABIC_NUMERALS = "012345678910";
    private static final String ROMAN_NUMERALS = "IVXLCDM";

    public static int toArabic(String roman) {
        int arabic = 0;
        for (int i = 0; i < roman.length(); i++) {
            char current = roman.charAt(i);
            char next = (i + 1) < roman.length() ? roman.charAt(i + 1) : '\0';
            if (ROMAN_NUMERALS.indexOf(current) == -1) {
                throw new IllegalArgumentException("Неверная римская цифра: " + current);
            }
            if (current == 'I') {
                if (next == 'V') {
                    arabic += 4;
                    i++;
                } else if (next == 'X') {
                    arabic += 9;
                    i++;
                } else {
                    arabic += 1;
                }
            } else if (current == 'V') {
                arabic += 5;
            } else if (current == 'X') {
                if (next == 'L') {
                    arabic += 40;
                    i++;
                } else if (next == 'C') {
                    arabic += 90;
                    i++;
                } else {
                    arabic += 10;
                }
            } else if (current == 'L') {
                arabic += 50;
            } else if (current == 'C') {
                if (next == 'D') {
                    arabic += 400;
                    i++;
                } else if (next == 'M') {
                    arabic += 900;
                    i++;
                } else {
                    arabic += 100;
                }
            } else if (current == 'D') {
                arabic += 500;
            } else if (current == 'M') {
                arabic += 1000;
            }
        }
        return arabic;
    }


    public static String toRoman(int num) {
        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] units = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return thousands[num / 1000] + hundreds[(num % 1000) / 100] + tens[(num % 100) / 10] + units[num % 10];
    }


    public static String calc(String input) {
        String[] parts = input.split(" ");
        if (parts.length < 3) throw new IllegalArgumentException("Строка не является математической операцией.");
        if (parts.length > 3) throw new IllegalArgumentException("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");

        int num1;
        int num2;
        String operator = parts[1];

        boolean isNum1Arabic = ARABIC_NUMERALS.contains(parts[0]);
        boolean isNum2Arabic = ARABIC_NUMERALS.contains(parts[2]);
        boolean isRoman = !isNum1Arabic && !isNum2Arabic;

        if (isNum1Arabic && isNum2Arabic) {
            num1 = Integer.parseInt(parts[0]);
            num2 = Integer.parseInt(parts[2]);
        } else if (!isNum1Arabic && !isNum2Arabic) {
            num1 = toArabic(parts[0]);
            num2 = toArabic(parts[2]);
        } else {
            throw new IllegalArgumentException("Используются одновременно разные системы счисления.");
        }
        if ((isNum1Arabic && (num1 < 1 || num1 > 10) || (isNum2Arabic && (num2 < 1 || num2 > 10)))) {
            throw new IllegalArgumentException("Введите числа от 1 до 10 ");
        }
        int result = 0;
        switch (operator) {
            case "+" -> result = num1 + num2;
            case "-" -> result = num1 - num2;
            case "*" -> result = num1 * num2;
            case "/" -> result = num1 / num2;
            default -> System.out.println("Неверная математическая операция.");
        }
        if (isRoman && result < 1) throw new IllegalArgumentException("В римской системе счисления нет ноля или отрицательных чисел.");
        return isRoman ? toRoman(result) : String.valueOf(result);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите данные: ");
        String input = scanner.nextLine();
        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (IllegalArgumentException exception) {
            System.out.println("Ошибка " + exception.getLocalizedMessage());
        }
    }
}