import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
При прочтении ТЗ не указана форма ввода, но из примеров я увидел, что форма ввода такого формата типа данных String:
число(1-0)пробелМатЗнакПробелЧисло(1-0), и сделал приложение под такую форму ввода. Другие формы ввода будут кидать исключение.
В варианте для римских чисел то же самое, но вместо чисел символы I, V, X написанные по правилам записи римских цифр.
 */

public class Main {

    // Мапа для перевода римских чисел в арабские.
    static Map <String, Integer> toRoma = new HashMap<>();
    static{
        toRoma.put("I", 1); toRoma.put("II", 2); toRoma.put("III", 3); toRoma.put("IV", 4); toRoma.put("V", 5);
        toRoma.put("VI", 6); toRoma.put("VII", 7); toRoma.put("VIII", 8); toRoma.put("IX", 9); toRoma.put("X", 10);
    }

    public static void main(String[] args) throws Exception {
        Input input = new Input();
        System.out.println(Main.calc(input.inputString()));

    }
    public static String calc(String input) throws Exception {
        String result="";

        Pattern patternArabian = Pattern.compile("\\d+ [/+*\\-] \\d+");
        Pattern patternRoman = Pattern.compile("(X|IX|IV|V?I{0,3}) +[/*+\\-] (X|IX|IV|V?I{0,3})");
        String [] strings = input.split(" ");


        Matcher matcherArabian = patternArabian.matcher(input);
        Matcher matcherRoman = patternRoman.matcher(input);

        // Проверка на соответсвие строки условию ввода арабских чисел
        if (matcherArabian.matches()) {
        // Проверка соответсвия арабских чисел на диапазон ТЗ
            if(Integer.parseInt(strings[0])<0 || Integer.parseInt(strings[0])>10
            || Integer.parseInt(strings[2])<0 || Integer.parseInt(strings[2])>10 ){
                throw new Exception();
            } else {
                int a = Integer.parseInt(strings[0]);
                int b = Integer.parseInt(strings[2]);
                // Проверка математического знака
                if (strings[1].equals("+")) {
                    result = Integer.toString(a + b);
                } else if (strings[1].equals("-")){
                    result = Integer.toString(a - b);
                } else if (strings[1].equals("*")){
                    result = Integer.toString(a * b);
                } else if (strings[1].equals("/")){
                    result = Integer.toString(a / b);
                }
            }
            return result;
            // Первая проверка на соответствие строки условию ввода римских цифр
        }else if (matcherRoman.matches()) {
            // проверка математического знака
            int a = toRoma.get(strings[0]);
            int b = toRoma.get(strings[2]);
            if (strings[1].equals("+")) {
                result = RomanConverter.toRoman(a+b);
            } else if (strings[1].equals("-")){
                // Результат вычитания не должен быть меньше единицы
                if(a-b<1){
                    throw new Exception();
                } else {
                    result = RomanConverter.toRoman(a-b);
                }
            } else if (strings[1].equals("*")){
                result = RomanConverter.toRoman(a*b);
            } else if (strings[1].equals("/")) {
                // Результат деления не должен быть меньше единицы
                if (a / b < 1) {
                    throw new Exception();
                } else {
                    result = RomanConverter.toRoman(a/b);
                }
            }

        } else if (!matcherArabian.matches() && !matcherRoman.matches()){
            throw new Exception();
        } return result;
    }
}
class Input {
    private final Scanner scanner = new Scanner(System.in);

    public String inputString(){
        return scanner.nextLine();
    }
}
class RomanConverter{
    public static String toRoman(int number) {
        int[] romanValueArray = new int[]{100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanCharArray = new String[]{"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < romanValueArray.length; i++) {
            if (number == 0) {
                break;
            } else {
                while (number >= romanValueArray[i]) {
                    number = number - romanValueArray[i];
                    result.append(romanCharArray[i]);
                }
            }
        }
        return result.toString();
    }
}